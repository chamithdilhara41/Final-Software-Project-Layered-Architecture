package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.SupplierStockDetail;
import lk.ijse.dto.tdm.SupplierStockDetailTm;

import java.sql.SQLException;
import java.util.List;

public interface SupplierStockDetailDAO extends CrudDAO<SupplierStockDetail> {

    public boolean save(SupplierStockDetail supplierStockDetail) throws SQLException ;

    public List<SupplierStockDetailTm> searchSuppliersWithStockId(String stockID) throws SQLException ;

}
