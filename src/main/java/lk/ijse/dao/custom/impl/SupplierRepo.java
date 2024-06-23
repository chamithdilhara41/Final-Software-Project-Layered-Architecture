package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplier.getSupplierId());
        pstm.setObject(2, supplier.getSupplierName());
        pstm.setObject(3, supplier.getSupplierAddress());
        pstm.setObject(4,supplier.getSupplierContact());
        pstm.setObject(5,supplier.getSupplierGender());

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String supplierID) throws SQLException {

        String sql = "SELECT * FROM supplier WHERE supplierID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplierID);
        ResultSet resultSet = pstm.executeQuery();
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

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET name=?, address=?, contact=?, gender=? WHERE supplierId = ?;";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getSupplierName());
        pstm.setObject(2, supplier.getSupplierAddress());
        pstm.setObject(3, supplier.getSupplierContact());
        pstm.setObject(4, supplier.getSupplierGender());
        pstm.setObject(5, supplier.getSupplierId());

        return pstm.executeUpdate() > 0;
    }
    public static List<Supplier> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
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

    public static boolean delete(String supplierID) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplierID);

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchBySupplierIdForPayment(String supplierID) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplierID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplierID);
        ResultSet resultSet = pstm.executeQuery();
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

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT supplierId FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;

    }
}
