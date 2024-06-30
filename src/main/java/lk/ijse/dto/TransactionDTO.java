package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TransactionDTO {
    private String transactionId;
    private String orderId;
    private String accountNo;
    private String description;
    private Double amount;
    private String date;
    private String method;
}
