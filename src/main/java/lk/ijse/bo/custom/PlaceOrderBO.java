package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.PlaceOrder;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {

    public boolean placeOrder(PlaceOrder po) throws SQLException ;

}
