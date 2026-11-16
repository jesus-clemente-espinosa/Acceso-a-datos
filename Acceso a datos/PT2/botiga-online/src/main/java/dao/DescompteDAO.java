package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Descompte;
import util.Connexio;

public class DescompteDAO {

    public void inserir(Descompte d) throws SQLException {
        String sql = "INSERT INTO Descomptes (producte_id, tipus, valor) VALUES (?, ?, ?)";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getProducteId());
            ps.setString(2, d.getTipus());
            ps.setDouble(3, d.getValor());
            ps.executeUpdate();
        }
    }

    public List<Descompte> llistar() throws SQLException {
        List<Descompte> llista = new ArrayList<>();
        String sql = "SELECT id, producte_id, tipus, valor FROM Descomptes";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                llista.add(new Descompte(rs.getInt("id"),
                                         rs.getInt("producte_id"),
                                         rs.getString("tipus"),
                                         rs.getDouble("valor")));
            }
        }
        return llista;
    }

    public boolean actualitzar(Descompte d) throws SQLException {
        String sql = "UPDATE Descomptes SET producte_id=?, tipus=?, valor=? WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getProducteId());
            ps.setString(2, d.getTipus());
            ps.setDouble(3, d.getValor());
            ps.setInt(4, d.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Descomptes WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}