package com.adrian.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;

public class SelectionController {

    @FXML
    private Button settingsButton;

    @FXML
    private void handleTicketsClick() {
        System.out.println("Клік: Квитки");
    }

    @FXML
    private void handleMenuClick() {
        System.out.println("Клік: Меню");
    }

    @FXML
    private void handleSettingsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login-form.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            loginStage.setScene(scene);
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initOwner(settingsButton.getScene().getWindow());
            loginStage.setAlwaysOnTop(true);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}