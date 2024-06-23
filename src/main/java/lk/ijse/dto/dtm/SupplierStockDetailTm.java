package lk.ijse.dto.dtm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierStockDetailTm {
    private String supplierId;
    private String name;
    private Double weight;
}
