package com.adrian;

import com.adrian.application.panel.SelectionPanel;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KioskApp extends Application {

    private StackPane root;
    private PauseTransition inactivityTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        root = new StackPane();
        root.setStyle("-fx-background-color: #0B0F1C;");
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);

        Pane welcomePane = FXMLLoader.load(getClass().getResource("/fxml/welcome-page.fxml"));
        root.getChildren().setAll(welcomePane);

        // Клік по welcome -> перехід до Selection
        welcomePane.setOnMouseClicked((MouseEvent e) -> switchToSelectionScreen());

        primaryStage.setTitle("Cinema Kiosk");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void switchToSelectionScreen() {
        try {
            Pane selectionPane = new SelectionPanel().getRoot();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), root.getChildren().get(0));
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                root.getChildren().setAll(selectionPane);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), selectionPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                // ▶️ Запуск таймера бездіяльності на 30 секунд
                startInactivityTimer();

                // ⛔ Скидання таймера при будь-якій взаємодії
                selectionPane.setOnMouseMoved(ev -> resetInactivityTimer());
                selectionPane.setOnMouseClicked(ev -> resetInactivityTimer());
            });

            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnToWelcomeScreen() {
        try {
            Pane welcomePane = FXMLLoader.load(getClass().getResource("/fxml/welcome-page.fxml"));
            root.getChildren().setAll(welcomePane);

            // Дозволяємо повторно перейти в selection
            welcomePane.setOnMouseClicked((MouseEvent e) -> switchToSelectionScreen());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startInactivityTimer() {
        inactivityTimer = new PauseTransition(Duration.seconds(30));
        inactivityTimer.setOnFinished(e -> returnToWelcomeScreen());
        inactivityTimer.play();
    }

    private void resetInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.stop();
            inactivityTimer.play();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
