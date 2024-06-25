package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Delivery;
import lk.ijse.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {

    public boolean save(Delivery delivery) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO delivery VALUES(?,?,?,?)",
                delivery.getDeliveryId(),
                delivery.getDate(),
                delivery.getOrderId(),
                delivery.getVehicleNo()
        );

    }

    public boolean update(Delivery delivery) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE delivery SET date =?,orderId =?,vehicleNo =? WHERE deliveryId =?",
                delivery.getDate(),
                delivery.getOrderId(),
                delivery.getVehicleNo(),
                delivery.getDeliveryId()
        );
    }

    public boolean delete(String deliveryID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM delivery WHERE deliveryId =?",deliveryID);
    }

    public Delivery searchByDeliveryId(String deliveryID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM delivery WHERE deliveryId =?",deliveryID);
        if(resultSet.next()){
            resultSet.getString(1);
            resultSet.getString(2);
            resultSet.getString(3);
            resultSet.getString(4);

            return new Delivery(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }
        return null;
    }

    public List<Delivery> getAll() throws SQLException, ClassNotFoundException {

        List<Delivery> deliveryList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM delivery");
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
