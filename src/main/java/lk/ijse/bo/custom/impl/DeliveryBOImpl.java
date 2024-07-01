package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.dto.DeliveryDTO;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);

    @Override
    public boolean saveDelivery(DeliveryDTO delivery) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(new Delivery(delivery.getDeliveryId(),delivery.getDate(),delivery.getOrderId(),delivery.getVehicleNo()));
    }

    @Override
    public boolean updateDelivery(DeliveryDTO delivery) throws SQLException, ClassNotFoundException {
        return deliveryDAO.update(new Delivery(delivery.getDeliveryId(),delivery.getDate(),delivery.getOrderId(),delivery.getVehicleNo()));
    }

    @Override
    public boolean deleteDelivery(String deliveryID) throws SQLException, ClassNotFoundException {
        return deliveryDAO.delete(deliveryID);
    }

    @Override
    public Delivery searchByDeliveryIdDelivery(String deliveryID) throws SQLException, ClassNotFoundException {
        return deliveryDAO.searchByDeliveryId(deliveryID);
    }

    @Override
    public List<DeliveryDTO> getAllDelivery() throws SQLException, ClassNotFoundException {
         List<Delivery> deliveryList = deliveryDAO.getAll();
         List<DeliveryDTO> deliveryDTOList = new ArrayList<>();
         for (Delivery delivery : deliveryList) {
             deliveryDTOList.add(new DeliveryDTO(
                     delivery.getDeliveryId(),
                     delivery.getDate(),
                     delivery.getOrderId(),
                     delivery.getVehicleNo()
             ));
         }
         return deliveryDTOList;
    }
}
