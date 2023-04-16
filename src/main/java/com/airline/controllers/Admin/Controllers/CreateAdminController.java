package com.airline.controllers.Admin.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class CreateAdminController extends AdminViewController{

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox isGlobalCheck;
    @FXML
    private Label confirmMessage;
    @FXML
    private Label errorMsg;


    public void submitForm(ActionEvent actionEvent) {
        if (adminController.createAdmin(firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                isGlobalCheck.isSelected(),
                true) != null)
        {
            display("Staff user has been added successfully", Color.GREEN);

            clearAll();
        } else {
            display("Invalid input! please, try again", Color.RED);
            clearAll();
        }
    }

    private void display(String msg, Color color) {
        errorMsg.setText(msg);
        errorMsg.setTextFill(color);
        confirmMessage.setVisible(true);
    }

    private void clearAll () {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        isGlobalCheck.setSelected(false);
        confirmMessage.setVisible(false);
    }
}
