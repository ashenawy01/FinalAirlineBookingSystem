package com.airline.controllers.Staff.Controllers;

import com.airline.controllers.StaffController;
import com.airline.entities.Admin;
import com.airline.entities.Staff;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffLoginFormController extends StaffViewController {

    @FXML
    private Hyperlink backLink;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMsg;

    @FXML
    private Button loginButton;


    @FXML
    public void back() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource( getViewPath() + "index.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backLink.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void login(MouseEvent event) throws IOException {

        String email = emailField.getText();
        String pass = passwordField.getText();
        Staff staff = staffController.signIn(email, pass);

        if (staff != null) {
            flightController.setCurrentStaff(staff);
            Parent root = FXMLLoader.load(getClass().getResource(getStaffPath() + "staff-home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) backLink.getScene().getWindow();
            stage.setScene(scene);
        } else {
            errorMsg.setVisible(true);
        }
    }
}
