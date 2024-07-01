package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {

    public boolean saveSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException ;

    public Supplier searchByIdSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException ;

    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException ;

    public boolean deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public Supplier searchBySupplierIdForPaymentSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsSupplier() throws SQLException, ClassNotFoundException ;

}
