package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.TransactionDTO;
import lk.ijse.entity.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionBO extends SuperBO {


    public boolean saveTransaction(TransactionDTO transaction) throws SQLException, ClassNotFoundException ;

    public boolean updateTransaction(TransactionDTO transaction) throws SQLException, ClassNotFoundException ;

    public boolean deleteTransaction(String transactionID) throws SQLException, ClassNotFoundException ;

    public List<TransactionDTO> getAllTransaction() throws SQLException, ClassNotFoundException ;

    public Transaction searchByTransactionIdTransaction(String transactionID) throws SQLException, ClassNotFoundException ;

}
