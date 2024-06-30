package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);

    @Override
    public boolean saveDelivery(Delivery delivery) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(delivery);
    }

    @Override
    public boolean updateDelivery(Delivery delivery) throws SQLException, ClassNotFoundException {
        return deliveryDAO.update(delivery);
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
    public List<Delivery> getAllDelivery() throws SQLException, ClassNotFoundException {
        return deliveryDAO.getAll();
    }
}
