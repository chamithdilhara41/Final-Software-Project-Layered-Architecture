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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.util.AnimationUtil;
import lk.ijse.dto.Order;
import lk.ijse.dto.tdm.OrderStockTm;
import lk.ijse.dto.tdm.OrderTm;
import lk.ijse.dao.custom.impl.OrderDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderFormController {


    @FXML
    private AnchorPane mainPane; // Define mainPane variable and annotate with @FXML

    @FXML
    private JFXButton btnPlaceOrder;

    public Label lblOrderForm;

    @FXML
    private TableView<OrderStockTm> tblOrderStock;

    @FXML
    private TableColumn<?, ?> colOrderIDStockID;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TableColumn<?, ?> colBuyerID;

    @FXML
    private JFXComboBox<String> cmbBuyerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private Label lblBuyerName;

    @FXML
    private TableView<OrderTm> tblOrder;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderID;

    //dependency injection
    OrderDAO orderDAO = new OrderDAOImpl();

    public void initialize() throws SQLException {
        getAllOrders();
        setCellValueFactoryOrders();
        animateLabelTyping();
        getAllOrderStocks();
        setCellValueFactoryOrderStock();
        //getBuyerIds();
        //txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void getAllOrderStocks() throws SQLException {
        ObservableList<OrderStockTm> obList = FXCollections.observableArrayList();
        List<OrderStockTm> OrderStockList = orderDAO.getAllOrderStocks();
        for ( OrderStockTm OrderStock: OrderStockList){
            obList.add(new OrderStockTm(
                    OrderStock.getStockID(),
                    OrderStock.getOrderID(),
                    OrderStock.getBuyerID()
            ));
        }
        tblOrderStock.setItems(obList);
    }

    private void setCellValueFactoryOrderStock() {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        colOrderIDStockID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colBuyerID.setCellValueFactory(new PropertyValueFactory<>("buyerID"));
    }

    private void setCellValueFactoryOrders() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAllOrders() throws SQLException {
        ObservableList<OrderTm> obList = FXCollections.observableArrayList();
        List<Order> ordersList = orderDAO.getAll();

        for ( Order order: ordersList){
            obList.add(new OrderTm(
                    order.getOrderId(),
                    order.getDate()
            ));
        }
        tblOrder.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtOrderID.setText("");
        //txtDate.setText("");
        //cmbBuyerId.getSelectionModel().clearSelection();
    }

    public void btnOnActionPlaceOrder(javafx.event.ActionEvent actionEvent) throws IOException {
//        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"));
//
//        mainPane.getChildren().clear();
//        mainPane.getChildren().add(dashboardPane);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlaceOrderForm.fxml"));
        AnchorPane contentPane = loader.load();

        // Add the loaded content to the main pane
        mainPane.getChildren().clear();
        mainPane.getChildren().add(contentPane);
        AnimationUtil.popUpAnimation(mainPane, contentPane);
    }

    private void animateLabelTyping() {
        String loginText = lblOrderForm.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblOrderForm.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblOrderForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }
//    private void getBuyerIds() {
//        ObservableList<String> obList = FXCollections.observableArrayList();
//
//        try {
//            List<String> NoList = BuyerRepo.getIds();
//
//            for(String No : NoList) {
//                obList.add(No);
//            }
//
//            cmbBuyerId.setItems(obList);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
