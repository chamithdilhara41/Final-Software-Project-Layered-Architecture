package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForgetGetUsernameEmailController {

    @FXML
    private Label lblForgetPassword;

    @FXML
    private Label lblCheckUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsername;

    public static int OTP;
    public static String Username;
    public ObservableList<String> obList;

    public void initialize() throws SQLException {
        animateLabelTyping();
        getUsernames();
    }

    @FXML
    void btnForEmailOnAction(ActionEvent event) throws SQLException, IOException {
        String username = txtUsername.getText();

        try {
            String isCheckedEmail = ForgetGetUsernameEmailController.checkEmail(username);
            if (isCheckedEmail != null) {
                txtEmail.setText(isCheckedEmail);
                btnSendOtpOnAction(event);
            } else {
                txtEmail.clear();
                new Alert(Alert.AlertType.ERROR, "Can't find Email...", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String checkEmail(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?"; // Specify the column name for comparison
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, username);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("email");
        }
        return null;
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        boolean isValid = false;

        for(String s : obList) {
            if(s.equals(txtUsername.getText())) {
                isValid = true;
                break;
            }
        }

        if(isValid) {
            lblCheckUsername.setText("Valid Username and press Enter get email...");
            lblCheckUsername.setStyle("-fx-text-fill: #00b600;");
        } else {
            lblCheckUsername.setText("Invalid Username...");
            lblCheckUsername.setStyle("-fx-text-fill: #f33232;");
        }
    }

    private void getUsernames() {
        obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = ForgetGetUsernameEmailController.getUsers();

            for(String No : NoList) {
                obList.add(No);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getUsers() throws SQLException {
        String sql = "SELECT username FROM users";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }   
        return idList;
    }


    @FXML
    void btnSendOtpOnAction(ActionEvent event) throws IOException {

        String email = txtEmail.getText();
        String username = txtUsername.getText();

        if (username!=null) {
            Random random = new Random();
            int otp = 1000 + random.nextInt(9000);

            boolean sendingOTP = JavaMailUtil.sendMail(email, otp);
                if (sendingOTP) {
                    new Alert(Alert.AlertType.INFORMATION, "OTP Sent", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "OTP Failed", ButtonType.OK).show();
                }

            Username = username;
            OTP = otp;
            System.out.println(">>>" + otp);

            Parent root = FXMLLoader.load(getClass().getResource("/view/ForgetPasswordOTPForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("OTP Form");
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR,"username type", ButtonType.OK).show();
        }

    }

    @FXML
    void hyperOnActionLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

    private void animateLabelTyping()  {
        String loginText = lblForgetPassword.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblForgetPassword.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblForgetPassword.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }

}
