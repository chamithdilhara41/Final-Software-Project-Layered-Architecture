package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = new SupplierDAOImpl();

    @Override
    public boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(supplier);
    }

    @Override
    public Supplier searchByIdSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        return supplierDAO.searchById(supplierID);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(supplier);
    }

    @Override
    public List<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAll();
    }

    @Override
    public boolean deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierID);
    }

    @Override
    public Supplier searchBySupplierIdForPaymentSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        return supplierDAO.searchBySupplierIdForPayment(supplierID);
    }

    @Override
    public List<String> getIdsSupplier() throws SQLException, ClassNotFoundException {
        return supplierDAO.getIds();
    }
}
