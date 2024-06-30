package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Delivery {
    private String deliveryId;
    private String date;
    private String orderId;
    private String vehicleNo;
}
