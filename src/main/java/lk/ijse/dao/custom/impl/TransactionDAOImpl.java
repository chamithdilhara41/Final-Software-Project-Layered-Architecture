package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    public boolean save(Transaction transaction) throws SQLException {
        String sql = "insert into transection values(?,?,?,?,?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,transaction.getTransactionId());
        pstm.setObject(2,transaction.getDescription());
        pstm.setObject(3,transaction.getAmount());
        pstm.setObject(4,transaction.getDate());
        pstm.setObject(5,transaction.getAccountNo());
        pstm.setObject(6,transaction.getMethod());
        pstm.setObject(7,transaction.getOrderId());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(Transaction transaction) throws SQLException {
        String sql = "UPDATE transection SET description = ?,amount = ?,date = ?,accountNo = ?,method = ?,orderId = ? WHERE transectionId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, transaction.getDescription());
        pstm.setObject(2, transaction.getAmount());
        pstm.setObject(3, transaction.getDate());
        pstm.setObject(4, transaction.getAccountNo());
        pstm.setObject(5, transaction.getMethod());
        pstm.setObject(6, transaction.getOrderId());
        pstm.setObject(7, transaction.getTransactionId());

        return pstm.executeUpdate() > 0;

    }

    public boolean delete(String transactionID) throws SQLException {
        String sql = "DELETE FROM transection WHERE transectionId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, transactionID);

        return pstm.executeUpdate() > 0;
    }

    public List<Transaction> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "select * from transection";
        List<Transaction> data = new ArrayList<>();
        ResultSet resultSet = con.createStatement().executeQuery(sql);
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

    public Transaction searchByTransactionId(String transactionID) throws SQLException {
        String sql = "select * from transection where transectionId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, transactionID);
        ResultSet resultSet = pstm.executeQuery();
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
