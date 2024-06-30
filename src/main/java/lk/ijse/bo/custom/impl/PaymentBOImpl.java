package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean savePayment(Payment payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(Payment payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String paymentID) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentID);
    }

    @Override
    public Payment searchByPaymentIdPayment(String paymentID) throws SQLException, ClassNotFoundException {
        return paymentDAO.searchByPaymentId(paymentID);
    }

    @Override
    public List<Payment> getAllPayment() throws SQLException, ClassNotFoundException {
        return paymentDAO.getAll();
    }
}
