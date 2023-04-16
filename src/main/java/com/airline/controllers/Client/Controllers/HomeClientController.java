package com.airline.controllers.Client.Controllers;

import com.airline.entities.Booking;
import com.airline.entities.ClassType;
import com.airline.entities.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeClientController extends ClientViewController implements Initializable {
    @FXML
    private Button bookBtn;
    @FXML
    private TextField fromField;
    @FXML
    private TextField toField;
    @FXML
    private DatePicker datePicker;
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
    private Label addedFlightsLabel;
    @FXML
    private TextField travelersNoField;
    @FXML
    private ComboBox<ClassType> classTypeComboBox;

    private static ObservableList<Flight> flightObservableList;
    private static ArrayList<Flight> flights = new ArrayList<>();
    private static int counter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        classTypeComboBox.getItems().addAll(ClassType.values());
        travelersNoField.setText("1");
        classTypeComboBox.setValue(ClassType.Economy);

        flightObservableList = FXCollections.observableArrayList();

        // Add a listener to the flightTable to detect when a row is clicked
        flightTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Flight selectedFlight = (Flight) flightTable.getSelectionModel().getSelectedItem();
                if (selectedFlight != null) {
                    Flight flight = (Flight) flightController.findFlightByID(selectedFlight.getFlightID());
                    if (flights.contains(flight)) {
                        flights.remove(flight);
                        counter--;
                    } else {
                        flights.add(flight);
                        counter++;
                    }
                    addedFlightsLabel.setText("Your Added Flight : " + counter);
                }
            }
        });

        // set the cellValueFactory of each TableColumn to the corresponding field name of the Flight class
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        seatNoColumn.setCellValueFactory(new PropertyValueFactory<>("seatListSize"));

        flightTable.setItems(flightObservableList);

    }

    public void searchHandler() {
        if (datePicker.getValue() == null) {
            System.out.println("No date no search!");
            return;
        }
        if (!fromField.getText().isEmpty()) {
            flightObservableList.clear();
            if (toField.getText().isEmpty()) {
                flightObservableList.addAll(flightController.findFlightFrom(fromField.getText(), datePicker.getValue()));
            } else {
                flightObservableList.addAll(
                        flightController.findFlightFromTo(fromField.getText(), toField.getText(), datePicker.getValue()));

            }
        } else {
            System.out.println("No filter to apply");
        }
    }

    @FXML
    public void handleBookButton() throws Exception {
        if (!travelersNoField.getText().matches("^[1-9][0-9]*$")) {
            return;
        }
        // Load the FXML file for the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getClientPath() + "booking-confirm.fxml"));
        BookingConfirm controller = new BookingConfirm(Integer.parseInt(travelersNoField.getText()), classTypeComboBox.getValue() ,flights);
        loader.setController(controller);
        Parent root = loader.load();
        // Create a new stage for the new window
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Set the priority of the new window
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show the new window and wait for it to be closed before continuing
        clearAll();
    }

    private void clearAll() {
        flights.clear();
        travelersNoField.setText("1");
        classTypeComboBox.setValue(ClassType.Economy);
        counter = 0;
        fromField.setText(null);
        toField.setText(null);
        datePicker.setValue(null);
    }


}
