package lk.ijse.dto.dtm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DeliveryTm {
    private String deliveryId;
    private String date;
    private String orderId;
    private String vehicleNo;
}
