package Modal;

import java.sql.Timestamp;

public record Operation(int id , Timestamp date , double montant , String type , String lieu , int carteId) {

    public Operation(double montant, Timestamp date ,  String type , String lieu , int carteId){
        this(0 , null , montant , type, lieu , carteId);
    }
}
