package lk.ijse.dao.custom.impl;

import lk.ijse.util.SQLUtil;
import lk.ijse.dao.custom.BuyerDAO;
import lk.ijse.entity.Buyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAOImpl implements BuyerDAO {

    public boolean save(Buyer buyer) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO buyer VALUES(?, ?, ?, ?, ?);",
                buyer.getBuyerId(),
                buyer.getBuyerName(),
                buyer.getBuyerAddress(),
                buyer.getBuyerContactOffice(),
                buyer.getBuyerContactManager()
        );
    }

    public List<Buyer> getAll() throws SQLException, ClassNotFoundException {

        List<Buyer> data = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM buyer");
        while (resultSet.next()) {
            data.add(new Buyer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public Buyer searchById(String buyerID) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM buyer WHERE buyerId = ?",buyerID);
        if (resultSet.next()) {
            String buyerId = resultSet.getString(1);
            String buyerName = resultSet.getString(2);
            String buyerAddress = resultSet.getString(3);
            String buyerContactOffice = resultSet.getString(4);
            String buyerContactManager = resultSet.getString(5);

            return new Buyer(buyerID,buyerName,buyerAddress,buyerContactOffice,buyerContactManager);
        }
        return null;
    }

    public boolean update(Buyer buyer) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE buyer SET name = ?, address = ?, contactOffice = ?, contactManager = ? WHERE buyerId = ?",
                buyer.getBuyerName(),
                buyer.getBuyerAddress(),
                buyer.getBuyerContactOffice(),
                buyer.getBuyerContactManager(),
                buyer.getBuyerId()
        );
    }

    public boolean delete(String buyerID) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM buyer WHERE buyerId = ?;",buyerID);
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT buyerId FROM buyer");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public Buyer searchByBuyerIdForOrder(String no) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM buyer WHERE buyerId = ?;",no);
        if (resultSet.next()) {
            String buyerID = resultSet.getString(1);
            String buyerName = resultSet.getString(2);
            String buyerAddress = resultSet.getString(3);
            String buyerContactOffice = resultSet.getString(4);
            String buyerContactManager = resultSet.getString(5);

            return new Buyer(buyerID, buyerName, buyerAddress,buyerContactOffice,buyerContactManager);
        }
        return null;
    }

    public Buyer searchByStockIdForTransaction(String oId) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT b.* FROM buyer b JOIN ordersstockinfo osi ON b.buyerId = osi.buyerId WHERE osi.stockId = ?;",oId);
        if (resultSet.next()) {
            String buyerID = resultSet.getString(1);
            String buyerName = resultSet.getString(2);
            String buyerAddress = resultSet.getString(3);
            String buyerContactOffice = resultSet.getString(4);
            String buyerContactManager = resultSet.getString(5);

            return new Buyer(buyerID, buyerName, buyerAddress,buyerContactOffice,buyerContactManager);
        }

        return null;
    }

}
