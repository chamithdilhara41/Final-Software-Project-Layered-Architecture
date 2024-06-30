package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.util.SQLUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    public boolean save(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        System.out.println(odList);
        for (OrderDetail od : odList) {
            boolean isSaved = saveOrderDetailsAndStocks(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean saveOrderDetailsAndStocks(OrderDetail od) throws SQLException, ClassNotFoundException {

        SQLUtil.execute("UPDATE stock SET status = 'inactive' WHERE stockId = ?", od.getStockID());

        return SQLUtil.execute("INSERT INTO ordersstockinfo VALUES(?, ?, ?)",
                od.getStockID(),
                od.getOrderID(),
                od.getBuyerID()
        );
        //false ->  |
    }



    @Override
    public boolean save(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
