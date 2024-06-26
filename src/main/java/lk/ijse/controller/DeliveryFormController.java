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
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BuyerBO;
import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.bo.custom.StockBO;
import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.bo.custom.impl.BuyerBOImpl;
import lk.ijse.bo.custom.impl.DeliveryBOImpl;
import lk.ijse.bo.custom.impl.VehicleBOImpl;
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.impl.StockDAOImpl;
import lk.ijse.dto.BuyerDTO;
import lk.ijse.dto.DeliveryDTO;
import lk.ijse.entity.Buyer;
import lk.ijse.entity.Delivery;
import lk.ijse.entity.Vehicle;
import lk.ijse.tdm.DeliveryTm;
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
    BuyerBO buyerBO = (BuyerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BUYER);
    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DELIVERY);
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VEHICLE);
    StockBO stockBO = (StockBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);

    public void initialize() throws SQLException, ClassNotFoundException {
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

    private void getAllDeliveries() throws SQLException, ClassNotFoundException {
        ObservableList<Object> obList = FXCollections.observableArrayList();
        List<DeliveryDTO> deliveryList = deliveryBO.getAllDelivery();

        for (DeliveryDTO delivery : deliveryList) {
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
    void btnOnActionDelete(ActionEvent event) throws ClassNotFoundException {
        String deliveryID = txtDeliveryID.getText();

        if (deliveryID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Delivery ID", ButtonType.OK).show();
            return;
        }

        try {
            boolean isDeleted = deliveryBO.deleteDelivery(deliveryID);
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
    void btnOnActionSave(ActionEvent event) throws ClassNotFoundException {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbStockID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        DeliveryDTO delivery = new DeliveryDTO(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = deliveryBO.saveDelivery(delivery);
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
    void btnOnActionUpdate(ActionEvent event) throws ClassNotFoundException {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbStockID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }


        DeliveryDTO delivery = new DeliveryDTO(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = deliveryBO.updateDelivery(delivery);
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
    void cmbStockIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String sId = cmbStockID.getValue();

        try {
            Buyer buyer = buyerBO.searchByStockIdForTransactionBuyer(sId);
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
    void cmbVehicleNoOnAction(ActionEvent event) throws ClassNotFoundException {
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

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        String deliveryID = txtDeliveryID.getText();

        Delivery delivery = deliveryBO.searchByDeliveryIdDelivery(deliveryID);
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

    private void getStockIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = stockBO.getIdsStock();

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
