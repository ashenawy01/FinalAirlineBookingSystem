package com.airline.controllers.Client.Controllers;

import com.airline.entities.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileClientController extends ClientViewController implements Initializable {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label confirmMsg;

    Client client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = clientController.getCurrentClient();
        firstNameField.setText(client.getFirstName());
        lastNameField.setText(client.getLastName());
        emailField.setText(client.getEmail());
        passwordField.setText(client.getPassword());
        confirmMsg.setVisible(false);
    }

    public void updateHandler() {
        if (clientController.updateMyInfo(
                firstNameField.getText(),
                lastNameField.getText(),
                passwordField.getText()
        )) {
            confirmMsg.setText("You Info has been updated successfully");
            confirmMsg.setTextFill(Color.GREEN);
            confirmMsg.setVisible(true);
        } else {
            confirmMsg.setText("Invalid input data! please, try again");
            confirmMsg.setTextFill(Color.RED);
            confirmMsg.setVisible(true);
        }
    }
}
