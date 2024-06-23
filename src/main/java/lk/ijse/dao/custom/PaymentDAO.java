package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDAO extends CrudDAO {

    public boolean save(Payment payment) throws SQLException ;

    public boolean update(Payment payment) throws SQLException ;

    public boolean delete(String paymentID) throws SQLException ;

    public Payment searchByPaymentId(String paymentID) throws SQLException ;

    public List<Payment> getAll() throws SQLException ;

}
