package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierContact(), supplier.getSupplierGender()));
    }

    @Override
    public Supplier searchByIdSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        return supplierDAO.searchById(supplierID);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierContact(), supplier.getSupplierGender()));
    }

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        List<Supplier> supplierList = supplierDAO.getAll();
        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            supplierDTOList.add(new SupplierDTO(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getSupplierAddress(),
                    supplier.getSupplierContact(),
                    supplier.getSupplierGender()
            ));
        }
        return supplierDTOList;
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
