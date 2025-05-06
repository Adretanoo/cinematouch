package com.adrian.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ImageView qrImage;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private VBox loginBox;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        Image qr = new Image(getClass().getResourceAsStream("/images/qr_code.png"));
        qrImage.setImage(qr);

        signInButton.setOnAction(e -> handleLogin());

        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        loginBox.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
        });
        loginBox.setClip(clip);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void handleLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.equals("admin") && password.equals("1234")) {
            System.out.println("Авторизація успішна");
        } else {
            System.out.println("Невірний логін або пароль");
        }
    }
}
