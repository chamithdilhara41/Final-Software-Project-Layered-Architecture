package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BuyerDTO;
import lk.ijse.entity.Buyer;

import java.sql.SQLException;
import java.util.List;

public interface BuyerBO extends SuperBO {

    public boolean saveBuyer(BuyerDTO buyerDTO) throws SQLException, ClassNotFoundException ;

    public List<BuyerDTO> getAllBuyer() throws SQLException, ClassNotFoundException ;

    public BuyerDTO searchByIdBuyer(String buyerID) throws SQLException, ClassNotFoundException ;

    public boolean updateBuyer(BuyerDTO buyerDTO) throws SQLException, ClassNotFoundException ;

    public boolean deleteBuyer(String buyerID) throws SQLException, ClassNotFoundException ;

    public List<String> getIdsBuyer() throws SQLException, ClassNotFoundException ;

    public Buyer searchByBuyerIdForOrderBuyer(String no) throws SQLException, ClassNotFoundException ;

    public Buyer searchByStockIdForTransactionBuyer(String oId) throws SQLException, ClassNotFoundException ;

}
