package com.airline.controllers.Client.Controllers;

import com.airline.controllers.BookingController;
import com.airline.controllers.ClientController;
import com.airline.controllers.FlightController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientViewController {
    protected final BookingController bookingController = new BookingController();
    protected final FlightController flightController = new FlightController();
    protected final ClientController clientController = new ClientController();
    private final String viewPath = "/com/airlineview/views/";
    private final String clientPath = viewPath + "ClientViews/";
    @FXML
    private Hyperlink signOut;
    @FXML
    private Hyperlink myProfile;
    @FXML
    private Hyperlink myBookings;
    @FXML
    private Hyperlink home;

    public String getClientPath() {
        return clientPath;
    }

    public String getViewPath() {
        return viewPath;
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Hyperlink link = (Hyperlink) event.getSource();

        switch (link.getId()) {
            case "myBookings":
                loadContent("my-bookings.fxml");
                break;
            case "home":
                loadContent("client-home.fxml");
                break;
            case "myProfile":
                loadContent("my-profile-c.fxml");
                break;
            case "signOut":
                loadContent("ClientLoginForm.fxml");
                break;
            default:
                System.out.println("Error 404 - Unknown Link");
                break;
        }
    }


    private void loadContent(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(clientPath + page));
        Scene scene = new Scene(root);
        Stage stage = (Stage) home.getScene().getWindow();
        stage.setScene(scene);

    }

}
