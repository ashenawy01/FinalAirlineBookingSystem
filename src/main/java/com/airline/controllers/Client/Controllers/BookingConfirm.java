package com.airline.controllers.Client.Controllers;

import com.airline.entities.Booking;
import com.airline.entities.ClassType;
import com.airline.entities.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingConfirm extends ClientViewController implements Initializable {

    private ArrayList<Flight> flights;
    private int travelersNo;
    private ClassType classType;
    @FXML
    private Button confirmBtn;
    @FXML
    private TextArea bookingDetailsField;
    StringBuilder stringBuilder = new StringBuilder();


    public BookingConfirm(int travelers, ClassType classType, ArrayList<Flight> flights) {
        this.travelersNo = travelers;
        this.classType = classType;
        this.flights = flights;
    }


    public StringBuilder generateBookingReport(){

        stringBuilder.append("Confirmation report\n");
        stringBuilder.append("Travelers number : "+ travelersNo + "\n");
        stringBuilder.append("Class type : " + classType.toString() + "\n");
        stringBuilder.append("\n===============\n");
        flights.forEach(myFlight -> {
            stringBuilder.append( "Flight #No. "+myFlight.getFlightID() + "\n" +
                    "From { " + myFlight.getOrigin() + " }"+
                    " To { " + myFlight.getDestination() + " }"+
                    " on { " + myFlight.getFlightTime() + " } " +
                    " in " + myFlight.getDuration() + " h\n" +
                    "Seats : " + myFlight.getSeats() + "\n" +
                    "===================\n");
        });
        return stringBuilder;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookingDetailsField.setText(String.valueOf(generateBookingReport()));
        confirmBtn.setOnAction(event -> {
            bookingController.createBooking(travelersNo, classType, flights);
            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            stage.close();
        });
    }


}
