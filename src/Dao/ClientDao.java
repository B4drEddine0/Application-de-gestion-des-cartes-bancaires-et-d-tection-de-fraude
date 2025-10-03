package Dao;

import Modal.Client;
import Modal.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDao implements ClientRepository {

    @Override
    public void insertClient(Client client) {
        String sql = "INSERT INTO Client (name, email, password) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.name());
            ps.setString(2, client.email());
            ps.setString(3, client.password());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting client: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Client> findClientByEmail(String email) {
        String sql = "SELECT * FROM Client WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String clientEmail = rs.getString("email");
                String password = rs.getString("password");
                Client client = new Client(id, name, clientEmail, password);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client by email: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> findClientById(int id) {
        String sql = "SELECT * FROM Client WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Client client = new Client(id, name, email, password);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client by id: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Client client = new Client(id, name, email, password);
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all clients: " + e.getMessage(), e);
        }
        return clients;
    }

    @Override
    public void updateClient(Client client) {
        String sql = "UPDATE Client SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.name());
            ps.setString(2, client.email());
            ps.setString(3, client.password());
            ps.setInt(4, client.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteClient(int id) {
        String sql = "DELETE FROM Client WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client: " + e.getMessage(), e);
        }
    }
}
