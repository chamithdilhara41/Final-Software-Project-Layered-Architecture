package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.StockDTO;
import lk.ijse.entity.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockBO extends SuperBO {
    public boolean saveStock(StockDTO stock) throws SQLException, ClassNotFoundException ;

    public List<StockDTO> getAllStock() throws SQLException, ClassNotFoundException ;

    public boolean updateWeightStock(String stockID, String supplierID, Double weight) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsStock() throws SQLException, ClassNotFoundException ;

    public Stock searchByStockIdForOrderStock(String no) throws SQLException, ClassNotFoundException ;

    public boolean deleteStock(String stockID) throws SQLException, ClassNotFoundException ;
}
