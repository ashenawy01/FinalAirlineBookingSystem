package com.airline.controllers.Staff.Controllers;

import com.airline.controllers.FlightController;
import com.airline.controllers.StaffController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffViewController {

    protected final StaffController staffController = new StaffController();
    protected final FlightController flightController = new FlightController();


    private final String viewPath = "/com/airlineview/views/";
    private final String staffPath = viewPath + "StaffViews/";
    @FXML
    private Hyperlink signOut;
    @FXML
    private Hyperlink myProfile;
    @FXML
    private Hyperlink manageFlight;
    @FXML
    private Hyperlink bookings;
    @FXML
    private Hyperlink flights;

    public String getStaffPath() {
        return staffPath;
    }

    public String getViewPath() {
        return viewPath;
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Hyperlink link = (Hyperlink) event.getSource();

        switch (link.getId()) {
            case "bookings":
                loadContent("booking.fxml");
                break;
            case "manageFlight":
                loadContent("manage-flight.fxml");
                break;
            case "flights":
                loadContent("staff-home.fxml");
                break;
            case "myProfile":
                loadContent("my-profile-s.fxml");
                break;
            case "signOut":
                loadContent("StaffLoginForm.fxml");
                break;
            default:
                System.out.println("Error 404 - Unknown Link");
                break;
        }
    }


    private void loadContent(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(staffPath + page));
        Scene scene = new Scene(root);
        Stage stage = (Stage) flights.getScene().getWindow();
        stage.setScene(scene);

    }

}
