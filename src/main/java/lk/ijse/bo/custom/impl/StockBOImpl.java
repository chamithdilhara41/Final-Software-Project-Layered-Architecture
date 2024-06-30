package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StockBO;
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.impl.StockDAOImpl;
import lk.ijse.entity.Stock;

import java.sql.SQLException;
import java.util.List;

public class StockBOImpl implements StockBO {

    StockDAO stockDAO = new StockDAOImpl();

    @Override
    public boolean saveStock(Stock stock) throws SQLException, ClassNotFoundException {
        return stockDAO.save(stock);
    }

    @Override
    public List<Stock> getAllStock() throws SQLException, ClassNotFoundException {
        return stockDAO.getAll();
    }

    @Override
    public boolean updateWeightStock(String stockID, String supplierID, Double weight) throws SQLException, ClassNotFoundException {
        return stockDAO.updateWeight(stockID,supplierID,weight);
    }

    @Override
    public List<String> getIdsStock() throws SQLException, ClassNotFoundException {
        return stockDAO.getIds();
    }

    @Override
    public Stock searchByStockIdForOrderStock(String no) throws SQLException, ClassNotFoundException {
        return stockDAO.searchByStockIdForOrder(no);
    }

    @Override
    public boolean deleteStock(String stockID) throws SQLException, ClassNotFoundException {
        return stockDAO.delete(stockID);
    }
}
