package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.impl.OrderDAOImpl;
import lk.ijse.entity.Order;
import lk.ijse.tdm.OrderBuyerTm;
import lk.ijse.tdm.OrderStockTm;

import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String getCurrentIdOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getCurrentId();
    }

    @Override
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return orderDAO.save(order);
    }

    @Override
    public List<String> getIdsOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getIds();
    }

    @Override
    public List<OrderStockTm> getAllOrderStocksOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllOrderStocks();
    }

    @Override
    public List<OrderBuyerTm> getAllOrderBuyerNamesOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllOrderBuyerNames();
    }

    @Override
    public List<Order> getAllOrders() throws SQLException,ClassNotFoundException {
        return orderDAO.getAll();
    }

}
