package lk.ijse.dto.dtm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String employeeAddress;
    private String employeeContact;
    private Double employeeSalary;
    private String vehicleNo;
}
