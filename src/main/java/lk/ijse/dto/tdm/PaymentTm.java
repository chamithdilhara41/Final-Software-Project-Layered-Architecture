package lk.ijse.dto.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentTm {
    private String paymentId;
    private String description;
    private Double amount;
    private String date;
    private String supplierId;
}
