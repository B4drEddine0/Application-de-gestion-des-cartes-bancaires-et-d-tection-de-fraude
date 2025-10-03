package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import Modal.DatabaseConnection;
import Modal.Operation;

public class FraudDao implements FraudRepository {

    @Override
    public Optional<Operation> getLastOperationById(int carteId){
        String sql = "Select date_operation, lieu from Operations where id_carte = ? ORDER BY date_operation desc limit 1";
        try(Connection con = DatabaseConnection.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, carteId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Timestamp date = rs.getTimestamp("date_operation");
                String lieu = rs.getString("lieu");
                Operation op = new Operation(0, date , "", lieu, 0);
                return Optional.of(op);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
