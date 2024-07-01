package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {

    public boolean saveEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException ;

    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException ;

    public boolean updateEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException ;

    public boolean deleteEmployee(String employeeID) throws SQLException, ClassNotFoundException ;

    public Employee searchByIdEmployee(String employeeID) throws SQLException, ClassNotFoundException ;
}
