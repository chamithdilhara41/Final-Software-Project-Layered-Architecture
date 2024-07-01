package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BuyerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BuyerDAO;
import lk.ijse.dto.BuyerDTO;
import lk.ijse.entity.Buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerBOImpl implements BuyerBO {

    BuyerDAO buyerDAO = (BuyerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BUYER);

    @Override
    public boolean saveBuyer(BuyerDTO buyerDTO) throws SQLException, ClassNotFoundException {
       return buyerDAO.save(new Buyer(buyerDTO.getBuyerId(),buyerDTO.getBuyerName(),buyerDTO.getBuyerAddress(),buyerDTO.getBuyerContactOffice(),buyerDTO.getBuyerContactManager()));
    }

    @Override
    public List<BuyerDTO> getAllBuyer() throws SQLException, ClassNotFoundException {
        List<Buyer> buyers = buyerDAO.getAll();
        List<BuyerDTO> buyerDTOs = new ArrayList<>();
        for (Buyer buyer : buyers) {
                buyerDTOs.add(
                        new BuyerDTO(
                                buyer.getBuyerId(),
                                buyer.getBuyerName(),
                                buyer.getBuyerAddress(),
                                buyer.getBuyerContactOffice(),
                                buyer.getBuyerContactManager()
                        )
                );
        }

        return buyerDTOs;
    }

    @Override
    public BuyerDTO searchByIdBuyer(String buyerID) throws SQLException, ClassNotFoundException {
        Buyer buyer = buyerDAO.searchById(buyerID);
        return new BuyerDTO(buyer.getBuyerId(), buyer.getBuyerName(),buyer.getBuyerAddress(), buyer.getBuyerContactOffice(), buyer.getBuyerContactManager() );
    }

    @Override
    public boolean updateBuyer(BuyerDTO buyerDTO) throws SQLException, ClassNotFoundException {
        return buyerDAO.update(new Buyer(buyerDTO.getBuyerId(),buyerDTO.getBuyerName(),buyerDTO.getBuyerAddress(),buyerDTO.getBuyerContactOffice(),buyerDTO.getBuyerContactManager()));
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
