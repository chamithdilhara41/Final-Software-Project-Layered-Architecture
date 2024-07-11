package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QueryDAOImpl implements QueryDAO {

    public int employeeCount() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS employee_count FROM employee");
        if(resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;
    }

    public int supplierCount() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS supplier_count FROM supplier");

        if(resultSet.next()) {
            return resultSet.getInt("supplier_count");
        }
        return 0;
    }

    public int buyerCount() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS buyer_count FROM buyer");

        if(resultSet.next()) {
            return resultSet.getInt("buyer_count");
        }
        return 0;
    }

    public Map<String, Double> stocksByDate() throws SQLException {

        Map<String, Double> stocksByDay = new HashMap<>();

        String sql = " SELECT date,SUM(TotalWeight) AS total_weight FROM stock GROUP BY date;";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                double totalWeight = resultSet.getDouble("total_weight");
                stocksByDay.put(date, totalWeight);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stocksByDay;
    }

}
