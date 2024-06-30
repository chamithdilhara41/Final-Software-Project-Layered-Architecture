package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {

    //public boolean save(Employee employee) throws SQLException ;

    //public List<Employee> getAll() throws SQLException ;

    //public boolean update(Employee employee) throws SQLException ;

    //public boolean delete(String employeeID) throws SQLException ;

    public Employee searchById(String employeeID) throws SQLException, ClassNotFoundException;

}
