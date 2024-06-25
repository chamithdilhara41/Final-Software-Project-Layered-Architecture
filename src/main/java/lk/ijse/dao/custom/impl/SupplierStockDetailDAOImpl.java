package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.SupplierStockDetailDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.SupplierStockDetail;
import lk.ijse.tdm.SupplierStockDetailTm;
import lk.ijse.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierStockDetailDAOImpl implements SupplierStockDetailDAO {

    public boolean save(SupplierStockDetail supplierStockDetail) throws SQLException, ClassNotFoundException {
       ;

        return SQLUtil.execute("insert into supplierstockinfo values(?,?,?)",
                supplierStockDetail.getStockId(),
                supplierStockDetail.getSupplierId(),
                supplierStockDetail.getWeight()
                );
    }




    @Override
    public boolean update(SupplierStockDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<SupplierStockDetail> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public List<SupplierStockDetailTm> searchSuppliersWithStockId(String stockID) throws SQLException {
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
