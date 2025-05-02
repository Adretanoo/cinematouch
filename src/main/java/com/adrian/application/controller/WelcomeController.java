package com.adrian.application.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WelcomeController {

    @FXML
    private VBox root;

    @FXML
    private Label topLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private void initialize() {
        // Анімація появи тексту
        FadeTransition fadeText = new FadeTransition(Duration.seconds(2), topLabel);
        fadeText.setFromValue(0);
        fadeText.setToValue(1);
        fadeText.play();

        // Завантаження картинки
        Image image = new Image(getClass().getResourceAsStream("/images/icon-view-1.png"));
        imageView.setImage(image);

        // Анімація появи картинки
        FadeTransition fadeIcon = new FadeTransition(Duration.seconds(2), imageView);
        fadeIcon.setFromValue(0);
        fadeIcon.setToValue(1);
        fadeIcon.play();

        // Пульсація
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1), imageView);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.1);
        pulse.setToY(1.1);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    @FXML
    private void handleClick(MouseEvent event) {
        root.setVisible(false);
    }
}
