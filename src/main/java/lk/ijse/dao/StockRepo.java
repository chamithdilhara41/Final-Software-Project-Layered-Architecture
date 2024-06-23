package lk.ijse.dao;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.Stock;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static boolean save(Stock stock) throws SQLException {
        String sql = "insert into stock values(?,?,?,'active')";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,stock.getStockId());
        pstm.setObject(2,stock.getWeight());
        pstm.setObject(3,stock.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static List<Stock> getAll() throws SQLException {
        String sql = "select * from stock";

        List<Stock> data = new ArrayList<Stock>();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDate(3)
            ));
        }
        return data;
    }

    public static boolean updateWeight(String stockID, String supplierID, Double weight) throws SQLException {
        String sql = "update stock set TotalWeight = TotalWeight + ? where stockID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,weight);
        pstm.setObject(2,stockID);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT stockId FROM stock WHERE status ='active'";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public static Stock searchByStockIdForOrder(String no) throws SQLException {
        String sql = "select * from stock where stockID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,no);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String stockId = resultSet.getString(1);
            Double weight = resultSet.getDouble(2);
            Date date = resultSet.getDate(3);

            return new Stock(stockId, weight, date);
        }
        return null;
    }

    public static boolean delete(String stockID) throws SQLException {

        String sql = "delete from stock where stockID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,stockID);
        return pstm.executeUpdate() > 0;

    }
}
