package com.airline.controllers.Staff.Controllers;

import com.airline.entities.Employee;
import com.airline.entities.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StaffHomeController extends StaffViewController implements Initializable {

    @FXML
    private TableView flightTable;
    @FXML
    private TableColumn flightIdColumn;
    @FXML
    private TableColumn originColumn;
    @FXML
    private TableColumn destColumn;
    @FXML
    private TableColumn airlineColumn;
    @FXML
    private TableColumn flightTimeColumn;
    @FXML
    private TableColumn durationColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn seatNoColumn;
    @FXML
    private Label addFlight;
    @FXML
    private TextField originField;
    @FXML
    private DatePicker dateField;

    private static ObservableList<Flight> flightsData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the observableList and add all flights
        flightsData = FXCollections.observableArrayList(staffController.getAllFlights());

        // set the cellValueFactory of each TableColumn to the corresponding field name of the Flight class
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        seatNoColumn.setCellValueFactory(new PropertyValueFactory<>("seatListSize"));

        flightTable.setItems(flightsData);
    }

    @FXML
    public void addFlightHandler(MouseEvent event) throws IOException {
        if (event.getSource() instanceof Label) {
            Label addFlightLabel = (Label) event.getSource();
            if (addFlightLabel.getId().equals("addFlight")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getStaffPath() + "add-flight.fxml"));
                Parent newFlight = loader.load();
                Scene scene = addFlightLabel.getScene();
                scene.setRoot(newFlight);
            }
        }
    }

    public void filterHandler(){

        ArrayList<Flight> flights =
                flightController.findFlightFrom(originField.getText(), dateField.getValue());
        if (flights != null) {
            flightsData.clear();
            flightsData.addAll(flights);
        }
    }

}
