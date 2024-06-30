package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data //getter & setter toString okkoma tiyenawa

public class SupplierDTO {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierGender;
}
