package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Producte;
import util.Connexio;

public class ProducteDAO {

    public void inserir(Producte p) throws SQLException {
        String sql = "INSERT INTO Productes (nom, preu, estoc) VALUES (?, ?, ?)";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPreu());
            ps.setInt(3, p.getEstoc());
            ps.executeUpdate();
        }
    }

    public List<Producte> llistar() throws SQLException {
        List<Producte> llista = new ArrayList<>();
        String sql = "SELECT id, nom, preu, estoc FROM Productes";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                llista.add(new Producte(rs.getInt("id"), rs.getString("nom"),
                                        rs.getDouble("preu"), rs.getInt("estoc")));
            }
        }
        return llista;
    }

    public void actualitzar(Producte p) throws SQLException {
        String sql = "UPDATE Productes SET nom=?, preu=?, estoc=? WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPreu());
            ps.setInt(3, p.getEstoc());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Productes WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}