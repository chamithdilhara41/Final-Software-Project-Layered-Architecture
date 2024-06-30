package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;
import lk.ijse.tdm.OrderBuyerTm;
import lk.ijse.tdm.OrderStockTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {

    public boolean save(Order order) throws SQLException, ClassNotFoundException ;

    //public boolean update(Order order) throws SQLException ;

    //public boolean delete(String orderID) throws SQLException ;

    //public List<Order> getAll() throws SQLException ;

    public List<String> getIds() throws SQLException, ClassNotFoundException;

    public String getCurrentId() throws SQLException, ClassNotFoundException;

    public List<OrderStockTm> getAllOrderStocks() throws SQLException, ClassNotFoundException;

    public List<OrderBuyerTm> getAllOrderBuyerNames() throws SQLException, ClassNotFoundException;

}
