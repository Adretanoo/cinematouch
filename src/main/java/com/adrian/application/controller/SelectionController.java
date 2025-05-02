package com.adrian.application.controller;


import javafx.fxml.FXML;

public class SelectionController {

    @FXML
    private void handleTicketsClick() {
        System.out.println("Клік: Квитки");
        // Тут буде перехід на екран з вибором квитків
    }

    @FXML
    private void handleMenuClick() {
        System.out.println("Клік: Меню");
        // Тут буде перехід на екран з їжею/напоями
    }
}
