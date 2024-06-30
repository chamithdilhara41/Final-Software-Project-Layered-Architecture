package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO employee values(?,?,?,?,?,?)",
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmployeeAddress(),
                employee.getEmployeeContact(),
                employee.getEmployeeSalary(),
                employee.getVehicleNo()
        );
    }

    public List<Employee> getAll() throws SQLException, ClassNotFoundException {

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");
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

    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE employee SET name=?, address=?, contact=?, salary=?, vehicleNo=? WHERE employeeId = ?;",
                employee.getEmployeeName(),
                employee.getEmployeeAddress(),
                employee.getEmployeeContact(),
                employee.getEmployeeSalary(),
                employee.getVehicleNo(),
                employee.getEmployeeId()
        );
    }

    public boolean delete(String employeeID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM employee WHERE employeeId = ?",employeeID);
    }

    public Employee searchById(String employeeID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE employeeId = ?",employeeID);
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
