package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {

    public boolean savePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException ;

    public boolean updatePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException ;

    public boolean deletePayment(String paymentID) throws SQLException, ClassNotFoundException ;

    public Payment searchByPaymentIdPayment(String paymentID) throws SQLException, ClassNotFoundException ;

    public List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException ;

}
