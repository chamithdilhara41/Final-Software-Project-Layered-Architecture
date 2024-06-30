package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicle() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAll();
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(vehicle);
    }

    @Override
    public Vehicle searchByVehicleNoVehicle(String vehicleNO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.searchByVehicleNo(vehicleNO);
    }

    @Override
    public Vehicle searchByVehicleNoForEmpVehicle(String vehicleNO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.searchByVehicleNo(vehicleNO);
    }

    @Override
    public boolean deleteVehicle(String vehicleNo) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(vehicleNo);
    }

    @Override
    public List<String> getNosVehicle() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNos();
    }
}
