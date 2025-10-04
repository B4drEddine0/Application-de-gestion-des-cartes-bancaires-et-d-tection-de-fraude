package Service;

import java.util.Optional;

import Dao.CardRepository;
import Dao.OperationRepository;
import Modal.Card;
import Modal.CarteCredit;
import Modal.CarteDebit;
import Modal.CartePrepayee;
import Modal.Operation;

public class OperationService {
    private final OperationRepository operationRepo;
    private final CardRepository cardRepo;
    public OperationService(OperationRepository operationRepo , CardRepository cardRepo){
        this.operationRepo = operationRepo;
        this.cardRepo = cardRepo;
    }

    public Boolean insertOperation(int cardId , double montant , String type , String lieu){
        Optional<Card> optionalCard = cardRepo.findCardById(cardId);
        if(optionalCard.isEmpty()){
            System.out.println("Card Doesn't Exist!");
            return false;
        }
        Card card = optionalCard.get();
        if(!card.isValid()){
            System.out.println("Card Is Not Valid");
            return false;
        }

        if(card instanceof CarteCredit credit){
            if(credit.isDansPlafond(montant)){
                System.out.println("Amount Exceeds Monthly Credit limit!");
            }
        }
        else if(card instanceof CarteDebit debit){
            if(debit.isDansPlafond(montant)){
                System.out.println("Amount exceeds daily limit!");
            }
        }
        else if(card instanceof CartePrepayee prepaid){
            if(prepaid.aSoldeSuffisant(montant)){
                System.out.println("Insufficient balance");
            }
        }

        Operation operation = new Operation(montant , null , type, lieu, cardId);
        operationRepo.saveOperation(operation);
        System.out.println("Operation Saved Successfully !");
        return true;
    }
}
