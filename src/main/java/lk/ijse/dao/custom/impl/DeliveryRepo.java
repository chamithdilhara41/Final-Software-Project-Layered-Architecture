package lk.ijse.dao.custom.impl;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepo {
    public static boolean save(Delivery delivery) throws SQLException {

        String sql = "INSERT INTO delivery VALUES(?,?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, delivery.getDeliveryId());
        pstm.setObject(2,delivery.getDate());
        pstm.setObject(3,delivery.getOrderId());
        pstm.setObject(4,delivery.getVehicleNo());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Delivery delivery) throws SQLException {

        String sql = "UPDATE delivery SET date =?,orderId =?,vehicleNo =? WHERE deliveryId =?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, delivery.getDate());
        pstm.setObject(2, delivery.getOrderId());
        pstm.setObject(3, delivery.getVehicleNo());
        pstm.setObject(4,delivery.getDeliveryId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String deliveryID) throws SQLException {

        String sql = "DELETE FROM delivery WHERE deliveryId =?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, deliveryID);

        return pstm.executeUpdate() > 0;
    }

    public static Delivery searchByDeliveryId(String deliveryID) throws SQLException {
        String sql = "SELECT * FROM delivery WHERE deliveryId =?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,deliveryID);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            resultSet.getString(1);
            resultSet.getString(2);
            resultSet.getString(3);
            resultSet.getString(4);

            return new Delivery(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }
        return null;
    }

    public static List<Delivery> getAll() throws SQLException {
         String sql = "SELECT * FROM delivery";
         List<Delivery> deliveryList = new ArrayList<Delivery>();
        ResultSet resultSet = DbConnection.getInstance().getConnection().createStatement().executeQuery(sql);
        while(resultSet.next()){
            deliveryList.add(new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return deliveryList;
    }
}
