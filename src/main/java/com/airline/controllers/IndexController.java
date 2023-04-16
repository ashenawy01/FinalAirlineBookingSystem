package com.airline.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class IndexController {

    private final String viewPath = "/com/airlineview/views/";

    @FXML
    private Button Client;

    @FXML
    private Button admin;

    @FXML
    private Button staff;


    @FXML
    private void handleButton(MouseEvent event) throws IOException {
        if (event.getSource() == Client) {
            Parent root = FXMLLoader.load(getClass().getResource( viewPath + "ClientViews/ClientLoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) Client.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == admin) {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "AdminViews/AdminLoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) admin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "StaffViews/StaffLoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) admin.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}




