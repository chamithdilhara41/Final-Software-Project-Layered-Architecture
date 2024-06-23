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
import lk.ijse.dao.custom.BuyerDAO;
import lk.ijse.dto.Buyer;
import lk.ijse.dto.dtm.BuyerTm;
import lk.ijse.dao.custom.impl.BuyerDAOImpl;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class BuyerFormController {

    public Label lblBuyerForm;

    @FXML
    private TableColumn<?, ?> colBuyerAddress;

    @FXML
    private TableColumn<?, ?> colBuyerContactManager;

    @FXML
    private TableColumn<?, ?> colBuyerContactOffice;

    @FXML
    private TableColumn<?, ?> colBuyerID;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private TableView<BuyerTm> tblBuyer;

    @FXML
    private TextField txtBuyerAddress;

    @FXML
    private TextField txtBuyerContactManager;

    @FXML
    private TextField txtBuyerContactOffice;

    @FXML
    private TextField txtBuyerID;

    @FXML
    private TextField txtBuyerName;

    //dependency injection
    BuyerDAO buyerDAO = new BuyerDAOImpl();


    public void initialize() throws SQLException {
        animateLabelTyping();
        getAllBuyers();
        setCellValueFactory();
    }

    @FXML
    void txtBuyerAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtBuyerAddress);
    }

    @FXML
    void txtBuyerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtBuyerID);
    }

    @FXML
    void txtBuyerManagerContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtBuyerContactManager);
    }

    @FXML
    void txtBuyerNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtBuyerName);
    }

    @FXML
    void txtBuyerOfficeContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtBuyerContactOffice);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID,txtBuyerID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtBuyerName)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtBuyerAddress)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtBuyerContactManager)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtBuyerContactOffice)) return false;
        return true;
    }

    private void setCellValueFactory() {
        colBuyerID.setCellValueFactory(new PropertyValueFactory<>("BuyerId"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("BuyerName"));
        colBuyerAddress.setCellValueFactory(new PropertyValueFactory<>("BuyerAddress"));
        colBuyerContactOffice.setCellValueFactory(new PropertyValueFactory<>("BuyerContactOffice"));
        colBuyerContactManager.setCellValueFactory(new PropertyValueFactory<>("BuyerContactManager"));
    }

    private void getAllBuyers() throws SQLException {
        ObservableList<BuyerTm> obList = FXCollections.observableArrayList();
        List<Buyer> buyerList = buyerDAO.getAll();

        for ( Buyer buyer: buyerList){
            obList.add(new BuyerTm(
                    buyer.getBuyerId(),
                    buyer.getBuyerName(),
                    buyer.getBuyerAddress(),
                    buyer.getBuyerContactOffice(),
                    buyer.getBuyerContactManager()
            ));
        }
        tblBuyer.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblBuyer.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colBuyerID.getCellData(index).toString();
        String name = colBuyerName.getCellData(index).toString();
        String address = colBuyerAddress.getCellData(index).toString();
        String contactOffice = colBuyerContactOffice.getCellData(index).toString();
        String contactManager = colBuyerContactManager.getCellData(index).toString();

        txtBuyerID.setText(id);
        txtBuyerName.setText(name);
        txtBuyerAddress.setText(address);
        txtBuyerContactOffice.setText(contactOffice);
        txtBuyerContactManager .setText(contactManager);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String buyerID = txtBuyerID.getText();

        if (buyerID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please fill buyer Id for delete ").show();
            return;
        }

        try {
            boolean isDeleted = buyerDAO.delete(buyerID);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Buyer deleted").show();
                getAllBuyers();
                setCellValueFactory();
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Can't find Buyer").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {

        String buyerID = txtBuyerID.getText();
        String buyerName = txtBuyerName.getText();
        String buyerAddress = txtBuyerAddress.getText();
        String buyerContactOffice = txtBuyerContactOffice.getText();
        String buyerContactManager = txtBuyerContactManager.getText();

        if(buyerID.isEmpty() || buyerName.isEmpty() || buyerAddress.isEmpty() || buyerContactOffice.isEmpty() || buyerContactManager.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please fill all the fields").show();
            return;
        }

        Buyer buyer = new Buyer(buyerID, buyerName, buyerAddress, buyerContactOffice, buyerContactManager);

        try {
            boolean isSaved = false;
            if (isValid()) {
                isSaved = buyerDAO.save(buyer);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Buyer saved!").show();
                clearFields();
                getAllBuyers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerAddress.setText("");
        txtBuyerContactOffice.setText("");
        txtBuyerContactManager.setText("");
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String buyerID = txtBuyerID.getText();
        String buyerName = txtBuyerName.getText();
        String buyerAddress = txtBuyerAddress.getText();
        String buyerContactOffice = txtBuyerContactOffice.getText();
        String buyerContactManager = txtBuyerContactManager.getText();

        if(buyerID.isEmpty() || buyerName.isEmpty() || buyerAddress.isEmpty() || buyerContactOffice.isEmpty() || buyerContactManager.isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please fill all the fields").show();
            return;
        }

        Buyer buyer = new Buyer(buyerID, buyerName, buyerAddress, buyerContactOffice, buyerContactManager);

        try {
            boolean isUpdated = false;
            if (isValid()) {
                isUpdated = buyerDAO.update(buyer);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer updated!").show();
                clearFields();
                getAllBuyers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String buyerID = txtBuyerID.getText();

        Buyer buyer = buyerDAO.searchById(buyerID);
        if (buyer != null) {
            txtBuyerID.setText(buyer.getBuyerId());
            txtBuyerName.setText(buyer.getBuyerName());
            txtBuyerAddress.setText(buyer.getBuyerAddress());
            txtBuyerContactOffice.setText(buyer.getBuyerContactOffice());
            txtBuyerContactManager.setText(buyer.getBuyerContactManager());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Buyer not found!").show();
            txtBuyerName.setText("");
            txtBuyerAddress.setText("");
            txtBuyerContactOffice.setText("");
            txtBuyerContactManager.setText("");
        }
    }

    private void animateLabelTyping() {
        String loginText = lblBuyerForm.getText();
        int animationDuration = 250;

        lblBuyerForm.setText("");

        Timeline typingAnimation = new Timeline();

        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblBuyerForm.setText(loginText.substring(0, finalI));
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}
