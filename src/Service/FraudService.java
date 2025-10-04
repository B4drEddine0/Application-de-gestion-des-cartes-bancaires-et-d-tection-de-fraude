package Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import Dao.FraudRepository;
import Modal.Operation;

public class FraudService {
    private final FraudRepository fraudRepo;
    public FraudService(FraudRepository fraudRepo){
        this.fraudRepo = fraudRepo;
    }

    public Boolean checkFraud(int carteId , String lieu){
        Optional<Operation> lastOperation =  fraudRepo.getLastOperationById(carteId);
        if(lastOperation.isEmpty()){
            System.out.println("No Operation yet");
            return true;
        }
        String place = lastOperation.get().lieu();
        Timestamp date = lastOperation.get().date();
        LocalDateTime lastTime = date.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Boolean isLocationChanged = !place.equals(lieu);
        Boolean tooSoon = lastTime.plusMinutes(3).isAfter(now);
        if(isLocationChanged && tooSoon){
            System.out.println("Location Changed And too soon after last operation");
            return false;
        }
        return true;
    }
}
