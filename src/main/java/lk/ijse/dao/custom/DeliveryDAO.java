package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Delivery;

import java.sql.SQLException;

public interface DeliveryDAO extends CrudDAO<Delivery> {

    //public boolean save(Delivery delivery) throws SQLException;

    //public boolean update(Delivery delivery) throws SQLException;

    //public boolean delete(String deliveryID) throws SQLException;

    //public List<Delivery> getAll() throws SQLException;

    public Delivery searchByDeliveryId(String deliveryID) throws SQLException, ClassNotFoundException;


}
