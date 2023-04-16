package com.airline.Testing;


import com.airline.controllers.FlightController;
import com.airline.controllers.StaffController;
import com.airline.entities.Airline;
import com.airline.entities.Flight;
import com.airline.entities.Staff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StaffViewTest {

    static StaffController staffController = new StaffController();
    static FlightController flightController = new FlightController();

    public static void main(String[] args) {



        //        #################  Staff controller testing  #################

        // Staff user that will log in
        Staff staff;

        // sign in - initialize the user
        System.out.println("Sign in with the staff account:Noura ");
        staff = staffController.signIn("noura123@bue.edu.eg", "test123");

        // update password
        System.out.println("Updating the password...");
        staffController.updatePassword("test123", "test321");
        System.out.println("Updating the password...");
        staffController.updatePassword("test321", "test123");

        // generate a report of all existed flights
        System.out.println("\n#########  Flights report #########\n");
        System.out.println(staffController.generateFlightReport());

        // generate a report of all existed Bookings
        System.out.println("\n\n#########  Bookings report #########\n");
        System.out.println(staffController.generateBookingReport());
        System.out.println("\n\n");


//        #################  Flight controller testing  #################

        //initializing the current login staff user
        flightController.setCurrentStaff(staff);

        // Get all flights from Cairo on 1/1/2024
        System.out.println("Display all flights from Cairo on 1/1/2024");
        ArrayList<Flight> flightsFrom =
                flightController.findFlightFrom("Cairo", LocalDate.of(2024, 1, 1));
        flightsFrom.forEach(flight -> {
            System.out.println(flight);
            System.out.println("===============================================================\n");
        });


        // updating the time of the flight number 5 {from Dubai to El-sharka} from 5:00 to 5:30
        // note that the old time (before updating) will be run with the old time only once {done already}
        System.out.println("Before Updating....");
        System.out.println(flightController.findFlightByID(5));
        flightController.updateFlightTime(5, LocalDateTime.of(2024, 1, 1, 5, 0));
        System.out.println("After updating ....");
        System.out.println(flightController.findFlightByID(5));


        // Create a new flight from cairo to Roma 2/1/2024
        System.out.println("\n\nCreating a new flight ......");

        flightController.addFlight("Cairo", "Roma", LocalDateTime.of(2024, 1, 1, 5, 30),
                5.5f, 1000, Airline.American_Airlines,23);
        System.out.println("Display the created flight: ");
        System.out.println(flightController.findFlightByID(6));
        System.out.println("\n\n");


        // Delete the last added flight {id = 7} from Cairo to Roma
        System.out.println("Display Flight #NO. 7");
        System.out.println(flightController.findFlightByID(7));
        System.out.println("Deleting the flight ....");
        flightController.deleteFlight(7);
        System.out.println("Trying to display Flight #NO. 7 After deleting");
        System.out.println(flightController.findFlightByID(7));
        System.out.println("\n\n");


        // StaffView managed flights
        // these flights has been created or managed by the current login staff {Noura}
        System.out.println("=================== My Flights  ========================\n");
        System.out.println(staffController.displayMyAddedFlights());

    }
}
