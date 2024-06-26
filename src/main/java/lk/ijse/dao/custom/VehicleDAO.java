package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle> {

    //public boolean save(Vehicle vehicle) throws SQLException ;

    //public List<Vehicle> getAll() throws SQLException ;

    ///public boolean update(Vehicle vehicle) throws SQLException ;

    //public boolean delete(String vehicleNo) throws SQLException ;

    public Vehicle searchByVehicleNo(String vehicleNO) throws SQLException, ClassNotFoundException;

    public Vehicle searchByVehicleNoForEmp(String vehicleNO) throws SQLException, ClassNotFoundException;

    public List<String> getNos() throws SQLException, ClassNotFoundException;
}
