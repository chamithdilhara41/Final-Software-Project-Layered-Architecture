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
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.Supplier;
import lk.ijse.dto.tdm.SupplierTm;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    public TableView<SupplierTm> tblSupplier;

    public Label lblSupplierForm;

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupplierGender;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierContact;

    @FXML
    private TextField txtSupplierGender;

    @FXML
    private TextField txtSupplierID;

    @FXML
    private TextField txtSupplierName;

    //dependency injection
    SupplierDAO supplierDAO = new SupplierDAOImpl();

    public void initialize() throws SQLException {
        animateLabelTyping();
        getAllSuppliers();
        setCellValueFactory();
    }

    @FXML
    void txtGenderOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.GENDER, txtSupplierGender);
    }

    @FXML
    void txtSupplierAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtSupplierAddress);
    }

    @FXML
    void txtSupplierContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtSupplierContact);
    }

    @FXML
    void txtSupplierIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID,txtSupplierID);
    }

    @FXML
    void txtSupplierNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME,txtSupplierName);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID,txtSupplierID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.GENDER,txtSupplierGender)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtSupplierAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtSupplierContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtSupplierName)) return false;
        return true;
    }


    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String supplierID = txtSupplierID.getText();

        if(supplierID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please fill supplierId for delete").show();
            return;
        }

        try {
            boolean isDeleted = supplierDAO.delete(supplierID);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
                getAllSuppliers();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.ERROR,"Can't find Supplier").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();
        String supplierGender = txtSupplierGender.getText();

        if(supplierID.isEmpty() || supplierName.isEmpty() || supplierAddress.isEmpty() || supplierContact.isEmpty() || supplierGender.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier name or address or contact is empty!").show();
            return;
        }

        Supplier supplier = new Supplier(supplierID, supplierName, supplierAddress, supplierContact, supplierGender);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = supplierDAO.save(supplier);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved!").show();
                clearFields();
                getAllSuppliers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();
        String supplierGender = txtSupplierGender.getText();

        if(supplierID.isEmpty() || supplierName.isEmpty() || supplierAddress.isEmpty() || supplierContact.isEmpty() || supplierGender.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields").show();
            return;
        }

        Supplier supplier = new Supplier(supplierID, supplierName, supplierAddress, supplierContact, supplierGender);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = supplierDAO.update(supplier);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated!").show();
                getAllSuppliers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void clearFields() {
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierAddress.setText("");
        txtSupplierContact.setText("");
        txtSupplierGender.setText("");
    }

    public void txtOnActionSearch(ActionEvent actionEvent) throws SQLException {
        String supplierID = txtSupplierID.getText();

        Supplier supplier = supplierDAO.searchById(supplierID);
        if (supplier != null) {
            txtSupplierID.setText(supplier.getSupplierId());
            txtSupplierName.setText(supplier.getSupplierName());
            txtSupplierAddress.setText(supplier.getSupplierAddress());
            txtSupplierContact.setText(supplier.getSupplierContact());
            txtSupplierGender.setText(supplier.getSupplierGender());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            txtSupplierName.setText("");
            txtSupplierAddress.setText("");
            txtSupplierContact.setText("");
            txtSupplierGender.setText("");
        }
    }
    void getAllSuppliers() throws SQLException {

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        List<Supplier> supplierList = supplierDAO.getAll();

        for ( Supplier supplier: supplierList){
            obList.add(new SupplierTm(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getSupplierAddress(),
                    supplier.getSupplierContact(),
                    supplier.getSupplierGender()
            ));
        }
        tblSupplier.setItems(obList);
    }

    void setCellValueFactory(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        colSupplierGender.setCellValueFactory(new PropertyValueFactory<>("supplierGender"));

    }

    public void OnMouseClicked(MouseEvent mouseEvent) {
        int index = tblSupplier.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colSupplierID.getCellData(index).toString();
        String name = colSupplierName.getCellData(index).toString();
        String address = colSupplierAddress.getCellData(index).toString();
        String contact = colSupplierContact.getCellData(index).toString();
        String gender = colSupplierGender.getCellData(index).toString();

        txtSupplierID.setText(id);
        txtSupplierName.setText(name);
        txtSupplierAddress.setText(address);
        txtSupplierContact.setText(contact);
        txtSupplierGender.setText(gender);
    }

    private void animateLabelTyping() {
        String loginText = lblSupplierForm.getText();
        int animationDuration = 250;

        lblSupplierForm.setText("");

        Timeline typingAnimation = new Timeline();

        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblSupplierForm.setText(loginText.substring(0, finalI));
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}