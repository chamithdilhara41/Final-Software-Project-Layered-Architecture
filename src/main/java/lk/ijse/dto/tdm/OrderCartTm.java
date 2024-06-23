package lk.ijse.dto.tdm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderCartTm {
    //private String orderID ;
    private String buyerID ;
    private String buyerName ;
    private String date ;
    private  String stockID;
    private String stockWeight ;
    private JFXButton btnRemove ;
}
