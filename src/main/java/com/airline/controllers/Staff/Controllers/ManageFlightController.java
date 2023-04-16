package com.airline.controllers.Staff.Controllers;

import com.airline.controllers.FlightController;
import com.airline.entities.Airline;
import com.airline.entities.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageFlightController extends StaffViewController implements Initializable {

    @FXML
    private TextField originField;
    @FXML
    private TextField destField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField durationField;
    @FXML
    private ComboBox<Airline> airlineField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField seatsNoField;
    @FXML
    private Label confirmMsg;
    @FXML
    private TextField flightIdField;
    @FXML
    private Text flightIdLabel;

    private static Flight flight;

    public void searchHandler() {
        if (flightIdField.getText().matches("^[1-9][0-9]*$")) {
            flight = flightController.findFlightByID(Integer.parseInt(flightIdField.getText()));
            if (flight == null) {
                display("Flight is not existed!", Color.RED);
            } else {
                displayFlight();
            }
        }
        else
            display("Invalid ID, Please, enter positive numbers only!", Color.RED);
    }

    private void displayFlight() {
        originField.setText(flight.getOrigin());
        destField.setText(flight.getDestination());
        dateField.setValue(flight.getFlightTime().toLocalDate());
        timeField.setText(flight.getFlightTime().toLocalTime().toString());
        durationField.setText("" + flight.getDuration());
        priceField.setText("" + flight.getTicketPrice());
        airlineField.setValue(flight.getAirline());
        seatsNoField.setText("" + flight.getSeatListSize());
        flightIdLabel.setText("Flight No. " + flight.getFlightID());
    }

    public void updateHandler() {

        if (flight == null) {
            display("No flight found to update", Color.RED);
            return;
        }

        if (!isTimeValid(timeField.getText())) {
            display("Error - Please Enter a valid time (ex: 11:30)", Color.RED);
            return;
        }

        LocalDateTime flightTime = dateField.getValue().
                atTime(LocalTime.parse(timeField.getText()));

        Flight newFlight =
                FlightController.updateFlight(
                        flight.getFlightID(),
                        originField.getText(),
                        destField.getText(),
                        flightTime,
                        Float.parseFloat(durationField.getText()),
                        Double.parseDouble(priceField.getText()),
                        airlineField.getValue(),
                        Integer.parseInt(seatsNoField.getText())
                );
        if (flight == null) {
            display("Invalid input data, please, try again!", Color.RED);
        } else {
            display("Flight is updated successfully!", Color.GREEN);
            flight = newFlight;
            displayFlight();
        }
    }
    public void deleteHandler() {
        if (flight == null) {
            display("No flight found to delete", Color.RED);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete flight No. : " + flight.getFlightID());
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (flightController.deleteFlight(flight.getFlightID())) {
                display("Flight has been deletd successfully!", Color.GREEN);
                clearAll();
            }
        }
    }

    private void clearAll() {
        originField.setText(null);
        destField.setText(null);
        dateField.setValue(null);
        timeField.setText(null);
        durationField.setText(null);
        priceField.setText(null);
        airlineField.setValue(null);
        seatsNoField.setText(null);
        flightIdLabel.setText(null);
    }
    private boolean isTimeValid(String timeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(timeStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private void display(String msg, Color color) {
        confirmMsg.setText(msg);
        confirmMsg.setTextFill(color);
        confirmMsg.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        airlineField.getItems().addAll(Airline.values());
    }
}
