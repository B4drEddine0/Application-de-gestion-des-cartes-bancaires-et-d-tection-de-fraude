package Dao;

import Modal.Card;
import Modal.StatutCarte;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    void insertCard(Card card);
    Optional<Card> findCardById(int id);

    List<Card> findCardsByClientId(int idClient);

    List<Card> getAllCards();

    

    void updateCardStatut(int id, StatutCarte statut);

    void deleteCard(int id);
}
