package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.DeliveryDTO;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryBO extends SuperBO {

    public boolean saveDelivery(DeliveryDTO delivery) throws SQLException, ClassNotFoundException ;

    public boolean updateDelivery(DeliveryDTO delivery) throws SQLException, ClassNotFoundException ;

    public boolean deleteDelivery(String deliveryID) throws SQLException, ClassNotFoundException ;

    public Delivery searchByDeliveryIdDelivery(String deliveryID) throws SQLException, ClassNotFoundException ;

    public List<DeliveryDTO> getAllDelivery() throws SQLException, ClassNotFoundException ;

}
