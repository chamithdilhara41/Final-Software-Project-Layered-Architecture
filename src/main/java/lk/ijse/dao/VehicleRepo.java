package lk.ijse.dao;

import javafx.scene.control.Alert;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Vehicle;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepo {

    public static boolean save(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle VALUES(?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, vehicle.getVehicleNo());
        pstm.setObject(2, vehicle.getVehicleType());

        return pstm.executeUpdate() > 0;
    }

    public static List<Vehicle> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM vehicle";

        List<Vehicle> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Vehicle(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }

    public static boolean update(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE vehicle SET type = ? WHERE vehicleNo = ?;";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(2, vehicle.getVehicleNo());
        pstm.setObject(1, vehicle.getVehicleType());

        return pstm.executeUpdate() > 0;
    }

    public static Vehicle searchByVehicleNo(String vehicleNO) throws SQLException {

        String sql = "SELECT * FROM vehicle WHERE vehicleNo = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, vehicleNO);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Found").showAndWait();
            String vehicleNo = resultSet.getString(1);
            String vehicleType = resultSet.getString(2);

            return new Vehicle(vehicleNo, vehicleType);
        }
        return null;
    }


    @Nullable
    public static Vehicle searchByVehicleNoForEmp(String vehicleNO) throws SQLException {

        String sql = "SELECT * FROM vehicle WHERE vehicleNo = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, vehicleNO);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String vehicleNo = resultSet.getString(1);
            String vehicleType = resultSet.getString(2);

            return new Vehicle(vehicleNo, vehicleType);
        }
        return null;
    }

    public static boolean delete(String vehicleNo) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE vehicleNo = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, vehicleNo);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getNos() throws SQLException {

        String sql = "SELECT vehicleNo FROM vehicle";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
