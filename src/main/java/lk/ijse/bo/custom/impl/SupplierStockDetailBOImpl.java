package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierStockDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierStockDetailDAO;
import lk.ijse.entity.SupplierStockDetail;
import lk.ijse.tdm.SupplierStockDetailTm;

import java.sql.SQLException;
import java.util.List;

public class SupplierStockDetailBOImpl implements SupplierStockDetailBO {

    SupplierStockDetailDAO supplierStockDetailDAO = (SupplierStockDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_STOCK_DTL);

    @Override
    public boolean saveSupplierStockDetail(SupplierStockDetail supplierStockDetail) throws SQLException, ClassNotFoundException {
        return supplierStockDetailDAO.save(supplierStockDetail);
    }

    @Override
    public List<SupplierStockDetailTm> searchSuppliersWithStockIdSupplierStockDetail(String stockID) throws SQLException, ClassNotFoundException {
        return supplierStockDetailDAO.searchSuppliersWithStockId(stockID);
    }
}
