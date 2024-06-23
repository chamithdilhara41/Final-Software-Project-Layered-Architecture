package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.PlaceOrderDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Order;
import lk.ijse.dto.OrderDetail;
import lk.ijse.dto.PlaceOrder;
import lk.ijse.dto.tdm.OrderBuyerTm;
import lk.ijse.dto.tdm.OrderStockTm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderDAOImpl implements OrderDAO, OrderDetailDAO, PlaceOrderDAO {

    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

    public boolean placeOrder(PlaceOrder po) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.save(po.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = orderDetailDAO.save(po.getOdList());
//                po.getOdList(getStockId)
                System.out.println(isOrderDetailSaved);
                if (isOrderDetailSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public boolean save(Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String orderID) throws SQLException {
        return false;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<String> getIds() throws SQLException {
        return List.of();
    }

    @Override
    public String getCurrentId() throws SQLException {
        return "";
    }

    @Override
    public List<OrderStockTm> getAllOrderStocks() throws SQLException {
        return List.of();
    }

    @Override
    public List<OrderBuyerTm> getAllOrderBuyerNames() throws SQLException {
        return List.of();
    }

    @Override
    public boolean save(List<OrderDetail> odList) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrderDetailsAndStocks(OrderDetail od) throws SQLException {
        return false;
    }

}
