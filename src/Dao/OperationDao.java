package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modal.DatabaseConnection;
import Modal.Operation;

public class OperationDao implements OperationRepository {

    @Override
    public void saveOperation(Operation operation){
        String sql = "Insert into Operations (id_carte , type_operation , montant , lieu) values (?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, operation.carteId());
            ps.setString(2, operation.type());
            ps.setDouble(3, operation.montant());
            ps.setString(4, operation.lieu());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
