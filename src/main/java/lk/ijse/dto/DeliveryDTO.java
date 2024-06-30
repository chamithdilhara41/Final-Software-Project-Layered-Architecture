package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DeliveryDTO {
    private String deliveryId;
    private String date;
    private String orderId;
    private String vehicleNo;
}
