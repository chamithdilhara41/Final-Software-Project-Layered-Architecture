package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockDAO extends CrudDAO<Stock> {

    //public List<Stock> getAll() throws SQLException ;

    //public boolean save(Stock stock) throws SQLException ;

    //public boolean delete(String stockID) throws SQLException ;

    public Stock searchByStockIdForOrder(String no) throws SQLException, ClassNotFoundException;

    public boolean updateWeight(String stockID, String supplierID, Double weight) throws SQLException, ClassNotFoundException;

    public List<String> getIds() throws SQLException, ClassNotFoundException;

}
