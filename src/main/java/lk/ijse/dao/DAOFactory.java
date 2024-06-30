package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            return daoFactory = new DAOFactory();
        }else {
            return daoFactory;
        }
    }

    public enum DAOTypes{
        BUYER,DELIVERY,EMPLOYEE,ORDER,ORDER_DTL,PAYMENT,STOCK,SUPPLIER,SUPPLIER_STOCK_DTL,TRANSACTION,VEHICLE
    }

    public SuperDAO getDAO(DAOTypes type) {

        switch (type) {
            case BUYER:return new BuyerDAOImpl();
            case DELIVERY:return new DeliveryDAOImpl();
            case EMPLOYEE:return new EmployeeDAOImpl();
            case ORDER:return new OrderDAOImpl();
            case ORDER_DTL:return new OrderDetailDAOImpl();
            case PAYMENT:return new PaymentDAOImpl();
            case STOCK:return new StockDAOImpl();
            case SUPPLIER:return new SupplierDAOImpl();
            case SUPPLIER_STOCK_DTL:return new SupplierStockDetailDAOImpl();
            case TRANSACTION:return new TransactionDAOImpl();
            case VEHICLE:return new VehicleDAOImpl();
            default:return null;
        }

    }
}
