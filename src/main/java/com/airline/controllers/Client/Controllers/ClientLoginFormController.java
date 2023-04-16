package com.airline.controllers.Client.Controllers;

import com.airline.entities.Client;
import com.airline.entities.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientLoginFormController extends ClientViewController {

    private final String viewPath = "/com/airlineview/views/";

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink createAccountLink;
    @FXML
    private Label errorMsg;


    @FXML
    private Hyperlink backLink;

    @FXML
    private Button signInBtn;

    @FXML
    public void openHelloView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(viewPath + "ClientViews/SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) createAccountLink.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void back() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(viewPath + "index.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backLink.getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    public void login() throws IOException {

        String email = emailField.getText();
        String pass = passwordField.getText();
        Client client = clientController.signIn(email, pass);

        if (client == null) {
            errorMsg.setVisible(true);
            return;
        }
        bookingController.setCurrentClient(client);
        Parent root = FXMLLoader.load(getClass().getResource(viewPath + "ClientViews/client-home.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) signInBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
