package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BuyerBO;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.bo.custom.StockBO;
import lk.ijse.bo.custom.impl.BuyerBOImpl;
import lk.ijse.bo.custom.impl.OrderBOImpl;
import lk.ijse.bo.custom.impl.PlaceOrderBOImpl;
import lk.ijse.bo.custom.impl.StockBOImpl;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.StockDAO;
import lk.ijse.dao.custom.impl.*;
import lk.ijse.util.AnimationUtil;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.*;
import lk.ijse.tdm.OrderCartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController {

    public Label lblPlaceOrderForm;

    public AnchorPane mainPane;

    public Label lblStocksTotalWeight;

    @FXML
    private JFXComboBox<String> cmbBuyerId;

    @FXML
    private JFXComboBox<String> cmbStockID;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBuyerID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TableColumn<?, ?> colStockWeight;

    @FXML
    private Label lblBuyerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblStockWeight;

    @FXML
    private TableView<OrderCartTm> tblOrderPlace;

    private ObservableList<OrderCartTm> obList = FXCollections.observableArrayList();

    //dependency injection
    BuyerBO buyerBO = (BuyerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BUYER);
    StockBO stockBO = (StockBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);


    public void initialize() throws ClassNotFoundException {
        animateLabelTyping();
        getCurrentOrderId();
        getBuyerIds();
        getStockIds();
        lblDate.setText(String.valueOf(LocalDate.now()));
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colBuyerID.setCellValueFactory(new PropertyValueFactory<>("buyerID"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        colStockWeight.setCellValueFactory(new PropertyValueFactory<>("stockWeight"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {

        if (cmbStockID.getSelectionModel().isEmpty() || cmbBuyerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Select Stock ID and Buyer ID", ButtonType.OK).show();
            return;
        }

        String buyerID = cmbBuyerId.getValue();
        String buyerName = lblBuyerName.getText();
        String date = lblDate.getText();
        String stockID = cmbStockID.getValue();
        String stockWeight = lblStockWeight.getText();
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setStyle("-fx-alignment: center; -fx-background-color: #ff5b5b ;");

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderPlace.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrderPlace.refresh();
            }
        });
        OrderCartTm tm = new OrderCartTm(buyerID, buyerName, date, stockID, stockWeight, btnRemove);
        obList.add(tm);


        tblOrderPlace.setItems(obList);
        calculateNetTotal();
        cmbBuyerId.getSelectionModel().clearSelection();
        cmbStockID.getSelectionModel().clearSelection();

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) throws IOException, JRException, SQLException, ClassNotFoundException {

        if (tblOrderPlace.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Add to cart firstly...", ButtonType.OK).show();
            return;
        }

        String orderID = lblOrderID.getText();
        String date = lblDate.getText();

        var order = new Order(orderID,  date);

        List<OrderDetail> odList = new ArrayList<>();

        for (int i = 0; i < tblOrderPlace.getItems().size(); i++) {
            OrderCartTm tm = obList.get(i);

            OrderDetail od =new OrderDetail(
                    tm.getStockID(),
                    orderID,
                    tm.getBuyerID()
            );
            odList.add(od);

        }

        PlaceOrder po = new PlaceOrder(order, odList);

        try {

            boolean isPlaced = placeOrderBO.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!",ButtonType.OK).show();
                getStockIds();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!",ButtonType.OK).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/PrintsBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("orderId",lblOrderID.getText());
        data.put("date",lblDate.getText());
        data.put("time", LocalTime.now().toString());
        data.put("netWeight",lblStocksTotalWeight.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data,DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

        cmbBuyerId.getSelectionModel().clearSelection();
        cmbStockID.getSelectionModel().clearSelection();
        tblOrderPlace.getItems().clear();
        tblOrderPlace.refresh();
        lblStocksTotalWeight.setText("");
        getCurrentOrderId();

    }
    private void calculateNetTotal() {
        double netTotal = 0;
        for (int i = 0; i < tblOrderPlace.getItems().size(); i++) {
            OrderCartTm tm = obList.get(i);

            double weight = Double.parseDouble(tm.getStockWeight());

            netTotal += weight;
        }
        lblStocksTotalWeight.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnOnActionBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderForm.fxml"));
        AnchorPane contentPane = loader.load();

        // Add the loaded content to the main pane
        mainPane.getChildren().clear();
        mainPane.getChildren().add(contentPane);
        AnimationUtil.popUpAnimation(mainPane,contentPane);

    }

    @FXML
    void cmbBuyerIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String No = cmbBuyerId.getValue();
        try {
            Buyer buyer = buyerBO.searchByBuyerIdForOrderBuyer(No);

            if (buyer != null) {
                lblBuyerName.setText(buyer.getBuyerName());
            } else {
                // Handle case when vehicle is not found
                lblBuyerName.setText("");
            }

        } catch (SQLException e) {
            // Handle any SQLException that might occur during the search
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for buyer: " + e.getMessage()).show();
        }
    }

    @FXML
    void cmbStockIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String No = cmbStockID.getValue();
        try {
            Stock stock = stockBO.searchByStockIdForOrderStock(No);

            if (stock != null) {
                lblStockWeight.setText(String.valueOf(stock.getWeight()));
            } else {
                // Handle case when vehicle is not found
                lblStockWeight.setText("");
            }

        } catch (SQLException e) {
            // Handle any SQLException that might occur during the search
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for buyer: " + e.getMessage()).show();
        }
    }

    private void getBuyerIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = buyerBO.getIdsBuyer();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbBuyerId.setItems(obList);

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

    private void getCurrentOrderId() throws ClassNotFoundException {
        try {
            String currentId = orderBO.getCurrentIdOrder();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderID.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private String generateNextOrderId(String currentId) {

        if(currentId != null) {

            String[] split = currentId.split("[oO]");

            int idNum = Integer.parseInt(split[1]);

            return "O" + String.format("%03d", ++idNum);

        }

        return "O001";
    }

    private void animateLabelTyping() {
        String loginText = lblPlaceOrderForm.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblPlaceOrderForm.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblPlaceOrderForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }

}









