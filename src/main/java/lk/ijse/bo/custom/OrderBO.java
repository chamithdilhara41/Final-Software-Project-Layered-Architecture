package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Order;
import lk.ijse.tdm.OrderBuyerTm;
import lk.ijse.tdm.OrderStockTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {

    public String getCurrentIdOrder() throws SQLException, ClassNotFoundException;

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsOrder() throws SQLException, ClassNotFoundException;

    public List<OrderStockTm> getAllOrderStocksOrder() throws SQLException, ClassNotFoundException;

    public List<OrderBuyerTm> getAllOrderBuyerNamesOrder() throws SQLException, ClassNotFoundException;

    public List<Order> getAllOrders() throws SQLException,ClassNotFoundException;

}
