package dao;

import java.sql.*;
import java.util.List;
import model.LiniaComanda;
import util.Connexio;

public class ComandaDAO {

    /**
     * Crea una comanda con sus línies dins d'una transacció.
     * Retorna l'id de la comanda creada.
     * Llança SQLException si alguna cosa falla (després de fer rollback).
     * @param clientId
     * @param linies
     * @return 
     * @throws java.sql.SQLException
     */
    public int crearComanda(int clientId, List<LiniaComanda> linies) throws SQLException {
        if (linies == null || linies.isEmpty()) {
            throw new SQLException("La comanda ha de tenir almenys una línia.");
        }

        Connection c = null;
        try {
            c = Connexio.getConnection();
            c.setAutoCommit(false); // inicia la transacció

            // 1. Inserir la comanda (total a 0 de moment)
            int comandaId;
            String sqlComanda = "INSERT INTO Comandes (client_id, total) VALUES (?, 0)";
            try (PreparedStatement ps = c.prepareStatement(sqlComanda, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, clientId);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    comandaId = keys.getInt(1);
                }
            }

            double total = 0;

            // 2. Per cada línia: comprovar estoc, decrementar i inserir
            String sqlEstoc = "SELECT preu, estoc FROM Productes WHERE id=? FOR UPDATE";
            String sqlDecrementa = "UPDATE Productes SET estoc = estoc - ? WHERE id=?";
            String sqlLinia = "INSERT INTO LiniesComanda (comanda_id, producte_id, quantitat, preuUnitari) VALUES (?, ?, ?, ?)";

            for (LiniaComanda l : linies) {
                if (l.getQuantitat() <= 0) {
                    throw new SQLException("La quantitat ha de ser positiva.");
                }

                double preu;
                try (PreparedStatement ps = c.prepareStatement(sqlEstoc)) {
                    ps.setInt(1, l.getProducteId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("El producte " + l.getProducteId() + " no existeix.");
                        }
                        preu = rs.getDouble("preu");
                        int estoc = rs.getInt("estoc");
                        if (estoc < l.getQuantitat()) {
                            throw new SQLException("Estoc insuficient pel producte "
                                    + l.getProducteId() + " (disponible: " + estoc + ").");
                        }
                    }
                }

                try (PreparedStatement ps = c.prepareStatement(sqlDecrementa)) {
                    ps.setInt(1, l.getQuantitat());
                    ps.setInt(2, l.getProducteId());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = c.prepareStatement(sqlLinia)) {
                    ps.setInt(1, comandaId);
                    ps.setInt(2, l.getProducteId());
                    ps.setInt(3, l.getQuantitat());
                    ps.setDouble(4, preu);
                    ps.executeUpdate();
                }

                total += preu * l.getQuantitat();
            }

            // 3. Total sense descompte
            try (PreparedStatement ps = c.prepareStatement("UPDATE Comandes SET total=? WHERE id=?")) {
                ps.setDouble(1, total);
                ps.setInt(2, comandaId);
                ps.executeUpdate();
            }

            // 4. SAVEPOINT: a partir d'aquí, intentem aplicar descomptes
            Savepoint sp = c.setSavepoint("DespresLinies");
            try {
                double totalAmbDescompte = aplicarDescomptes(c, comandaId);
                try (PreparedStatement ps = c.prepareStatement("UPDATE Comandes SET total=? WHERE id=?")) {
                    ps.setDouble(1, totalAmbDescompte);
                    ps.setInt(2, comandaId);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                // El descompte ha fallat: tornem al savepoint.
                // La comanda, les línies i l'estoc es conserven.
                c.rollback(sp);
                System.out.println("No s'ha pogut aplicar el descompte (" + e.getMessage()
                        + "). Es desa la comanda sense descompte.");
            }

            c.commit();
            return comandaId;

        } catch (SQLException e) {
            if (c != null) {
                try {
                    c.rollback(); // desfà-ho tot
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (c != null) {
                try {
                    c.setAutoCommit(true);
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
        /**
     * Calcula el total de la comanda aplicant els descomptes de cada producte.
     * Utilitza la mateixa connexió (mateixa transacció).
     */
    private double aplicarDescomptes(Connection c, int comandaId) throws SQLException {
        String sql = "SELECT l.quantitat, l.preuUnitari, d.tipus, d.valor "
                   + "FROM LiniesComanda l "
                   + "LEFT JOIN Descomptes d ON d.producte_id = l.producte_id "
                   + "WHERE l.comanda_id = ?";
        double total = 0;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, comandaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int quantitat = rs.getInt("quantitat");
                    double subtotal = rs.getDouble("preuUnitari") * quantitat;
                    String tipus = rs.getString("tipus");   // null si no hi ha descompte
                    double valor = rs.getDouble("valor");

                    double descompte = 0;
                    if ("%".equals(tipus)) {
                        descompte = subtotal * valor / 100.0;
                    } else if ("€".equals(tipus)) {
                        descompte = valor * quantitat;
                    }
                    descompte = Math.min(descompte, subtotal); // mai negatiu
                    total += subtotal - descompte;
                }
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }
}