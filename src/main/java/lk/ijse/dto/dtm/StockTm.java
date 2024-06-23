package lk.ijse.dto.dtm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StockTm {
    private String stockId;
    private Double totalWeight;
    private Date date;

}
