package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;
import java.util.Map;

public interface QueryDAO extends SuperDAO {

    public int employeeCount() throws SQLException, ClassNotFoundException;

    public int supplierCount() throws SQLException, ClassNotFoundException;

    public int buyerCount() throws SQLException, ClassNotFoundException;

    public Map<String, Double> stocksByDate() throws SQLException ;

}
