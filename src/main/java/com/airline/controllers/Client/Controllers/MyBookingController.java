package com.airline.controllers.Client.Controllers;

import com.airline.entities.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MyBookingController extends ClientViewController implements Initializable {

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
    private TextField bookingIdField;
    @FXML
    private DatePicker dateFilterField;
    @FXML
    private Label addedBookingsLabel;

    private static int counter = 0;


    private static ObservableList<Booking> bookingObservableList;
    private static Set<Booking> selectedBookings = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add a listener to the flightTable to detect when a row is clicked
        bookingTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Booking selectedBooking = (Booking) bookingTable.getSelectionModel().getSelectedItem();
                if (selectedBooking != null) {
                    Booking booking = (Booking) bookingController.findBookingById(selectedBooking.getBookingID());
                    if (booking == null) {
                        System.out.println("Null");
                    } else {
                        if (selectedBookings.contains(booking)) {
                            selectedBookings.remove(booking);
                            counter--;
                        } else {
                            selectedBookings.add(booking);
                            counter++;
                        }
                        addedBookingsLabel.setText("Your Added Flight : " + counter);
                    }
                }
            }
        });

        bookingObservableList = FXCollections.observableArrayList(bookingController.listMyBookings());

        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        flightsNoCol.setCellValueFactory(new PropertyValueFactory<>("flightListSize"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        travelersNoCol.setCellValueFactory(new PropertyValueFactory<>("travelers"));
        totalFareCol.setCellValueFactory(new PropertyValueFactory<>("totalFare"));

        bookingTable.setItems(bookingObservableList);
        clearAll();
    }


    @FXML
    public void cancelBookingHandle(ActionEvent actionEvent) throws IOException {
        // Load the FXML file for the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getClientPath() + "cancel-confirm.fxml"));
        CancelConfirmController cancelConfirmController =
                new CancelConfirmController(selectedBookings);
        loader.setController(cancelConfirmController);
        Parent root = loader.load();

        // Create a new stage for the new window
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Set the priority of the new window
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Show the new window and wait for it to be closed before continuing
        clearAll();
    }

    public void filterHandler () {
        String idStr = bookingIdField.getText();
        bookingObservableList.clear();

        ArrayList<Booking> bookings = bookingController.listMyBookings();
        if (!idStr.isEmpty()) {
            if (!idStr.matches("^[1-9][0-9]*$")) {
                return;
            }
            for (Booking booking : bookings) {
                if (booking.getBookingID() == Integer.parseInt(bookingIdField.getText())) {
                    bookingObservableList.add(booking);
                    break;
                }
            }
        } else if (dateFilterField.getValue() != null) {
            for (Booking booking : bookings) {
                if (booking.getDate().getDayOfYear() == dateFilterField.getValue().getDayOfYear()) {
                    bookingObservableList.add(booking);
                }
            }
        } else {
            System.out.println("No filter to apply!");
        }
    }
    private void clearAll () {
        selectedBookings.clear();
        bookingObservableList.clear();
        counter = 0;
        bookingObservableList.addAll(bookingController.listMyBookings());
        bookingIdField.setText(null);
        dateFilterField.setValue(null);
    }
}
