package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO extends CrudDAO {

    public boolean save(Transaction transaction) throws SQLException ;

    public boolean update(Transaction transaction) throws SQLException ;

    public boolean delete(String transactionID) throws SQLException ;

    public List<Transaction> getAll() throws SQLException ;

    public Transaction searchByTransactionId(String transactionID) throws SQLException ;

}
