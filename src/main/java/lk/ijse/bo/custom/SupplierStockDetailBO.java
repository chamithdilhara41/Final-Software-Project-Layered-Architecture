package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.SupplierStockDetail;
import lk.ijse.tdm.SupplierStockDetailTm;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierStockDetailBO extends SuperBO {

    public boolean saveSupplierStockDetail(SupplierStockDetail supplierStockDetail) throws SQLException, ClassNotFoundException ;

    public List<SupplierStockDetailTm> searchSuppliersWithStockIdSupplierStockDetail(String stockID) throws SQLException, ClassNotFoundException ;

}
