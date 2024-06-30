package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDTO {
    private String paymentId;
    private String description;
    private Double amount;
    private String date;
    private String supplierId;

}
