package lk.ijse.dao.custom.impl;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.SupplierStockDetail;
import lk.ijse.dto.dtm.SupplierStockDetailTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierStockDetailRepo {

    public static boolean save(SupplierStockDetail supplierStockDetail) throws SQLException {
        String sql = "insert into supplierstockinfo values(?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, supplierStockDetail.getStockId());
        pstm.setObject(2, supplierStockDetail.getSupplierId());
        pstm.setObject(3,supplierStockDetail.getWeight());

        return pstm.executeUpdate() > 0;
    }

    public static List<SupplierStockDetailTm> searchSuppliersWithStockId(String stockID) throws SQLException {
        String sql = "SELECT s.supplierId, s.name, si.Weight FROM supplier s JOIN supplierstockinfo si ON s.supplierId = si.supplierId WHERE si.stockId = ?;";
        List<SupplierStockDetailTm> data = new ArrayList<>();

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, stockID);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            data.add(new SupplierStockDetailTm(
                    resultSet.getString("supplierId"),
                    resultSet.getString("name"),
                    resultSet.getDouble("Weight")
            ));
        }
        return data;
    }
}
