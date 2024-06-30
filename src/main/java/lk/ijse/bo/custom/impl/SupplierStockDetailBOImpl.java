package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierStockDetailBO;
import lk.ijse.dao.custom.SupplierStockDetailDAO;
import lk.ijse.dao.custom.impl.SupplierStockDetailDAOImpl;
import lk.ijse.entity.SupplierStockDetail;
import lk.ijse.tdm.SupplierStockDetailTm;

import java.sql.SQLException;
import java.util.List;

public class SupplierStockDetailBOImpl implements SupplierStockDetailBO {

    SupplierStockDetailDAO supplierStockDetailDAO = new SupplierStockDetailDAOImpl();

    @Override
    public boolean saveSupplierStockDetail(SupplierStockDetail supplierStockDetail) throws SQLException, ClassNotFoundException {
        return supplierStockDetailDAO.save(supplierStockDetail);
    }

    @Override
    public List<SupplierStockDetailTm> searchSuppliersWithStockIdSupplierStockDetail(String stockID) throws SQLException, ClassNotFoundException {
        return supplierStockDetailDAO.searchSuppliersWithStockId(stockID);
    }
}
