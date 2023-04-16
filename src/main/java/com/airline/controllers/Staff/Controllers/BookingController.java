package com.airline.controllers.Staff.Controllers;

import com.airline.controllers.FlightController;
import com.airline.entities.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController extends StaffViewController implements Initializable {

    @FXML
    private TableView bookingTable;
    @FXML
    private TableColumn bookingIdCol;
    @FXML
    private TableColumn clientIdCol;
    @FXML
    private TableColumn flightsNoCol;
    @FXML
    private TableColumn dateCol;
    @FXML
    private TableColumn travelersNoCol;
    @FXML
    private TableColumn totalFareCol;
    @FXML
    private TextField clientIdField;
    @FXML
    private DatePicker dateFilterField;


    private ObservableList<Booking> bookingObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookingObservableList = FXCollections.observableArrayList(staffController.getAllBookings());

        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clintID"));
        flightsNoCol.setCellValueFactory(new PropertyValueFactory<>("flightListSize"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        travelersNoCol.setCellValueFactory(new PropertyValueFactory<>("travelers"));
        totalFareCol.setCellValueFactory(new PropertyValueFactory<>("totalFare"));

        bookingTable.setItems(bookingObservableList);
    }

    public void filterHandler (){
         if (clientIdField.getText().isEmpty() && !(dateFilterField.getValue() == null)) {
            System.out.println("date filter");
            bookingObservableList.clear();
            bookingObservableList.addAll(
                    staffController.getBookingsOn(dateFilterField.getValue())
            );
        } else if (!clientIdField.getText().isEmpty()) {
             System.out.println("Find id " + clientIdField.getText());
            if (!clientIdField.getText().matches("^[1-9][0-9]*$")) {
                System.out.println("Find id " + clientIdField.getText() + "Is not a number");
                return;
            }
            bookingObservableList.clear();
            if (dateFilterField.getValue() == null) {
                bookingObservableList.addAll(
                        staffController.getBookingsOf(Integer.parseInt(clientIdField.getText()))
                );
            } else {
                bookingObservableList.addAll(
                        staffController.getBookingsFilter(Integer.parseInt(clientIdField.getText()), dateFilterField.getValue())
                );
            }
        } else {
             System.out.println("No filter to apply Filter");
        }
    }
}
