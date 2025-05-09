package com.adrian.application.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AdminDashboardController {
    @FXML private StackPane mainContent;
    @FXML private Button dashboardBtn, moviesBtn, logoutBtn;

    @FXML
    public void initialize() {
        List<Button> buttons = List.of(dashboardBtn, moviesBtn, logoutBtn);
        buttons.forEach(btn -> btn.setOnAction(e -> switchView(btn)));
        dashboardBtn.fire();
    }

    private void switchView(Button btn) {
        List<Button> all = List.of(dashboardBtn, moviesBtn, logoutBtn);
        all.forEach(b -> b.getStyleClass().remove("active"));
        btn.getStyleClass().add("active");

        FadeTransition fo = new FadeTransition(Duration.millis(150), mainContent);
        fo.setFromValue(1); fo.setToValue(0);
        fo.setOnFinished(ev -> {
            Node content;
            if (btn == moviesBtn) {
                content = loadFXML("/fxml/movies-panel.fxml");
            } else if (btn == logoutBtn) {
                System.exit(0); return;
            } else {
                Label lbl = new Label("Dashboard");
                lbl.getStyleClass().add("section-title");
                content = lbl;
            }
            mainContent.getChildren().setAll(content);
            FadeTransition fi = new FadeTransition(Duration.millis(150), mainContent);
            fi.setFromValue(0); fi.setToValue(1);
            fi.play();
        });
        fo.play();
    }

    private Node loadFXML(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) throw new RuntimeException("Не знайдено " + path);
            return FXMLLoader.load(url);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
