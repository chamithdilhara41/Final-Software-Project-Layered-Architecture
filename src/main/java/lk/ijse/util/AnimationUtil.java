package lk.ijse.util;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AnimationUtil implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing AnimationUtil");
    }

    public static void popUpAnimation(AnchorPane stage, Parent rootNode) {

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), rootNode);
        translateTransition.setFromX(+rootNode.getBoundsInLocal().getWidth());
        translateTransition.setToX(0);
        translateTransition.play();
    }

    public static void popUpAnimation1(Stage stage, Parent rootNode) {

        stage.setHeight(792);
        stage.setWidth(1164);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), rootNode);
        translateTransition.setFromY(-rootNode.getBoundsInLocal().getHeight());
        translateTransition.setToY(0);
        translateTransition.play();

        stage.show();
    }

    public static void addFadeAnimation(JFXButton button) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), button);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), button);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        button.setOnMouseEntered(event -> {
            fadeIn.play();
        });

        button.setOnMouseExited(event -> {
            fadeOut.play();
        });
    }

    public static void addPulseAnimation(JFXButton button) {
        ScaleTransition pulseIn = new ScaleTransition(Duration.millis(200), button);
        pulseIn.setFromX(1.0);
        pulseIn.setFromY(1.0);
        pulseIn.setToX(1.1);
        pulseIn.setToY(1.1);

        ScaleTransition pulseOut = new ScaleTransition(Duration.millis(200), button);
        pulseOut.setFromX(1.1);
        pulseOut.setFromY(1.1);
        pulseOut.setToX(1.0);
        pulseOut.setToY(1.0);

        button.setOnMouseEntered(event -> {
            pulseIn.play();
        });

        button.setOnMouseExited(event -> {
            pulseOut.play();
        });
    }

}
