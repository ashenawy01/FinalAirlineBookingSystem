package com.airline.Testing;

import com.airline.controllers.BookingController;
import com.airline.controllers.ClientController;
import com.airline.controllers.FlightController;
import com.airline.entities.ClassType;
import com.airline.entities.Client;
import com.airline.entities.Flight;

import java.util.ArrayList;

public class ControllerTesting {
    static BookingController bookingController = new BookingController();
    static ClientController clientController = new ClientController();
    static FlightController flightController = new FlightController();

    public static void main(String[] args) {



//        #################  Client controller testing  #################

        // Client object of the current client
        Client client;

        // Sign up with an existed account
        // ### Note: If the email changes, a new account will be created ###
        System.out.println("Trying to create an a existed client account");
        clientController.signUp("Nour", "El-said", "nour123@gmail.com", "test123");

        // sigh in with the created account
        client = clientController.signIn("nour123@gmail.com", "test123");

        // display the current client info
        System.out.println(client);




//        #################  Booking controller testing  #################

        // initialize the current accessed client {Nour}
        bookingController.setCurrentClient(client);

        // display the current client
        System.out.print("Welcome back: ");
        System.out.println(bookingController.getCurrentClient());

        // Create Booking from Cairo to Roma for 1 traveler and 2 flights
        System.out.println("Crate Booking includes one flight fore one person");

        //create a list of included flights
        ArrayList<Flight> bookFlights = new ArrayList<>();
        bookFlights.add(flightController.findFlightByID(4));


        // crete the booking
        if (bookingController.createBooking(1, ClassType.Economy, bookFlights)) {
            System.out.println("Booking is added successfully!");
        } else {
            System.out.println("Booking is failed. please, try again");
        }



        // display all bookings details
        System.out.println("\n *************  My Bookings  *************  ");
        System.out.println(bookingController.displayClientBookings());

        // Delete booking
        System.out.println("Deleting the duplicated booking {ID = 3}....");
        bookingController.deleteBooking(3);



        // display all bookings details After deleting Booking with id 3
        System.out.println("\n *************  My Bookings  *************  ");
        System.out.println(bookingController.displayClientBookings());





    }
}
