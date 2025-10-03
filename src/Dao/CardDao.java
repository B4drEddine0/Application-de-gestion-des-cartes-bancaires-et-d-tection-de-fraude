package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Modal.Card;
import Modal.CarteCredit;
import Modal.CarteDebit;
import Modal.CartePrepayee;
import Modal.DatabaseConnection;
import Modal.StatutCarte;

public class CardDao implements CardRepository {

    @Override
    public void insertCard(Card card) {
        String sql = "INSERT INTO cards (numero, dateExpiration , statut, idClient, type, plafondJournalier, plafondMensuel, tauxInteret, soldeDisponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, card.getNumero());
            ps.setDate(2, java.sql.Date.valueOf(card.getDateExpiration()));
            ps.setString(3, card.getStatut().toString());
            ps.setInt(4, card.getIdClient());
            ps.setString(5, card.getTypeCarte());
            if (card instanceof CarteDebit carteDebit) {
                ps.setDouble(6, carteDebit.getPlafondJournalier());
                ps.setNull(7, Types.DOUBLE);
                ps.setNull(8, Types.DOUBLE);
                ps.setNull(9, Types.DOUBLE);
            } else if (card instanceof CarteCredit creditCard) {
                ps.setNull(6, Types.DOUBLE);
                ps.setDouble(7, creditCard.getPlafondMensuel());
                ps.setDouble(8, creditCard.getTauxInteret());
                ps.setNull(9, Types.DOUBLE);
            } else if (card instanceof CartePrepayee prepCard) {
                ps.setNull(6, Types.DOUBLE);
                ps.setNull(7, Types.DOUBLE);
                ps.setNull(8, Types.DOUBLE);
                ps.setDouble(9, prepCard.getSoldeDisponible());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Card> findCardById(int id) {
        String sql = "Select * from Cards where id = ?";
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                Card card = null;
                switch (type) {
                    case "DEBIT":
                        card = new CarteDebit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondJournalier"));
                        break;
                    case "CREDIT":
                        card = new CarteCredit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondMensuel"),
                                rs.getDouble("tauxInteret"));
                        break;
                    case "PREPAYEE":
                        card = new CartePrepayee(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("soldeDisponible"));
                        break;

                }
                return Optional.of(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Card> findCardsByClientId(int idClient) {
        List<Card> cards = new ArrayList<>();
        String sql = "Select * from Cards where idClient = ?";
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Card card = null;
                switch (type) {
                    case "DEBIT":
                        card = new CarteDebit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondJournalier"));
                        break;
                    case "CREDIT":
                        card = new CarteCredit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondMensuel"),
                                rs.getDouble("tauxInteret"));
                        break;
                    case "PREPAYEE":
                        card = new CartePrepayee(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("soldeDisponible"));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown card type: " + type);
                }
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
    @Override
    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        String sql = "Select * from cards";
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Card card = null;
                switch (type) {
                    case "DEBIT":
                        card = new CarteDebit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondJournalier"));
                        break;
                    case "CREDIT":
                        card = new CarteCredit(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("plafondMensuel"),
                                rs.getDouble("tauxInteret"));
                        break;
                    case "PREPAYEE":
                        card = new CartePrepayee(
                                rs.getInt("id"),
                                rs.getString("numero"),
                                rs.getDate("dateExpiration").toLocalDate(),
                                StatutCarte.valueOf(rs.getString("statut")),
                                rs.getInt("idClient"),
                                rs.getDouble("soldeDisponible"));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown card type: " + type);
                }
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void deleteCard(int id){
        String sql = "delete from cards where id = ?";
        try(Connection con = DatabaseConnection.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void updateCardStatut(int id , StatutCarte status){
        String sql = "update cards set statut = ? where id = ?";
        try(Connection con = DatabaseConnection.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status.toString());
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Updated Successfully!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
