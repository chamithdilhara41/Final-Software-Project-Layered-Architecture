package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(employee);
    }

    @Override
    public boolean deleteEmployee(String employeeID) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeID);
    }

    @Override
    public Employee searchByIdEmployee(String employeeID) throws SQLException, ClassNotFoundException {
        return employeeDAO.searchById(employeeID);
    }

}
