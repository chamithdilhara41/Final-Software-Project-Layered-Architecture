package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.StockDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.Stock;
import lk.ijse.util.SQLUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    public boolean save(Stock stock) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("insert into stock values(?,?,?,'active')",stock.getStockId(),stock.getWeight(),stock.getDate());
    }

    public List<Stock> getAll() throws SQLException, ClassNotFoundException {

        List<Stock> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("select * from stock");
        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDate(3)
            ));
        }
        return data;
    }

    public boolean updateWeight(String stockID, String supplierID, Double weight) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("update stock set TotalWeight = TotalWeight + ? where stockID = ?",weight,stockID);
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT stockId FROM stock WHERE status ='active'");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public Stock searchByStockIdForOrder(String no) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("select * from stock where stockID = ?",no);
        if (resultSet.next()) {
            String stockId = resultSet.getString(1);
            Double weight = resultSet.getDouble(2);
            Date date = resultSet.getDate(3);

            return new Stock(stockId, weight, date);
        }
        return null;
    }

    public boolean delete(String stockID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("delete from stock where stockID = ?",stockID);

    }




    @Override
    public boolean update(Stock dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
