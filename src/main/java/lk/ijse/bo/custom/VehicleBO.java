package lk.ijse.bo.custom;
;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleBO extends SuperBO {

    public boolean saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException ;

    public List<Vehicle> getAllVehicle() throws SQLException, ClassNotFoundException ;

    public boolean updateVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException ;

    public Vehicle searchByVehicleNoVehicle(String vehicleNO) throws SQLException, ClassNotFoundException ;

    public Vehicle searchByVehicleNoForEmpVehicle(String vehicleNO) throws SQLException, ClassNotFoundException ;

    public boolean deleteVehicle(String vehicleNo) throws SQLException, ClassNotFoundException ;

    public List<String> getNosVehicle() throws SQLException, ClassNotFoundException ;
}
