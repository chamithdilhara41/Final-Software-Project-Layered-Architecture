package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierStockDetailDTO {

    private String stockId;
    private String supplierId;
    private Double weight;
}
