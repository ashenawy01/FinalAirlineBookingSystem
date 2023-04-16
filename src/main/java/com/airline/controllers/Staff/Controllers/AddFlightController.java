package com.airline.controllers.Staff.Controllers;

import com.airline.controllers.FlightController;
import com.airline.entities.Airline;
import com.airline.entities.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddFlightController extends StaffViewController implements Initializable {

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


    public void submitForm(ActionEvent actionEvent) {

        if (!isTimeValid(timeField.getText())) {
            display("Error - Please Enter a valid time (ex: 11:30)", Color.RED);
            return;
        }

        LocalDateTime flightTime = dateField.getValue().
                atTime(LocalTime.parse(timeField.getText()));

        Flight flight =
                FlightController.addFlight(
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
            display("Flight is added successfully!", Color.GREEN);
        }
        cleanAll();
    }

    private void cleanAll() {
        originField.setText(null);
        destField.setText(null);
        dateField.setValue(null);
        timeField.setText(null);
        durationField.setText(null);
        priceField.setText(null);
        airlineField.setValue(null);
        seatsNoField.setText(null);
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

