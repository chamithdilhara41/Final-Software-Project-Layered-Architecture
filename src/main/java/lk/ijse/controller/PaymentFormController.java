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
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.bo.custom.impl.SupplierBOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Supplier;
import lk.ijse.tdm.PaymentTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    public Label lblPaymentForm;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPaymentID;

    @FXML
    private TextField txtSupplierID;

    //dependency injection
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() throws SQLException, ClassNotFoundException {
        animateLabelTyping();
        txtDate.setText(LocalDate.now().toString());
        getAllPayments();
        setCellValueFactory();
    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.AMOUNT, txtAmount);
    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE,txtDate);
    }

    @FXML
    void txtPaymentIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtPaymentID);
    }

    @FXML
    void txtSupplierIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtSupplierID);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtPaymentID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtSupplierID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT, txtAmount)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDate)) return false;
        return true;
    }

    private void setCellValueFactory() {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    private void getAllPayments() throws SQLException, ClassNotFoundException {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        List<PaymentDTO> paymentList = paymentBO.getAllPayment();

        for (PaymentDTO payment : paymentList) {
            obList.add(new PaymentTm(
                    payment.getPaymentId(),
                    payment.getDescription(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getSupplierId()
            ));
        }tblPayment.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) throws ClassNotFoundException {
        int index = tblPayment.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String paymentId = colPaymentID.getCellData(index).toString();
        String description = colDescription.getCellData(index).toString();
        String amount = colAmount.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();
        String supplierId = colSupplierID.getCellData(index).toString();

        txtPaymentID.setText(paymentId);
        txtDescription.setText(description);
        txtAmount.setText(amount);
        txtDate.setText(date);
        txtSupplierID.setText(supplierId);

        try {
            txtOnActionSearchSupplier(new ActionEvent());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPaymentID.setText("");
        txtSupplierID.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        lblSupplierName.setText("");
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) throws ClassNotFoundException {
        String paymentID = txtPaymentID.getText();

        if (paymentID.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter payment ID for delete", ButtonType.OK).show();
            return;
        }

        try {
            boolean isDeleted = paymentBO.deletePayment(paymentID);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.ERROR,"Can't find Payment ID").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) throws ClassNotFoundException {
        String paymentID = txtPaymentID.getText();
        String description = txtDescription.getText();
        String amount = String.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String supplierID = txtSupplierID.getText();

        if(paymentID.isEmpty() || description.isEmpty() || amount.describeConstable().isEmpty() || date.isEmpty() || supplierID.isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields").show();
            return;
        }

        PaymentDTO payment = new PaymentDTO(paymentID, description, Double.valueOf(amount), date, supplierID);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = paymentBO.savePayment(payment);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) throws ClassNotFoundException {
        String paymentID = txtPaymentID.getText();
        String description = txtDescription.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String supplierID = txtSupplierID.getText();

        try {
            if(paymentID.isEmpty() || description.isEmpty() || amount.describeConstable().isEmpty() || date.isEmpty() || supplierID.isEmpty()){
                new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        PaymentDTO payment = new PaymentDTO(paymentID, description, amount, date, supplierID);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = paymentBO.updatePayment(payment);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Updated").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws ClassNotFoundException {
        String paymentID = txtPaymentID.getText();

        try {
            Payment payment = paymentBO.searchByPaymentIdPayment(paymentID);
            if (payment != null) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Found").show();
                txtPaymentID.setText(payment.getPaymentId());
                txtDescription.setText(payment.getDescription());
                txtAmount.setText(payment.getAmount().toString());
                txtDate.setText(payment.getDate());
                txtSupplierID.setText(payment.getSupplierId());
                txtOnActionSearchSupplier(event);
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Found").show();
                txtSupplierID.setText("");
                txtDescription.setText("");
                txtAmount.setText("");
                lblSupplierName.setText("");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearchSupplier(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierID = txtSupplierID.getText();

        Supplier supplier = supplierBO.searchBySupplierIdForPaymentSupplier(supplierID);
        if(supplier != null){
            lblSupplierName.setText(supplier.getSupplierName());

        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier not found").show();
            lblSupplierName.setText("");

        }
    }

    private void animateLabelTyping() {
        String loginText = lblPaymentForm.getText();
        int animationDuration = 250;

        lblPaymentForm.setText("");

        Timeline typingAnimation = new Timeline();

        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblPaymentForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}
