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
import lk.ijse.dao.custom.BuyerDAO;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.dao.custom.impl.BuyerDAOImpl;
import lk.ijse.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.dao.custom.impl.StockRepo;
import lk.ijse.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.dto.Buyer;
import lk.ijse.dto.Delivery;
import lk.ijse.dto.Vehicle;
import lk.ijse.dto.dtm.DeliveryTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeliveryFormController {

    public Label lblDeliveryForm;
    @FXML
    private JFXComboBox<String> cmbStockID;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDeliveryID;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private Label lblBuyerName;

    @FXML
    private Label lblVehicleType;

    @FXML
    private TableView<Object> tblDelivery;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDeliveryID;

    //dependency injection
    BuyerDAO buyerDAO = new BuyerDAOImpl();
    DeliveryDAO deliveryDAO = new DeliveryDAOImpl();
    VehicleDAO vehicleDAO = new VehicleDAOImpl();

    public void initialize() throws SQLException {
        animateLabelTyping();
        txtDate.setText(String.valueOf(LocalDate.now()));
        getVehicleNos();
        getStockIds();
        getAllDeliveries();
        setCellValueFactory();
    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE,txtDate);
    }

    @FXML
    void txtDeliveryIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID,txtDeliveryID);
    }

    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtDate)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.ID,txtDeliveryID)) return false;
        return true;
    }

    private void setCellValueFactory() {
        colDeliveryID.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStockID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
    }

    private void getAllDeliveries() throws SQLException {
        ObservableList<Object> obList = FXCollections.observableArrayList();
        List<Delivery> deliveryList = deliveryDAO.getAll();

        for (Delivery delivery : deliveryList) {
            obList.add(new DeliveryTm(
                    delivery.getDeliveryId(),
                    delivery.getDate(),
                    delivery.getOrderId(),
                    delivery.getVehicleNo()
            ));
        }tblDelivery.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblDelivery.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String deliveryID = colDeliveryID.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();
        String orderID = colStockID.getCellData(index).toString();
        String vehicleNo = colVehicleNo.getCellData(index).toString();

        txtDeliveryID.setText(deliveryID);
        txtDate.setText(date);
        cmbStockID.setValue(orderID);
        cmbVehicleNo.setValue(vehicleNo);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtDeliveryID.setText("");
        lblBuyerName.setText("");
        cmbStockID.getSelectionModel().clearSelection();
        cmbVehicleNo.getSelectionModel().clearSelection();
    }
    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();

        if (deliveryID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Delivery ID", ButtonType.OK).show();
            return;
        }

        try {
            boolean isDeleted = deliveryDAO.delete(deliveryID);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delivery deleted!").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.ERROR,"Can't find Delivery ").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbStockID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        Delivery delivery = new Delivery(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = deliveryDAO.save(delivery);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Saved").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbStockID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }


        Delivery delivery = new Delivery(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = deliveryDAO.update(delivery);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ",ButtonType.OK).show();
            }
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Updated").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void cmbStockIdOnAction(ActionEvent event) {
        String sId = cmbStockID.getValue();

        try {
            Buyer buyer = buyerDAO.searchByStockIdForTransaction(sId);
            if(buyer != null){
                lblBuyerName.setText(buyer.getBuyerName());
            }else {
                lblBuyerName.setText("");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void cmbVehicleNoOnAction(ActionEvent event) {
        String No = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = vehicleDAO.searchByVehicleNoForEmp(No);

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

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String deliveryID = txtDeliveryID.getText();

        Delivery delivery = deliveryDAO.searchByDeliveryId(deliveryID);
        if(delivery != null){
            new Alert(Alert.AlertType.INFORMATION, "Delivery Searched").show();
            txtDeliveryID.setText(delivery.getDeliveryId());
            txtDate.setText(delivery.getDate());
            cmbStockID.setValue(delivery.getOrderId());
            cmbVehicleNo.setValue(delivery.getVehicleNo());
        }else {
            new Alert(Alert.AlertType.ERROR, "Delivery Not Found").show();
            lblBuyerName.setText("");
            cmbStockID.getSelectionModel().clearSelection();
            cmbVehicleNo.getSelectionModel().clearSelection();
        }
    }
    private void getVehicleNos() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = vehicleDAO.getNos();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbVehicleNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = StockRepo.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbStockID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void animateLabelTyping() {
        String loginText = lblDeliveryForm.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblDeliveryForm.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblDeliveryForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }
}
