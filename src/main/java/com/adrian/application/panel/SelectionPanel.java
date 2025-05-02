package com.adrian.application.panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SelectionPanel {

    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/selection-page.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
    }
    public Scene getScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/selection-page.fxml"));
            return new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Pane getRoot() throws Exception {
        return FXMLLoader.load(getClass().getResource("/fxml/selection-page.fxml"));
    }
}
