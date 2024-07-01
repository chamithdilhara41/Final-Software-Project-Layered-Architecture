package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TransactionBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.dao.custom.impl.TransactionDAOImpl;
import lk.ijse.dto.TransactionDTO;
import lk.ijse.entity.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionBOImpl implements TransactionBO {

    TransactionDAO transactionDAO = (TransactionDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);

    @Override
    public boolean saveTransaction(TransactionDTO transaction) throws SQLException, ClassNotFoundException {
        return transactionDAO.save(new Transaction(transaction.getTransactionId(), transaction.getOrderId(), transaction.getAccountNo(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), transaction.getMethod()));
    }

    @Override
    public boolean updateTransaction(TransactionDTO transaction) throws SQLException, ClassNotFoundException {
        return transactionDAO.update(new Transaction(transaction.getTransactionId(), transaction.getOrderId(), transaction.getAccountNo(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), transaction.getMethod()));
    }

    @Override
    public boolean deleteTransaction(String transactionID) throws SQLException, ClassNotFoundException {
        return transactionDAO.delete(transactionID);
    }

    @Override
    public List<TransactionDTO> getAllTransaction() throws SQLException, ClassNotFoundException {
        List<Transaction> transactions = transactionDAO.getAll();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDTOs.add(new TransactionDTO(
                    transaction.getTransactionId(),
                    transaction.getOrderId(),
                    transaction.getAccountNo(),
                    transaction.getDescription(),
                    transaction.getAmount(),
                    transaction.getDate(),
                    transaction.getMethod()
            ));
        }
        return transactionDTOs;
    }

    @Override
    public Transaction searchByTransactionIdTransaction(String transactionID) throws SQLException, ClassNotFoundException {
        return transactionDAO.searchByTransactionId(transactionID);
    }
}
