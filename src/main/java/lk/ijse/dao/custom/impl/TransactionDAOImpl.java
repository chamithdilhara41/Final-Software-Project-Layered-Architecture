package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.entity.Transaction;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    public boolean save(Transaction transaction) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("insert into transection values(?,?,?,?,?,?,?)",
                transaction.getTransactionId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getAccountNo(),
                transaction.getMethod(),
                transaction.getOrderId()
        );
    }

    public boolean update(Transaction transaction) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE transection SET description = ?,amount = ?,date = ?,accountNo = ?,method = ?,orderId = ? WHERE transectionId = ?",
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getAccountNo(),
                transaction.getMethod(),
                transaction.getOrderId(),
                transaction.getTransactionId()
        );

    }

    public boolean delete(String transactionID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM transection WHERE transectionId = ?",transactionID);
    }

    public List<Transaction> getAll() throws SQLException, ClassNotFoundException {

        List<Transaction> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("select * from transection");
        while (resultSet.next()) {
            data.add(new Transaction(
                    resultSet.getString(1),
                    resultSet.getString(7),
                    resultSet.getString(5),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public Transaction searchByTransactionId(String transactionID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("select * from transection where transectionId = ?",transactionID);
        if (resultSet.next()) {
            String transectionId = resultSet.getString(1);
            String orderId = resultSet.getString(7);
            String accountNo = resultSet.getString(5);
            String description = resultSet.getString(2);
            Double amount = resultSet.getDouble(3);
            String date = resultSet.getString(4);
            String method = resultSet.getString(6);

            return new Transaction(transectionId,orderId,accountNo,description,amount,date,method);
        }
        return null;
    }

}
