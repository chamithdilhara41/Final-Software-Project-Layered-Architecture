package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockDAO extends CrudDAO {

    public boolean save(Stock stock) throws SQLException ;

    public List<Stock> getAll() throws SQLException ;

    public boolean updateWeight(String stockID, String supplierID, Double weight) throws SQLException ;

    public List<String> getIds() throws SQLException ;

    public Stock searchByStockIdForOrder(String no) throws SQLException ;

    public boolean delete(String stockID) throws SQLException ;

}
