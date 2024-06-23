package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;
import lk.ijse.util.Regex;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SettingsFormController {

    @FXML
    public Label lblUsername;

    @FXML
    private Label lblSettingsForm;

    @FXML
    private TextField txtNewEmail1;

    @FXML
    private TextField txtNewEmail2;

    @FXML
    private TextField txtNewPassword1;

    @FXML
    private TextField txtNewPassword2;

    public void initialize() {
        animateLabelTyping();
        lblUsername.setText(LoginFormController.UserName);
    }


    @FXML
    void btnOnActionSaveEmail(ActionEvent event) {
        String email1 = txtNewEmail1.getText();
        String email2 = txtNewEmail2.getText();
        String username = lblUsername.getText();

        if (email1.isEmpty() || email2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK).show();
            return;
        }

        try {
            if (!isValidEmail()) {
                boolean isChangeEmail = false;
                if (email1.equals(email2)) {
                    isChangeEmail = SettingsFormController.changeEmail(username,email2);
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Email does not matched.", ButtonType.OK).show();
                }
                if (isChangeEmail) {
                    new Alert(Alert.AlertType.INFORMATION, "Email changed successfully", ButtonType.OK).show();
                    txtNewEmail1.clear();
                    txtNewEmail2.clear();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "check Fields..", ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

    }

    private static boolean changeEmail(String username, String email2) throws SQLException {

        String sql = "update users set email=? where username=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, email2);
        pstm.setString(2, username);

        return pstm.executeUpdate() > 0;

    }

    @FXML
    void btnOnActionSaveNewPassword(ActionEvent event) {
        String newPassword1 = txtNewPassword1.getText();
        String newPassword2 = txtNewPassword2.getText();
        String username = lblUsername.getText();

        if (newPassword1.isEmpty() || newPassword2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK).show();
            return;
        }

        try {
            if (!isValidPassword()) {
                boolean isChangePassword = false;
                if (newPassword1.equals(newPassword2)) {
                    isChangePassword = SettingsFormController.changePassword(username,newPassword2);
                }else {
                    new Alert(Alert.AlertType.ERROR, "Passwords does not match", ButtonType.OK).show();
                }
                if (isChangePassword) {
                    new Alert(Alert.AlertType.INFORMATION, "Password changed successfully",ButtonType.OK).show();
                    txtNewPassword1.clear();
                    txtNewPassword2.clear();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Password not changed ",ButtonType.OK).show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Check password fields", ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private static boolean changePassword(String username, String newPassword2) throws SQLException {
        String sql ="UPDATE `users` SET `password`=? WHERE `username`=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, newPassword2);
        pstm.setString(2, username);

        return pstm.executeUpdate() > 0;
    }


    @FXML
    void txtNewEmail1OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtNewEmail1);
    }

    @FXML
    void txtNewEmail2OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtNewEmail2);
    }

    public boolean isValidEmail() {
        if (Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtNewEmail1)) return false;
        if (Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtNewEmail2)) return false;
        return true;
    }

    @FXML
    void txtNewPassword1OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword1);
    }

    @FXML
    void txtNewPassword2OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword2);
    }

    public boolean isValidPassword(){
        if (Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword1)) return false;
        if (Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword2)) return false;
        return true;
    }

    private void animateLabelTyping() {
        String loginText = lblSettingsForm.getText();
        int animationDuration = 250;

        lblSettingsForm.setText("");

        Timeline typingAnimation = new Timeline();


        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblSettingsForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }
}
