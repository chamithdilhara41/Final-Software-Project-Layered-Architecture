package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    public boolean save(Employee employee) throws SQLException {

        String sql = "INSERT INTO employee values(?,?,?,?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getEmployeeName());
        pstm.setObject(3, employee.getEmployeeAddress());
        pstm.setObject(4, employee.getEmployeeContact());
        pstm.setObject(5,employee.getEmployeeSalary());
        pstm.setObject(6,employee.getVehicleNo());

        return pstm.executeUpdate() > 0;
    }

    public List<Employee> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return data;

    }

    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name=?, address=?, contact=?, salary=?, vehicleNo=? WHERE employeeId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeName());
        pstm.setObject(2, employee.getEmployeeAddress());
        pstm.setObject(3, employee.getEmployeeContact());
        pstm.setObject(4, employee.getEmployeeSalary());
        pstm.setObject(5, employee.getVehicleNo());
        pstm.setObject(6, employee.getEmployeeId());

        return pstm.executeUpdate() > 0;
    }

    public boolean delete(String employeeID) throws SQLException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employeeID);

        return pstm.executeUpdate() > 0;
    }

    public Employee searchById(String employeeID) throws SQLException {
        
        String sql = "SELECT * FROM employee WHERE employeeId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employeeID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String employeeName = resultSet.getString(2);
            String employeeAddress = resultSet.getString(3);
            String employeeContact = resultSet.getString(4);
            Double employeeSalary = Double.valueOf(resultSet.getString(5));
            String vehicleNo = resultSet.getString(6);

            return new Employee(employeeId,employeeName,employeeAddress,employeeContact,employeeSalary,vehicleNo);
        }

        return null;
    }

}
