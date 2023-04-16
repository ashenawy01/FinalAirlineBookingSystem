package com.airline.controllers.Staff.Controllers;

import com.airline.entities.Admin;
import com.airline.entities.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileStaffController extends StaffViewController implements Initializable {



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

    private Staff staff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staff = staffController.getCurrentStaff();
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        emailField.setText(staff.getEmail());
        passwordField.setText(staff.getPassword());
        confirmMsg.setVisible(false);
    }

    public void updateHandler() {
        if (staffController.updateMyInfo(
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
