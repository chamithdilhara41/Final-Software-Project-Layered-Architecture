package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryBO extends SuperBO {

    public boolean saveDelivery(Delivery delivery) throws SQLException, ClassNotFoundException ;

    public boolean updateDelivery(Delivery delivery) throws SQLException, ClassNotFoundException ;

    public boolean deleteDelivery(String deliveryID) throws SQLException, ClassNotFoundException ;

    public Delivery searchByDeliveryIdDelivery(String deliveryID) throws SQLException, ClassNotFoundException ;

    public List<Delivery> getAllDelivery() throws SQLException, ClassNotFoundException ;

}
