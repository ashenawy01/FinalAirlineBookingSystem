package com.airline.controllers.Admin.Controllers;

import com.airline.entities.Admin;
import com.airline.entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileController extends AdminViewController implements Initializable {

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

    private Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin = adminController.getCurrentAdmin();
        firstNameField.setText(admin.getFirstName());
        lastNameField.setText(admin.getLastName());
        emailField.setText(admin.getEmail());
        passwordField.setText(admin.getPassword());
        confirmMsg.setVisible(false);
    }

    public void updateHandler() {
        if (adminController.updateMyInfo(
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
