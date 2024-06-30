package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO supplier VALUES(?, ?, ?, ?, ?);",
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getSupplierAddress(),
                supplier.getSupplierContact(),
                supplier.getSupplierGender()
        );
    }

    public Supplier searchById(String supplierID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE supplierID = ?",supplierID);
        if (resultSet.next()) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Found!").show();
            String supplierId = resultSet.getString(1);
            String supplierName = resultSet.getString(2);
            String supplierAddress = resultSet.getString(3);
            String supplierContact = resultSet.getString(4);
            String supplierGender = resultSet.getString(5);

            return  new Supplier(supplierId, supplierName, supplierAddress, supplierContact, supplierGender);
        }

        return null;
    }

    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE supplier SET name=?, address=?, contact=?, gender=? WHERE supplierId = ?;",
                supplier.getSupplierName(),
                supplier.getSupplierAddress(),
                supplier.getSupplierContact(),
                supplier.getSupplierGender(),
                supplier.getSupplierId()
        );
    }

    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public boolean delete(String supplierID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM supplier WHERE supplierId = ?",supplierID);
    }

    public Supplier searchBySupplierIdForPayment(String supplierID) throws SQLException, ClassNotFoundException {
        ;
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE supplierID = ?",supplierID);
        if (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String supplierName = resultSet.getString(2);
            String supplierAddress = resultSet.getString(3);
            String supplierContact = resultSet.getString(4);
            String supplierGender = resultSet.getString(5);

            return  new Supplier(supplierId, supplierName, supplierAddress, supplierContact, supplierGender);
        }

        return null;
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT supplierId FROM supplier");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;

    }

}
