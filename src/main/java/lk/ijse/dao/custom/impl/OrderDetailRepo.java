package lk.ijse.dao.custom.impl;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {

    public static boolean save(List<OrderDetail> odList) throws SQLException {
        System.out.println(odList);
        for (OrderDetail od : odList) {
            boolean isSaved = saveOrderDetailsAndStocks(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrderDetailsAndStocks(OrderDetail od) throws SQLException {
        String sql = "INSERT INTO ordersstockinfo VALUES(?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, od.getStockID());
        pstm.setObject(2, od.getOrderID());
        pstm.setObject(3, od.getBuyerID());


        String sql1 = "UPDATE stock SET status = 'inactive' WHERE stockId = ?";
        PreparedStatement pstm1 = DbConnection.getInstance().getConnection().prepareStatement(sql1);

        pstm1.setObject(1, od.getStockID());
        pstm1.executeUpdate();


        return pstm.executeUpdate() > 0 ;    //false ->  |

    }
}
