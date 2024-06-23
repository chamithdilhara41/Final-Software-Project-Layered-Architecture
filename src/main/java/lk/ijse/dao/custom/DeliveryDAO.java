package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DeliveryDAO extends CrudDAO {

    public boolean save(Delivery delivery) throws SQLException;

    public boolean update(Delivery delivery) throws SQLException;

    public boolean delete(String deliveryID) throws SQLException;

    public Delivery searchByDeliveryId(String deliveryID) throws SQLException;

    public List<Delivery> getAll() throws SQLException;

}
