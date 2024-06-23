package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.Buyer;

import java.sql.SQLException;
import java.util.List;

public interface BuyerDAO extends CrudDAO {

    public boolean save(Buyer buyer) throws SQLException ;

    public List<Buyer> getAll() throws SQLException ;

    public Buyer searchById(String buyerID) throws SQLException ;

    public boolean update(Buyer buyer) throws SQLException ;

    public boolean delete(String buyerID) throws SQLException ;

    public List<String> getIds() throws SQLException ;

    public Buyer searchByBuyerIdForOrder(String no) throws SQLException ;

    public Buyer searchByStockIdForTransaction(String oId) throws SQLException ;

}
