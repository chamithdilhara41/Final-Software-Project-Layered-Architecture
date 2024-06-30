package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.bo.custom.impl.VehicleBOImpl;
import lk.ijse.entity.Employee;
import lk.ijse.entity.Vehicle;
import lk.ijse.tdm.EmployeeTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    public Label lblVehicleType;
    public Label lblEmployeeForm;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeContact;

    @FXML
    private TableColumn<?, ?> colEmployeeID;

    @FXML
    private TableColumn<?, ?> colEmployeeSalary;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeContact;

    @FXML
    private TextField txtEmployeeID;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeSalary;

    //dependency injection
    EmployeeBO employeeBO = new EmployeeBOImpl();
    VehicleBO vehicleBO = new VehicleBOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        animateLabelTyping();
        getVehicleNos();
        getAllEmployees();
        setCellValueFactory();
    }


    @FXML
    void txtEmployeeAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtEmployeeAddress);
    }

    @FXML
    void txtEmployeeContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtEmployeeContact);
    }

    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID,txtEmployeeID);
    }

    @FXML
    void txtEmployeeNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtEmployeeName);
    }

    @FXML
    void txtEmployeeSalaryOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.SALARY, txtEmployeeSalary);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtEmployeeName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtEmployeeAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtEmployeeContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtEmployeeID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.SALARY, txtEmployeeSalary)) return false;
        return true;
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblEmployee.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colEmployeeID.getCellData(index).toString();
        String name = colEmployeeName.getCellData(index).toString();
        String address = colEmployeeAddress.getCellData(index).toString();
        String contact = colEmployeeContact.getCellData(index).toString();
        String salary = colEmployeeSalary.getCellData(index).toString();
        String vehicleNo = colVehicleNo.getCellData(index).toString();


        txtEmployeeID.setText(id);
        txtEmployeeName.setText(name);
        txtEmployeeAddress.setText(address);
        txtEmployeeContact.setText(contact);
        txtEmployeeSalary.setText(salary);
        cmbVehicleNo.setValue(vehicleNo);
    }

    private void clearFields() {
        txtEmployeeID.setText("");
        txtEmployeeName.setText("");
        txtEmployeeContact.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeeSalary.setText("");
        cmbVehicleNo.getSelectionModel().clearSelection();
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) throws ClassNotFoundException {
        String EmployeeID = txtEmployeeID.getText();

        if (EmployeeID.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Employee ID", ButtonType.OK).show();
            return;
        }

        try {
            boolean isDeleted = employeeBO.deleteEmployee(EmployeeID);
            if(isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted!").show();
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.ERROR,"Can't find Employee").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) throws ClassNotFoundException {
        String employeeID = txtEmployeeID.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();
        String employeeSalary = String.valueOf(Double.valueOf(txtEmployeeSalary.getText()));
        String vehicleNo = cmbVehicleNo.getValue();

        try {
            if(employeeID.isEmpty() || employeeName.isEmpty() || employeeAddress.isEmpty() || employeeContact.isEmpty() || vehicleNo.isEmpty() || employeeSalary.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        Employee employee = new Employee(employeeID,employeeName,employeeAddress,employeeContact,Double.valueOf(employeeSalary),vehicleNo);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = employeeBO.saveEmployee(employee);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) throws ClassNotFoundException {
        String employeeID = txtEmployeeID.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();
        Double employeeSalary = Double.valueOf(txtEmployeeSalary.getText());
        String vehicleNo = cmbVehicleNo.getValue();

        try {
            if(employeeID.isEmpty() || employeeName.isEmpty() || employeeAddress.isEmpty() || employeeContact.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();        }

        Employee employee = new Employee(employeeID,employeeName,employeeAddress,employeeContact,employeeSalary,vehicleNo);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = employeeBO.updateEmployee(employee);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ",ButtonType.OK).show();
            }
            if(isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated!").show();
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeID = txtEmployeeID.getText();

        Employee employee = employeeBO.searchByIdEmployee(employeeID);
        if (employee != null) {
            txtEmployeeID.setText(employee.getEmployeeId());
            txtEmployeeName.setText(employee.getEmployeeName());
            txtEmployeeAddress.setText(employee.getEmployeeAddress());
            txtEmployeeContact.setText(employee.getEmployeeContact());
            txtEmployeeSalary.setText(String.valueOf(employee.getEmployeeSalary()));
            cmbVehicleNo.setValue(employee.getVehicleNo());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            txtEmployeeName.setText("");
            txtEmployeeContact.setText("");
            txtEmployeeAddress.setText("");
            txtEmployeeSalary.setText("");
            cmbVehicleNo.getSelectionModel().clearSelection();
        }
    }

    public void cmbVehicleNoOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String No = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = vehicleBO.searchByVehicleNoForEmpVehicle(No);

            if (vehicle != null) {
                lblVehicleType.setText(vehicle.getVehicleType());
            } else {
                // Handle case when vehicle is not found
                lblVehicleType.setText("");
            }

        } catch (SQLException e) {
            // Handle any SQLException that might occur during the search
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for vehicle: " + e.getMessage()).show();
        }
    }

    private void getVehicleNos() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = vehicleBO.getNosVehicle();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbVehicleNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllEmployees() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        List<Employee> employeesList = employeeBO.getAllEmployee();

        for ( Employee employee: employeesList){
            obList.add(new EmployeeTm(
                    employee.getEmployeeId(),
                    employee.getEmployeeName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeContact(),
                    employee.getEmployeeSalary(),
                    employee.getVehicleNo()
            ));
        }
        tblEmployee.setItems(obList);
    }

    private void setCellValueFactory() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
    }

    private void animateLabelTyping() {
        String loginText = lblEmployeeForm.getText();
        int animationDuration = 250;

        lblEmployeeForm.setText("");

        Timeline typingAnimation = new Timeline();

        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblEmployeeForm.setText(loginText.substring(0, finalI));
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}
