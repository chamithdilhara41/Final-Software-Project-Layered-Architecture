package lk.ijse.dao;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.Order;
import lk.ijse.dto.dtm.OrderBuyerTm;
import lk.ijse.dto.dtm.OrderStockTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getOrderId());
        pstm.setObject(2, order.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET date = ? WHERE orderId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getDate());
        pstm.setObject(2, order.getOrderId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String orderID) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, orderID);

        return pstm.executeUpdate() > 0;
    }


    public static List<Order> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<Order> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT orderId FROM orders";

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

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static List<OrderStockTm> getAllOrderStocks() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM ordersstockinfo";

        List<OrderStockTm> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new OrderStockTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static List<OrderBuyerTm> getAllOrderBuyerNames() throws SQLException {
        String sql = "SELECT DISTINCT osi.orderId, b.name AS buyerName FROM ordersstockinfo osi JOIN buyer b ON osi.buyerId = b.buyerId;";
        Connection con = DbConnection.getInstance().getConnection();

        List<OrderBuyerTm> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new OrderBuyerTm(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }
}
