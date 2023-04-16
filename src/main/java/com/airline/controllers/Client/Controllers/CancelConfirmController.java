package com.airline.controllers.Client.Controllers;

import com.airline.entities.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class CancelConfirmController extends ClientViewController implements Initializable {


    @FXML
    private TableView bookingTable;
    @FXML
    private TableColumn bookingIdCol;
    @FXML
    private TableColumn flightsNoCol;
    @FXML
    private TableColumn dateCol;
    @FXML
    private TableColumn travelersNoCol;
    @FXML
    private TableColumn totalFareCol;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button keepBtn;


    private ArrayList<Booking> bookings;

     private ObservableList<Booking> bookingsObservableList;

    public CancelConfirmController(Set<Booking> bookingsSet) {
        this.bookings = new ArrayList<>(bookingsSet);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bookingsObservableList = FXCollections.observableArrayList(bookings);

        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        flightsNoCol.setCellValueFactory(new PropertyValueFactory<>("flightListSize"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        travelersNoCol.setCellValueFactory(new PropertyValueFactory<>("travelers"));
        totalFareCol.setCellValueFactory(new PropertyValueFactory<>("totalFare"));

        bookingTable.setItems(bookingsObservableList);

        cancelBtn.setOnMouseClicked(event -> {
            bookings.forEach(booking -> {
                bookingController.deleteBooking(booking.getBookingID());
            });
            bookingsObservableList.clear();
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
        keepBtn.setOnMouseClicked(event -> {
            bookingsObservableList.clear();
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });

    }


}
