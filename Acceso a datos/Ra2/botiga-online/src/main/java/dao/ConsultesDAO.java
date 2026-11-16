package dao;

import java.sql.*;
import util.Connexio;

public class ConsultesDAO {

    /**
     * Mostra les comandes d'un client amb les seves línies i productes.
     */
    public void llistarComandesClient(int clientId) throws SQLException {
        String sql = "SELECT cm.id AS comanda_id, cm.data, cm.total, "
                   + "       cl.nom AS client, "
                   + "       p.nom AS producte, l.quantitat, l.preuUnitari, "
                   + "       (l.quantitat * l.preuUnitari) AS subtotal "
                   + "FROM Comandes cm "
                   + "JOIN Clients cl ON cl.id = cm.client_id "
                   + "JOIN LiniesComanda l ON l.comanda_id = cm.id "
                   + "JOIN Productes p ON p.id = l.producte_id "
                   + "WHERE cm.client_id = ? "
                   + "ORDER BY cm.id, l.id";

        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                int comandaActual = -1;
                boolean hiHaDades = false;

                while (rs.next()) {
                    hiHaDades = true;
                    int comandaId = rs.getInt("comanda_id");

                    // Capçalera cada cop que canviem de comanda
                    if (comandaId != comandaActual) {
                        comandaActual = comandaId;
                        System.out.println("\nComanda #" + comandaId
                                + " | Client: " + rs.getString("client")
                                + " | Data: " + rs.getTimestamp("data")
                                + " | TOTAL: " + String.format("%.2f", rs.getDouble("total")) + " €");
                    }
                    System.out.printf("   - %s x%d a %.2f € = %.2f €%n",
                            rs.getString("producte"),
                            rs.getInt("quantitat"),
                            rs.getDouble("preuUnitari"),
                            rs.getDouble("subtotal"));
                }
                if (!hiHaDades) {
                    System.out.println("Aquest client no té comandes.");
                }
            }
        }
    }

    /**
     * Resum de totes les comandes: total sense descompte vs total final.
     */
    public void llistarTotalsComandes() throws SQLException {
        String sql = "SELECT cm.id, cl.nom AS client, "
                   + "       SUM(l.quantitat * l.preuUnitari) AS sense_descompte, "
                   + "       cm.total AS final "
                   + "FROM Comandes cm "
                   + "JOIN Clients cl ON cl.id = cm.client_id "
                   + "JOIN LiniesComanda l ON l.comanda_id = cm.id "
                   + "GROUP BY cm.id, cl.nom, cm.total "
                   + "ORDER BY cm.id";

        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\nComanda | Client | Sense descompte | Total final");
            boolean hiHaDades = false;
            while (rs.next()) {
                hiHaDades = true;
                System.out.printf("#%d | %s | %.2f € | %.2f €%n",
                        rs.getInt("id"),
                        rs.getString("client"),
                        rs.getDouble("sense_descompte"),
                        rs.getDouble("final"));
            }
            if (!hiHaDades) {
                System.out.println("No hi ha comandes.");
            }
        }
    }
}