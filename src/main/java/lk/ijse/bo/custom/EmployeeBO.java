package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {

    public boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException ;

    public List<Employee> getAllEmployee() throws SQLException, ClassNotFoundException ;

    public boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException ;

    public boolean deleteEmployee(String employeeID) throws SQLException, ClassNotFoundException ;
    public Employee searchByIdEmployee(String employeeID) throws SQLException, ClassNotFoundException ;
}
