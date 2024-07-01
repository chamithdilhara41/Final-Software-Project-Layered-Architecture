package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.dto.VehicleDTO;
import lk.ijse.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean saveVehicle(VehicleDTO vehicle) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(vehicle.getVehicleNo(), vehicle.getVehicleType()));
    }

    @Override
    public List<VehicleDTO> getAllVehicle() throws SQLException, ClassNotFoundException {
       List<Vehicle> vehicles = vehicleDAO.getAll();
       List<VehicleDTO> vehicleDTOs = new ArrayList<>();
       for (Vehicle vehicle : vehicles) {
           vehicleDTOs.add(new VehicleDTO(vehicle.getVehicleNo(), vehicle.getVehicleType()));
       }
       return vehicleDTOs;
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicle) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(vehicle.getVehicleNo(), vehicle.getVehicleType()));
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
