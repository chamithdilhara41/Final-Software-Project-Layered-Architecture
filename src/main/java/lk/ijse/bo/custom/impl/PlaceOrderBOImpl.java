package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.impl.OrderDAOImpl;
import lk.ijse.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {


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
        } catch (SQLException | ClassNotFoundException e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

}
