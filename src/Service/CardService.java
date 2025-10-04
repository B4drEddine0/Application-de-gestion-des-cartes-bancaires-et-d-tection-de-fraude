package Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import Dao.CardRepository;
import Modal.Card;
import Modal.CarteCredit;
import Modal.CarteDebit;
import Modal.CartePrepayee;
import Modal.StatutCarte;

public class CardService {
    private final CardRepository cardRepo;

    public CardService(CardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public void createDebitCard(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondJournalier) {
        CarteDebit card = new CarteDebit(dateExpiration, statut, idClient, plafondJournalier);
        cardRepo.insertCard(card);
    }

    public void createCreditCard(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double plafondMensuel, double tauxInteret) {
        CarteCredit card = new CarteCredit(dateExpiration, statut, idClient, plafondMensuel, tauxInteret);
        cardRepo.insertCard(card);
    }

    public void createPrepayeeCard(LocalDate dateExpiration, StatutCarte statut, int idClient,
            double soldeDisponible) {
        CartePrepayee card = new CartePrepayee(dateExpiration, statut, idClient, soldeDisponible);
        cardRepo.insertCard(card);
    }

    public Optional<Card> getCardById(int id){
        return cardRepo.findCardById(id);
    }
    public List<Card> getClientCards(int clientId){
        return cardRepo.findCardsByClientId(clientId);
    }
    public void deleteCardById(int id){
        cardRepo.deleteCard(id);
    }
    public void UpdateCardStatus(int id , StatutCarte status){
        cardRepo.updateCardStatut(id, status);
    }
}
