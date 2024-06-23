package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public boolean save(Payment payment) throws SQLException {

        String sql = "INSERT INTO payment VALUES(?,?,?,?,?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getDescription());
        pstm.setObject(3, payment.getAmount());
        pstm.setObject(4, payment.getDate());
        pstm.setObject(5, payment.getSupplierId());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(Payment payment) throws SQLException {
        String sql ="UPDATE payment SET description=?, amount=?, date=?, supplierId=? WHERE paymentId=?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, payment.getDescription());
        pstm.setObject(2, payment.getAmount());
        pstm.setObject(3, payment.getDate());
        pstm.setObject(4, payment.getSupplierId());
        pstm.setObject(5, payment.getPaymentId());
        return pstm.executeUpdate() > 0;


    }

    public boolean delete(String paymentID) throws SQLException {
        String sql = "DELETE FROM payment WHERE paymentId=?;";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, paymentID);

        return pstm.executeUpdate() > 0;
    }

    public Payment searchByPaymentId(String paymentID) throws SQLException {
        String sql = "SELECT * FROM payment WHERE paymentId=?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, paymentID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String paymentId = resultSet.getString("paymentId");
            String description = resultSet.getString("description");
            Double amount = Double.valueOf(resultSet.getString("amount"));
            String date = resultSet.getString("date");
            String supplierId = resultSet.getString("supplierId");

            return new Payment(paymentId,description,amount,date,supplierId);
        }
        return null;
    }

    public List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment;";
        List<Payment> payments = new ArrayList<>();
        ResultSet resultSet = DbConnection.getInstance().getConnection().createStatement().executeQuery(sql);
        while (resultSet.next()) {
            payments.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return payments;
    }

}
