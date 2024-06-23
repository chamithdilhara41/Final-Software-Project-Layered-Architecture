package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.OrderDetail;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO {

    public boolean save(List<OrderDetail> odList) throws SQLException ;

    public boolean saveOrderDetailsAndStocks(OrderDetail od) throws SQLException ;

}
