package lk.ijse.dao.custom;

import javafx.scene.control.Alert;
import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface VehicleDAO extends CrudDAO {

    public boolean save(Vehicle vehicle) throws SQLException ;

    public List<Vehicle> getAll() throws SQLException ;

    public boolean update(Vehicle vehicle) throws SQLException ;

    public Vehicle searchByVehicleNo(String vehicleNO) throws SQLException ;

    public Vehicle searchByVehicleNoForEmp(String vehicleNO) throws SQLException ;

    public boolean delete(String vehicleNo) throws SQLException ;

    public List<String> getNos() throws SQLException ;
}
