package com.airline.controllers.Admin.Controllers;

import com.airline.entities.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginFormController extends AdminViewController{

    private final String viewPath = "/com/airlineview/views/";
    @FXML
    private Hyperlink backLink;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMsg;

    @FXML
    public void back() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(viewPath + "index.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backLink.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void login(MouseEvent event) throws IOException {

        String email = emailField.getText();
        String pass = passwordField.getText();
        Admin admin = adminController.signIn(email, pass);

        if (admin != null) {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath + "AdminViews/AdminHome.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) backLink.getScene().getWindow();
            stage.setScene(scene);
        } else {
            errorMsg.setVisible(true);
        }

    }


}
