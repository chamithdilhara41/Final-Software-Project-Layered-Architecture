package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Payment;
import lk.ijse.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?);",
                payment.getPaymentId(),
                payment.getDescription(),
                payment.getAmount(),
                payment.getDate(),
                payment.getSupplierId()
        );
    }

    public boolean update(Payment payment) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE payment SET description=?, amount=?, date=?, supplierId=? WHERE paymentId=?;",
                payment.getDescription(),
                payment.getAmount(),
                payment.getDate(),
                payment.getSupplierId(),
                payment.getPaymentId()
        );

    }

    public boolean delete(String paymentID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM payment WHERE paymentId=?;",paymentID);
    }

    public Payment searchByPaymentId(String paymentID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE paymentId=?;",paymentID);
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

    public List<Payment> getAll() throws SQLException, ClassNotFoundException {

        List<Payment> payments = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment;");
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
