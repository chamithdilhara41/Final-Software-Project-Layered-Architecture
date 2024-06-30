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
import lk.ijse.bo.custom.StockBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.bo.custom.SupplierStockDetailBO;
import lk.ijse.bo.custom.impl.StockBOImpl;
import lk.ijse.bo.custom.impl.SupplierBOImpl;
import lk.ijse.bo.custom.impl.SupplierStockDetailBOImpl;
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.SupplierStockDetailDAO;
import lk.ijse.entity.Stock;
import lk.ijse.entity.Supplier;
import lk.ijse.entity.SupplierStockDetail;
import lk.ijse.tdm.StockTm;
import lk.ijse.tdm.SupplierStockDetailTm;
import lk.ijse.dao.custom.impl.StockDAOImpl;
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
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    SupplierStockDetailBO supplierStockDetailBO = (SupplierStockDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER_STOCK_DTL);
    StockBO stockBO = (StockBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);

    public void initialize() throws SQLException, ClassNotFoundException {
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

    private void getAllStocks() throws SQLException, ClassNotFoundException {
        ObservableList<StockTm> obList = FXCollections.observableArrayList();
        List<Stock> stocksList = stockBO.getAllStock();

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
    void OnMouseClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        int index = tblStock.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colStockID.getCellData(index).toString();
        txtStockID.setText(id);

        String stockID = colStockID.getCellData(index).toString();
        ObservableList<SupplierStockDetailTm> obList = FXCollections.observableArrayList();

        List<SupplierStockDetailTm> supplierStockDetail = supplierStockDetailBO.searchSuppliersWithStockIdSupplierStockDetail(stockID);

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
    void btnOnActionDelete(ActionEvent event) throws ClassNotFoundException {
        String stockID = txtStockID.getText();

        try {
            boolean isDeleted = stockBO.deleteStock(stockID);
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
    void txtOnActionSearchSuppliers(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (isValidSupplier()) {
            String stockID = txtSuppliersStockID.getText();
            ObservableList<SupplierStockDetailTm> obList = FXCollections.observableArrayList();

            List<SupplierStockDetailTm> supplierStockDetail = supplierStockDetailBO.searchSuppliersWithStockIdSupplierStockDetail(stockID);

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
    void btnOnActionAddWeight(ActionEvent event) throws SQLException, ClassNotFoundException {
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
                issaved1 = stockBO.saveStock(stock);
            }else {
                new Alert(Alert.AlertType.ERROR, "Please check Text Fields... ").show();
            }
            boolean isSaved = false;
                if (issaved1 ) {
                    if (isValid()) {
                        isSaved = supplierStockDetailBO.saveSupplierStockDetail(supplierStockDetail);
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
            } catch (SQLException | ClassNotFoundException e) {

            boolean isUpdateWeight = stockBO.updateWeightStock(stockID,supplierID, Double.valueOf(weight));
            if (isUpdateWeight) {

                boolean fuck = supplierStockDetailBO.saveSupplierStockDetail(supplierStockDetail);
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
    void btnOnActionSave(ActionEvent event) throws ClassNotFoundException {
        String stockID = txtStockID.getText();
        //String supplierID = cmbSupplierID.getValue();
        Double weight = Double.valueOf(txtWeight.getText());
        Date date = Date.valueOf(txtDate.getText());

        Stock stock = new Stock(stockID, weight, date);
        try {
            boolean isSaved = stockBO.saveStock(stock);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String supplierID = cmbSupplierID.getValue();

        try {
            Supplier supplier = supplierBO.searchBySupplierIdForPaymentSupplier(supplierID);
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

    private void getSupplierIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = supplierBO.getIdsSupplier();

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
