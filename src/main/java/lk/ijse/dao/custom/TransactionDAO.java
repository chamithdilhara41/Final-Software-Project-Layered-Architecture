package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TransactionDAO extends CrudDAO {

    public boolean save(Transaction transaction) throws SQLException ;

    public boolean update(Transaction transaction) throws SQLException ;

    public boolean delete(String transactionID) throws SQLException ;

    public List<Transaction> getAll() throws SQLException ;

    public Transaction searchByTransactionId(String transactionID) throws SQLException ;

}
