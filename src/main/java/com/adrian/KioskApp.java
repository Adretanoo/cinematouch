package com.adrian;

import com.adrian.application.panel.SelectionPanel;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KioskApp extends Application {

    private StackPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Отримати розміри екрану
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Створюємо root і сцену
        root = new StackPane();
        root.setStyle("-fx-background-color: #0B0F1C;"); // Темний фон замість білого
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);


        // Завантажуємо welcome-pane
        Pane welcomePane = FXMLLoader.load(getClass().getResource("/fxml/welcome-page.fxml"));
        root.getChildren().setAll(welcomePane);

        // Обробка кліку по welcome
        welcomePane.setOnMouseClicked((MouseEvent e) -> switchToSelectionScreen());

        // Налаштування вікна
        primaryStage.setTitle("Cinema Kiosk");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void switchToSelectionScreen() {
        try {
            Pane selectionPane = new SelectionPanel().getRoot(); // отримаємо корінь (тип Pane)
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), root.getChildren().get(0));
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                root.getChildren().setAll(selectionPane);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), selectionPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
