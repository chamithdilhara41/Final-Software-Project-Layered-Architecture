package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TransactionBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.dao.custom.impl.TransactionDAOImpl;
import lk.ijse.entity.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TransactionBOImpl implements TransactionBO {

    TransactionDAO transactionDAO = (TransactionDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);

    @Override
    public boolean saveTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        return transactionDAO.save(transaction);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        return transactionDAO.update(transaction);
    }

    @Override
    public boolean deleteTransaction(String transactionID) throws SQLException, ClassNotFoundException {
        return transactionDAO.delete(transactionID);
    }

    @Override
    public List<Transaction> getAllTransaction() throws SQLException, ClassNotFoundException {
        return transactionDAO.getAll();
    }

    @Override
    public Transaction searchByTransactionIdTransaction(String transactionID) throws SQLException, ClassNotFoundException {
        return transactionDAO.searchByTransactionId(transactionID);
    }
}
