package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {

    //public boolean save(Supplier supplier) throws SQLException ;

    //public boolean update(Supplier supplier) throws SQLException ;

    //public List<Supplier> getAll() throws SQLException ;

    //public boolean delete(String supplierID) throws SQLException ;

    public Supplier searchById(String supplierID) throws SQLException, ClassNotFoundException;

    public Supplier searchBySupplierIdForPayment(String supplierID) throws SQLException, ClassNotFoundException;

    public List<String> getIds() throws SQLException, ClassNotFoundException;
}
