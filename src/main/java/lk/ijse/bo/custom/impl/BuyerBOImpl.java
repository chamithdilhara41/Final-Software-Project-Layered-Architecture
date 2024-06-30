package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BuyerBO;
import lk.ijse.dao.custom.BuyerDAO;
import lk.ijse.dao.custom.impl.BuyerDAOImpl;
import lk.ijse.entity.Buyer;

import java.sql.SQLException;
import java.util.List;

public class BuyerBOImpl implements BuyerBO {

    BuyerDAO buyerDAO = new BuyerDAOImpl();

    @Override
    public boolean saveBuyer(Buyer buyer) throws SQLException, ClassNotFoundException {
       return buyerDAO.save(buyer);
    }

    @Override
    public List<Buyer> getAllBuyer() throws SQLException, ClassNotFoundException {
        return buyerDAO.getAll();
    }

    @Override
    public Buyer searchByIdBuyer(String buyerID) throws SQLException, ClassNotFoundException {
        return buyerDAO.searchById(buyerID);
    }

    @Override
    public boolean updateBuyer(Buyer buyer) throws SQLException, ClassNotFoundException {
        return buyerDAO.update(buyer);
    }

    @Override
    public boolean deleteBuyer(String buyerID) throws SQLException, ClassNotFoundException {
        return buyerDAO.delete(buyerID);
    }

    @Override
    public List<String> getIdsBuyer() throws SQLException, ClassNotFoundException {
        return buyerDAO.getIds();
    }

    @Override
    public Buyer searchByBuyerIdForOrderBuyer(String no) throws SQLException, ClassNotFoundException {
        return buyerDAO.searchById(no);
    }

    @Override
    public Buyer searchByStockIdForTransactionBuyer(String oId) throws SQLException, ClassNotFoundException {
        return buyerDAO.searchById(oId);
    }

}
