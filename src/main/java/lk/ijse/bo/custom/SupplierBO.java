package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {

    public boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException ;

    public Supplier searchByIdSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException ;

    public List<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException ;

    public boolean deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public Supplier searchBySupplierIdForPaymentSupplier(String supplierID) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsSupplier() throws SQLException, ClassNotFoundException ;

}
