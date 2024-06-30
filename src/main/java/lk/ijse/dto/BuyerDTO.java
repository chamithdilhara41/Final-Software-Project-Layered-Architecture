package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class BuyerDTO {
    private String BuyerId;
    private String BuyerName;
    private String BuyerAddress;
    private String BuyerContactOffice;
    private String BuyerContactManager;
}
