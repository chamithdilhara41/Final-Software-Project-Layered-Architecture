package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StockBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.impl.StockDAOImpl;
import lk.ijse.dto.StockDTO;
import lk.ijse.entity.Stock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockBOImpl implements StockBO {

    StockDAO stockDAO = (StockDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STOCK);

    @Override
    public boolean saveStock(StockDTO stock) throws SQLException, ClassNotFoundException {
        return stockDAO.save(new Stock(stock.getStockId(), stock.getWeight(),stock.getDate()));
    }

    @Override
    public List<StockDTO> getAllStock() throws SQLException, ClassNotFoundException {
        List<Stock> stocks = stockDAO.getAll();
        List<StockDTO> stockDTOList = new ArrayList<>();
        for (Stock stock : stocks) {
            stockDTOList.add(new StockDTO(
                    stock.getStockId(),
                    stock.getWeight(),
                    stock.getDate()));
        }
        return stockDTOList;
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
