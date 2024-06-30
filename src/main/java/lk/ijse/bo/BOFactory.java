package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        BUYER,DELIVERY,EMPLOYEE,ORDER,PAYMENT,PLACE_ORDER,STOCK,SUPPLIER,SUPPLIER_STOCK_DTL,TRANSACTION,VEHICLE
    }

    public SuperBO getBO(BOTypes boType) {

        switch (boType) {
            case BUYER -> {
                return new BuyerBOImpl();
            }
            case DELIVERY -> {
                return new DeliveryBOImpl();
            }
            case EMPLOYEE -> {
                return new EmployeeBOImpl();
            }
            case ORDER -> {
                return new OrderBOImpl();
            }
            case PAYMENT -> {
                return new PaymentBOImpl();
            }
            case PLACE_ORDER -> {
                return new PlaceOrderBOImpl();
            }
            case STOCK -> {
                return new StockBOImpl();
            }
            case SUPPLIER -> {
                return new SupplierBOImpl();
            }
            case SUPPLIER_STOCK_DTL -> {
                return new SupplierStockDetailBOImpl();
            }
            case TRANSACTION -> {
                return new TransactionBOImpl();
            }
            case VEHICLE -> {
                return new VehicleBOImpl();
            }
            default -> {
                return null;
            }
        }
    }
}
