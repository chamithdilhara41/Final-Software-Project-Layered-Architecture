package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.Buyer;

import java.sql.SQLException;
import java.util.List;

public interface BuyerBO extends SuperBO {

    public boolean saveBuyer(Buyer buyer) throws SQLException, ClassNotFoundException ;

    public List<Buyer> getAllBuyer() throws SQLException, ClassNotFoundException ;

    public Buyer searchByIdBuyer(String buyerID) throws SQLException, ClassNotFoundException ;

    public boolean updateBuyer(Buyer buyer) throws SQLException, ClassNotFoundException ;

    public boolean deleteBuyer(String buyerID) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsBuyer() throws SQLException, ClassNotFoundException ;

    public Buyer searchByBuyerIdForOrderBuyer(String no) throws SQLException, ClassNotFoundException ;

    public Buyer searchByStockIdForTransactionBuyer(String oId) throws SQLException, ClassNotFoundException ;

}
