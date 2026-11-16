package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import util.Connexio;

public class ClientDAO {

    public void inserir(Client cl) throws SQLException {
        String sql = "INSERT INTO Clients (nom, correu) VALUES (?, ?)";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cl.getNom());
            ps.setString(2, cl.getCorreu());
            ps.executeUpdate();
        }
    }

    public List<Client> llistar() throws SQLException {
        List<Client> llista = new ArrayList<>();
        String sql = "SELECT id, nom, correu FROM Clients";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                llista.add(new Client(rs.getInt("id"),
                                      rs.getString("nom"),
                                      rs.getString("correu")));
            }
        }
        return llista;
    }

    public Client buscarPerId(int id) throws SQLException {
        String sql = "SELECT id, nom, correu FROM Clients WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(rs.getInt("id"),
                                      rs.getString("nom"),
                                      rs.getString("correu"));
                }
            }
        }
        return null; // no existe
    }

    public boolean actualitzar(Client cl) throws SQLException {
        String sql = "UPDATE Clients SET nom=?, correu=? WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cl.getNom());
            ps.setString(2, cl.getCorreu());
            ps.setInt(3, cl.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Clients WHERE id=?";
        try (Connection c = Connexio.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}