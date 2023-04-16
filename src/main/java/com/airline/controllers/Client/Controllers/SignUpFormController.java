package com.airline.controllers.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController extends ClientViewController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private Hyperlink backToLogin;
    @FXML
    private Label confirmMsg;

    @FXML
    public void submitForm() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (clientController.signUp(firstName, lastName, email, password) != null) {
            display("Your account has been created successfully!", Color.GREEN);
            clearAll();
        } else {
            display("Invalid input. please, Try again!", Color.RED);
        }
    }

    private void clearAll() {
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);

    }

    private void display(String msg, Color color) {
        confirmMsg.setText(msg);
        confirmMsg.setTextFill(color);
        confirmMsg.setVisible(true);
    }

    @FXML
    public void openSignInView() throws IOException {
        clearAll();
        Parent root = FXMLLoader.load(getClass().getResource(getViewPath() + "ClientViews/ClientLoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backToLogin.getScene().getWindow();
        stage.setScene(scene);
    }
}
