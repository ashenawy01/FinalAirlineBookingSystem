package com.airline.controllers.Admin.Controllers;

import com.airline.entities.Department;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateStaffController extends AdminViewController implements Initializable {

    @FXML
    private ComboBox<Department> deptBox;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private Label confirmMsg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deptBox.getItems().setAll(Department.values());
    }

    public void submitForm(ActionEvent actionEvent) {
        Department dept = deptBox.getValue();
        if (adminController.createStaff(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                jobTitleField.getText(),
                dept
        ) != null){
            clearAll();
            confirmMsg.setText("Staff User has been added successfully");
            confirmMsg.setTextFill(Color.GREEN);
            confirmMsg.setVisible(true);
        } else {
            clearAll();
            confirmMsg.setText("Sorry! Failed to add the user. Please, try again");
            confirmMsg.setTextFill(Color.RED);
            confirmMsg.setVisible(true);
        }
    }
    private void clearAll () {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        jobTitleField.clear();
        deptBox.setValue(null);
        confirmMsg.setVisible(false);
    }
}
