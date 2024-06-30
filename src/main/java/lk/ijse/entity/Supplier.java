package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data //getter & setter toString okkoma tiyenawa

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierGender;


}
