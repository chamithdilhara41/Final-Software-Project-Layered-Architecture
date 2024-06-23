package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lk.ijse.util.AnimationUtil;
import lombok.SneakyThrows;

public class MainFormController implements Initializable {

    @FXML
    private JFXButton btnBuyer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnStock;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnTransactions;

    @FXML
    private JFXButton btnVehicle;

    @FXML
    public Label lblName;

    @FXML
    public Label lblDate;

    @FXML
    public Label lblTime;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane rootNode;
    @SneakyThrows


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AnimationUtil.addPulseAnimation(btnBuyer);
        AnimationUtil.addPulseAnimation(btnDashboard);
        AnimationUtil.addPulseAnimation(btnDelivery);
        AnimationUtil.addPulseAnimation(btnEmployee);
        AnimationUtil.addPulseAnimation(btnLogout);
        AnimationUtil.addPulseAnimation(btnOrders);
        AnimationUtil.addPulseAnimation(btnPayment);
        AnimationUtil.addPulseAnimation(btnSettings);
        AnimationUtil.addPulseAnimation(btnStock);
        AnimationUtil.addPulseAnimation(btnSupplier);
        AnimationUtil.addPulseAnimation(btnTransactions);
        AnimationUtil.addPulseAnimation(btnVehicle);
        loadDashboardForm();
        setDate();
        setTime();
        lblName.setText(LoginFormController.Name+"...");
        animateLabelTyping();

    }

    @FXML
    void btnOnActionBuyer(ActionEvent event) throws IOException {
        AnchorPane buyerPane = FXMLLoader.load(getClass().getResource("/view/BuyerForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(buyerPane);
        AnimationUtil.popUpAnimation(mainPane,buyerPane);
    }

    @FXML
    void btnOnActionDashboard(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
        AnimationUtil.popUpAnimation(mainPane,dashboardPane);

    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
    }

    @FXML
    void btnOnActionEmployee(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/view/EmployeeForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(employeePane);
        AnimationUtil.popUpAnimation(mainPane,employeePane);
    }

    @FXML
    void btnOnActionLogout(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(root);

        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("Login Form");

        loginStage.show();
    }

    @FXML
    void btnOnActionOrders(ActionEvent event) throws IOException {
        AnchorPane orderPane = FXMLLoader.load(getClass().getResource("/view/OrderForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(orderPane);
        AnimationUtil.popUpAnimation(mainPane,orderPane);
    }

    @FXML
    void btnOnActionPayment(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(paymentPane);
        AnimationUtil.popUpAnimation(mainPane,paymentPane);
    }

    @FXML
    void btnOnActionDelivery(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(getClass().getResource("/view/DeliveryForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(paymentPane);
        AnimationUtil.popUpAnimation(mainPane,paymentPane);
    }

    @FXML
    void btnOnActionSettings(ActionEvent event) throws IOException {
        AnchorPane settingsPane = FXMLLoader.load(getClass().getResource("/view/SettingsForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(settingsPane);
        AnimationUtil.popUpAnimation(mainPane,settingsPane);
    }

    @FXML
    void btnOnActionStock(ActionEvent event) throws IOException {
        AnchorPane stockPane = FXMLLoader.load(getClass().getResource("/view/StockForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(stockPane);
        AnimationUtil.popUpAnimation(mainPane,stockPane);
    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) throws IOException {

        AnchorPane supplierPane = FXMLLoader.load(getClass().getResource("/view/SupplierForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(supplierPane);
        AnimationUtil.popUpAnimation(mainPane,supplierPane);
    }

    @FXML
    void btnOnActionTransactions(ActionEvent event) throws IOException {
        AnchorPane transactinPane = FXMLLoader.load(getClass().getResource("/view/TransactionForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(transactinPane);
        AnimationUtil.popUpAnimation(mainPane, transactinPane);
    }

    @FXML
    void btnOnActionVehicle(ActionEvent event) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().add(vehicleForm);
        AnimationUtil.popUpAnimation(mainPane, vehicleForm);
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            LocalTime currentTime = LocalTime.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = currentTime.format(timeFormatter);

            lblTime.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);

        clock.play();
    }

    private void animateLabelTyping() {
        String loginText = lblName.getText(); // Text to be typed
        int animationDuration = 350; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblName.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblName.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }





}
