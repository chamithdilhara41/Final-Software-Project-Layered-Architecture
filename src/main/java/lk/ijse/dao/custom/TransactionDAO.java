package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Transaction;

import java.sql.SQLException;

public interface TransactionDAO extends CrudDAO<Transaction> {

    //public boolean save(Transaction transaction) throws SQLException ;

    //public boolean update(Transaction transaction) throws SQLException ;

    //public boolean delete(String transactionID) throws SQLException ;

    //public List<Transaction> getAll() throws SQLException ;

    public Transaction searchByTransactionId(String transactionID) throws SQLException, ClassNotFoundException;

}
