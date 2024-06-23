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
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.SupplierStockDetailDAO;
import lk.ijse.dto.Stock;
import lk.ijse.dto.Supplier;
import lk.ijse.dto.SupplierStockDetail;
import lk.ijse.dto.tdm.StockTm;
import lk.ijse.dto.tdm.SupplierStockDetailTm;
import lk.ijse.dao.custom.impl.StockDAOImpl;
import lk.ijse.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.dao.custom.impl.SupplierStockDetailDAOImpl;
import lk.ijse.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StockFormController {

    public Label lblStockForm;

    @FXML
    private JFXComboBox<String> cmbSupplierID;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TextField txtSuppliersStockID;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colTotalWeight;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<StockTm> tblStock;

    @FXML
    private TableView<SupplierStockDetailTm> tblSupplierStock;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtStockID;

    @FXML
    private TextField txtWeight;

    //dependency injection
    SupplierDAO supplierDAO = new SupplierDAOImpl();
    SupplierStockDetailDAO supplierStockDetailDAO = new SupplierStockDetailDAOImpl();
    StockDAO stockDAO = new StockDAOImpl();

    public void initialize() throws SQLException {
        animateLabelTyping();
        getAllStocks();
        setCellValueFactoryForStocks();
        getSupplierIds();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE,txtDate);
    }

    @FXML
    void txtStockIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.SID,txtStockID);
    }

    @FXML
    void txtSupStockIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.SID,txtSuppliersStockID);
    }

    @FXML
    void txtWeightOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.WEIGHT,txtWeight);
    }

    public  boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.util.TextField.SID,txtStockID)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.WEIGHT,txtWeight)) return false;
        if(!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtDate)) return false;
        return true;
    }

    private void setCellValueFactoryForStocks() {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colTotalWeight.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAllStocks() throws SQLException {
        ObservableList<StockTm> obList = FXCollections.observableArrayList();
        List<Stock> stocksList = stockDAO.getAll();

        for (Stock t : stocksList) {
            obList.add(new StockTm(
                    t.getStockId(),
                    t.getWeight(),
                    t.getDate()
            ));
        }
        tblStock.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) throws SQLException {
        int index = tblStock.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colStockID.getCellData(index).toString();
        txtStockID.setText(id);

        String stockID = colStockID.getCellData(index).toString();
        ObservableList<SupplierStockDetailTm> obList = FXCollections.observableArrayList();

        List<SupplierStockDetailTm> supplierStockDetail = supplierStockDetailDAO.searchSuppliersWithStockId(stockID);

            for (SupplierStockDetailTm No : supplierStockDetail) {
                obList.add(new SupplierStockDetailTm(
                        No.getSupplierId(),
                        No.getName(),
                        No.getWeight()
                ));
            }

        tblSupplierStock.setItems(obList);
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        cmbSupplierID.setValue("");
        txtWeight.setText("");
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String stockID = txtStockID.getText();

        try {
            boolean isDeleted = stockDAO.delete(stockID);
            if (isDeleted) {
                getAllStocks();
                setCellValueFactoryForStocks();

                new Alert(Alert.AlertType.INFORMATION, "Stock Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public boolean isValidSupplier(){
        if(!Regex.setTextColor(lk.ijse.util.TextField.SID,txtSuppliersStockID)) return false;
        return true;
    }

    @FXML
    void txtOnActionSearchSuppliers(ActionEvent event) throws SQLException {
        if (isValidSupplier()) {
            String stockID = txtSuppliersStockID.getText();
            ObservableList<SupplierStockDetailTm> obList = FXCollections.observableArrayList();

            List<SupplierStockDetailTm> supplierStockDetail = supplierStockDetailDAO.searchSuppliersWithStockId(stockID);

            if (supplierStockDetail.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Suppliers not found").show();
            } else {
                for (SupplierStockDetailTm No : supplierStockDetail) {
                    obList.add(new SupplierStockDetailTm(
                            No.getSupplierId(),
                            No.getName(),
                            No.getWeight()
                    ));
                }
            }
            tblSupplierStock.setItems(obList);
            colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
            colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Check supplier stock textField....").show();
        }
    }

    @FXML
    void btnOnActionAddWeight(ActionEvent event) throws SQLException {
        String stockID = txtStockID.getText();
        String supplierID = cmbSupplierID.getValue();
        String weight = String.valueOf(txtWeight.getText());
        String date = String.valueOf(Date.valueOf(txtDate.getText()));

        try {
            if (stockID.isEmpty() || supplierID.isEmpty() || weight.isEmpty() || date.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        Stock stock = new Stock(stockID, Double.valueOf(weight), Date.valueOf(date));

        SupplierStockDetail supplierStockDetail = new SupplierStockDetail(stockID, supplierID, Double.valueOf(weight));

        try {
            boolean issaved1 = false;
            if (isValid()) {
                issaved1 = stockDAO.save(stock);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            boolean isSaved = false;
                if (issaved1 ) {
                    if (isValid()) {
                        isSaved = supplierStockDetailDAO.save(supplierStockDetail);
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
                    }
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "weight saved!").show();
                        getAllStocks();
                        setCellValueFactoryForStocks();
                        cmbSupplierID.setValue("");
                        txtWeight.setText("");
                    }

                }
            } catch (SQLException e) {

            boolean isUpdateWeight = stockDAO.updateWeight(stockID,supplierID, Double.valueOf(weight));
            if (isUpdateWeight) {

                boolean fuck = supplierStockDetailDAO.save(supplierStockDetail);
                if (fuck) {
                    new Alert(Alert.AlertType.INFORMATION, " weight updated!").show();
                    getAllStocks();
                    setCellValueFactoryForStocks();
                }

            }
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String stockID = txtStockID.getText();
        //String supplierID = cmbSupplierID.getValue();
        Double weight = Double.valueOf(txtWeight.getText());
        Date date = Date.valueOf(txtDate.getText());

        Stock stock = new Stock(stockID, weight, date);
        try {
            boolean isSaved = stockDAO.save(stock);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        String supplierID = cmbSupplierID.getValue();

        try {
            Supplier supplier = supplierDAO.searchBySupplierIdForPayment(supplierID);
            if(supplier!=null){
                lblSupplierName.setText(supplier.getSupplierName());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

    private void getSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = supplierDAO.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbSupplierID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void animateLabelTyping() {
        String loginText = lblStockForm.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblStockForm.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblStockForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }
}
