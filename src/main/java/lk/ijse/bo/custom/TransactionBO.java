package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionBO extends SuperBO {


    public boolean saveTransaction(Transaction transaction) throws SQLException, ClassNotFoundException ;

    public boolean updateTransaction(Transaction transaction) throws SQLException, ClassNotFoundException ;

    public boolean deleteTransaction(String transactionID) throws SQLException, ClassNotFoundException ;

    public List<Transaction> getAllTransaction() throws SQLException, ClassNotFoundException ;

    public Transaction searchByTransactionIdTransaction(String transactionID) throws SQLException, ClassNotFoundException ;

}
