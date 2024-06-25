package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.PlaceOrder;

import java.sql.SQLException;

public interface PlaceOrderDAO extends CrudDAO<PlaceOrder> {

    public boolean placeOrder(PlaceOrder po) throws SQLException ;

    //public boolean save(Order order) throws SQLException ;

    //public boolean update(Order order) throws SQLException ;

    //public boolean delete(String orderID) throws SQLException ;

    //public List<Order> getAll() throws SQLException ;

    //public List<String> getIds() throws SQLException ;

    //public String getCurrentId() throws SQLException ;

    //public List<OrderStockTm> getAllOrderStocks() throws SQLException ;

    //public List<OrderBuyerTm> getAllOrderBuyerNames() throws SQLException ;

    //public boolean save(List<OrderDetail> odList) throws SQLException ;

}
