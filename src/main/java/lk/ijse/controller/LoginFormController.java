package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.util.AnimationUtil;
import lk.ijse.db.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lk.ijse.util.Regex;
import lk.ijse.util.SQLUtil;


public class LoginFormController {

    @FXML
    private Label lblLogin;

    @FXML
    private PasswordField txtPasswordLogin;

    @FXML
    private TextField txtUsernameLogin;

    public static String UserName;
    public static String Name;

    public void initialize() {
        animateLabelTyping();
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColorLogin(lk.ijse.util.TextField.USERNAME,txtUsernameLogin);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColorLogin(lk.ijse.util.TextField.PASSWORD,txtPasswordLogin);
    }

    public boolean isValid(){
        if (!Regex.setTextColorLogin(lk.ijse.util.TextField.USERNAME,txtUsernameLogin)) return false;
        if (!Regex.setTextColorLogin(lk.ijse.util.TextField.PASSWORD,txtPasswordLogin)) return false;
        return true;
    }

    @FXML
    void btnLoginOnAction() throws SQLException, IOException, ClassNotFoundException {

        String usernameLogin = txtUsernameLogin.getText();
        String passwordLogin = txtPasswordLogin.getText();

        if (!isValid()) {
            if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please fill all.....", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "Check Username and Password Text fields again", ButtonType.OK).show();
        } else {

            //UserRepo.getLoginDetails(usernameLogin);

            ResultSet resultSet = SQLUtil.execute("SELECT username,password,name FROM users WHERE username = ?",usernameLogin);

            if (resultSet.next()) {
                String dbPw = resultSet.getString("password");
                String dbName = resultSet.getString("name");
                if (passwordLogin.equals(dbPw)) {
                    UserName= txtUsernameLogin.getText();
                    Name=dbName;
                    new Alert(Alert.AlertType.INFORMATION, "Login Successful").show();
                    navigateToTheMainForm();

                } else {
                    new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "sorry! username can't be find!").show();
            }

        }
    }

    private void navigateToTheMainForm () throws IOException {


        // Load the FXML file
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        // Create a new Scene
        Scene scene = new Scene(rootNode);

        // Get the Stage from the current window
        Stage stage = (Stage) txtPasswordLogin.getScene().getWindow();
        AnimationUtil.popUpAnimation1(stage, rootNode);
        // Set the new scene to the stage
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Main Form (Tea leaves stock Management System)");
    }


    @FXML
    void hyperOnActionForgetPassword(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/ForgetGetUsernameEmailForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Password Forget Form");
        stage.show();
    }

    @FXML
    void hyperOnActionRegister(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Register Form");
        stage.show();
    }

    public void txtOnActionLogin(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        btnLoginOnAction();
    }

    private void animateLabelTyping() {
        String loginText = lblLogin.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblLogin.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblLogin.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }
}
