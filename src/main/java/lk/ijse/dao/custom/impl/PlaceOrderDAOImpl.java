package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.PlaceOrderDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PlaceOrderDAOImpl implements PlaceOrderDAO {

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



    @Override
    public boolean save(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(PlaceOrder dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<PlaceOrder> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
