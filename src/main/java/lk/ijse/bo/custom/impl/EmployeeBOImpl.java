package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employee.getEmployeeId(), employee.getEmployeeName(), employee.getEmployeeAddress(), employee.getEmployeeContact(), employee.getEmployeeSalary(), employee.getVehicleNo()));
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(
                    employee.getEmployeeId(),
                    employee.getEmployeeName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeContact(),
                    employee.getEmployeeSalary(),
                    employee.getVehicleNo()
            ));
        }
        return employeeDTOs;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employee.getEmployeeId(), employee.getEmployeeName(), employee.getEmployeeAddress(), employee.getEmployeeContact(), employee.getEmployeeSalary(), employee.getVehicleNo()));
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
