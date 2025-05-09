package com.adrian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Завантажуємо FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin-dashboard.fxml"));
        Parent root = loader.load();

        // Створюємо сцену та підключаємо CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/admin-dashboard.css").toExternalForm());

        // Налаштовуємо та показуємо вікно
        primaryStage.setTitle("Admin Panel");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
