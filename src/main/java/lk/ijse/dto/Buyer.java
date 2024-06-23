package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Buyer {
    private String BuyerId;
    private String BuyerName;
    private String BuyerAddress;
    private String BuyerContactOffice;
    private String BuyerContactManager;
}
