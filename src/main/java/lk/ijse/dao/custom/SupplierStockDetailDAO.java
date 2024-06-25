package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.SupplierStockDetail;
import lk.ijse.tdm.SupplierStockDetailTm;

import java.sql.SQLException;
import java.util.List;

public interface SupplierStockDetailDAO extends CrudDAO<SupplierStockDetail> {

    public boolean save(SupplierStockDetail supplierStockDetail) throws SQLException, ClassNotFoundException;

    public List<SupplierStockDetailTm> searchSuppliersWithStockId(String stockID) throws SQLException ;

}
