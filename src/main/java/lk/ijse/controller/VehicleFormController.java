package lk.ijse.controller;

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
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.dto.Vehicle;
import lk.ijse.tdm.VehicleTm;
import lk.ijse.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class VehicleFormController {

    public Label lblVehiclesForm;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableColumn<?, ?> colVehicleType;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    private TextField txtVehicleType;

    //dependency injection
    VehicleDAO vehicleDAO = new VehicleDAOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        animateLabelTyping();
        getAllVehicles();
        setCellValueFactory();
    }

    @FXML
    void txtVehicleNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.VEHICLENo,txtVehicleNo);
    }

    @FXML
    void txtVehicleTypeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.VEHICLETYPE,txtVehicleType);
    }

    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.util.TextField.VEHICLENo,txtVehicleNo)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.VEHICLETYPE,txtVehicleType)) return false;
        return true;
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblVehicle.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String vehicleNo = colVehicleNo.getCellData(index).toString();
        String vehicleType = colVehicleType.getCellData(index).toString();

        txtVehicleNo.setText(vehicleNo);
        txtVehicleType.setText(vehicleType);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) throws ClassNotFoundException {
        String vehicleNo = txtVehicleNo.getText();

        if (vehicleNo.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please enter vehicle No").show();
            return;
        }

        try {
            boolean isDeleted = vehicleDAO.delete(vehicleNo);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION , "Vehicle Deleted").showAndWait();
                clearFields();
                getAllVehicles();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Can't find Vehicle").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR , e.getMessage()).showAndWait();
        }

    }

    @FXML
    void btnOnActionSave(ActionEvent event) throws ClassNotFoundException {
        String vehicleNo = txtVehicleNo.getText();
        String vehicleType = txtVehicleType.getText();

        if(vehicleNo.isEmpty() || vehicleType.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION , "Vehicle Name & No cannot be empty").showAndWait();
            return;
        }

        Vehicle vehicle = new Vehicle(vehicleNo, vehicleType);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = vehicleDAO.save(vehicle);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle saved!").show();
                clearFields();
                getAllVehicles();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
    }

    private void getAllVehicles() throws SQLException, ClassNotFoundException {
        ObservableList<VehicleTm> obList = FXCollections.observableArrayList();
        List<Vehicle> vehiclesList = vehicleDAO.getAll();

        for ( Vehicle vehicle: vehiclesList){
            obList.add(new VehicleTm(
                    vehicle.getVehicleNo(),
                    vehicle.getVehicleType()
            ));
        }
        tblVehicle.setItems(obList);
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) throws ClassNotFoundException {
        String vehicleNo = txtVehicleNo.getText();
        String vehicleType = txtVehicleType.getText();

        if(vehicleNo.isEmpty() || vehicleType.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION , "Vehicle Type & No cannot be empty").showAndWait();
            return;
        }

        Vehicle vehicle = new Vehicle(vehicleNo, vehicleType);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = vehicleDAO.update(vehicle);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle updated!").show();
                getAllVehicles();setCellValueFactory();
            }
        } catch (SQLException e) {
            new  Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        String vehicleNo = txtVehicleNo.getText();

        Vehicle vehicle = vehicleDAO.searchByVehicleNo(vehicleNo);

        if(vehicle != null) {
            txtVehicleNo.setText(vehicle.getVehicleNo());
            txtVehicleType.setText(vehicle.getVehicleType());
        } else {
            new Alert(Alert.AlertType.ERROR, "Vehicle not found!").show();
            txtVehicleType.setText("");
        }
    }

    private void clearFields() {
        txtVehicleNo.setText("");
        txtVehicleType.setText("");
    }

    private void animateLabelTyping() {
        String loginText = lblVehiclesForm.getText();
        int animationDuration = 250;

        lblVehiclesForm.setText("");

        Timeline typingAnimation = new Timeline();


        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblVehiclesForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}
