package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Order;
import lk.ijse.dto.tdm.OrderBuyerTm;
import lk.ijse.dto.tdm.OrderStockTm;
import lk.ijse.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public boolean save(Order order) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO orders VALUES (?,?);",order.getOrderId(),order.getDate());
    }

    public boolean update(Order order) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE orders SET date = ? WHERE orderId = ?;",order.getDate(),order.getOrderId());
    }

    public boolean delete(String orderID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM orders WHERE orderId = ?;",orderID);
    }


    public List<Order> getAll() throws SQLException, ClassNotFoundException {

        List<Order> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders");
        while (resultSet.next()) {
            data.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public String getCurrentId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public List<OrderStockTm> getAllOrderStocks() throws SQLException, ClassNotFoundException {

        List<OrderStockTm> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM ordersstockinfo");
        while (resultSet.next()) {
            data.add(new OrderStockTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public List<OrderBuyerTm> getAllOrderBuyerNames() throws SQLException, ClassNotFoundException {

        List<OrderBuyerTm> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT osi.orderId, b.name AS buyerName FROM ordersstockinfo osi JOIN buyer b ON osi.buyerId = b.buyerId;");
        while (resultSet.next()) {
            data.add(new OrderBuyerTm(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

}
