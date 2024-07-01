package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(payment.getPaymentId(), payment.getDescription(), payment.getAmount(), payment.getDate(), payment.getSupplierId()));
    }

    @Override
    public boolean updatePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(payment.getPaymentId(), payment.getDescription(), payment.getAmount(), payment.getDate(), payment.getSupplierId()));
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
    public List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOs.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getDescription(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getSupplierId()
            ));
        }
        return paymentDTOs;
    }
}
