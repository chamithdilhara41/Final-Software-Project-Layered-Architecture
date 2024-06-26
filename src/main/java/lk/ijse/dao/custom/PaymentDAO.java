package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<Payment> {

    //public boolean save(Payment payment) throws SQLException ;

    //public boolean update(Payment payment) throws SQLException ;

    //public boolean delete(String paymentID) throws SQLException ;

    //public List<Payment> getAll() throws SQLException ;

    public Payment searchByPaymentId(String paymentID) throws SQLException, ClassNotFoundException;

}
