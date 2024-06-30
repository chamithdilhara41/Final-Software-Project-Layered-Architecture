package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.entity.Vehicle;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    public boolean save(Vehicle vehicle) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO vehicle VALUES(?, ?);",
                vehicle.getVehicleNo(),
                vehicle.getVehicleType()
        );
    }

    public List<Vehicle> getAll() throws SQLException, ClassNotFoundException {

        List<Vehicle> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vehicle");
        while (resultSet.next()) {
            data.add(new Vehicle(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE vehicle SET type = ? WHERE vehicleNo = ?;",
                vehicle.getVehicleType(),
                vehicle.getVehicleNo()
                );
    }

    public Vehicle searchByVehicleNo(String vehicleNO) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vehicle WHERE vehicleNo = ?;",vehicleNO);
        if (resultSet.next()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Found").showAndWait();
            String vehicleNo = resultSet.getString(1);
            String vehicleType = resultSet.getString(2);

            return new Vehicle(vehicleNo, vehicleType);
        }
        return null;
    }

    public Vehicle searchByVehicleNoForEmp(String vehicleNO) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vehicle WHERE vehicleNo = ?;",vehicleNO);
        if (resultSet.next()) {
            String vehicleNo = resultSet.getString(1);
            String vehicleType = resultSet.getString(2);

            return new Vehicle(vehicleNo, vehicleType);
        }
        return null;
    }

    public boolean delete(String vehicleNo) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM vehicle WHERE vehicleNo = ?;",vehicleNo);
    }

    public List<String> getNos() throws SQLException, ClassNotFoundException {

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT vehicleNo FROM vehicle");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

}
