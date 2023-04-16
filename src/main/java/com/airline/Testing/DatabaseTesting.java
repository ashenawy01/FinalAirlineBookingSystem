package com.airline.Testing;

import com.airline.dao.*;
import com.airline.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
    This class for inserting initial data to the database

              ((((( For com.airline.Testing Only ))))))
*/
public class DatabaseTesting {


    // Create a list of seats according to their count
    public static ArrayList<Seat> createSeats(int seatsNum) {
        // Create a new linked list to store the seats
        ArrayList<Seat> seats = new ArrayList<>();

        // If there is only one seat, create it and add it to the list with economy class type
        if (seatsNum == 1)
            seats.add(new Seat("A1", ClassType.Economy));
            // If there are two seats, create them and add them to the list with economy and first class types
        else if (seatsNum == 2) {
            seats.add(new Seat("A1", ClassType.Economy));
            seats.add(new Seat("A2", ClassType.FirstClass));
        }
        // If there are more than two seats, calculate the number of seats per class and create the seats accordingly
        else {
            int seatsPerClass = seatsNum / 3; // Calculate the number of seats per class (assuming three classes)
            int seatsRem = seatsNum % 3; // Calculate the remaining seats that don't fit evenly into three classes
            String seatNumber = "";
            for (int i = 0; i < seatsNum; i++) {
                // First, adding the Economy class seats, create an economy class seat in the "A" row
                if (i < seatsPerClass) {
                    seatNumber = "A" + (i+1); // Calculate the seat number based on the current index
                    seats.add(new Seat(seatNumber, ClassType.Economy)); // Create the seat and add it to the list
                }
                // Second, adding the Business class seats, create a business class seat in the "B" row
                else if (i < seatsPerClass*2) {
                    seatNumber = "B" + ((i+1) - seatsPerClass); // Calculate the seat number based on the current index
                    seats.add(new Seat(seatNumber, ClassType.Business)); // Create the seat and add it to the list
                }
                // Lastly, adding the Business class seats, create a business class seat in the "C" row
                else {
                    seatNumber = "C" + ((i+1) - seatsPerClass*2); // Calculate the seat number based on the current index
                    seats.add(new Seat(seatNumber, ClassType.FirstClass)); // Create the seat and add it to the list
                }
            }
            // If there are any remaining seats, add them to the "A" row as economy class seats
            for (int i = 0; i < seatsRem; i++) {
                seatNumber = "A" + ((i+1) + seatsPerClass); // Calculate the seat number based on the current index and the number of seats per class
                seats.add(new Seat(seatNumber, ClassType.Economy)); // Create the seat and add it to the list
            }
        }
        // Return the list of seats
        return seats;
    }


    public static void main(String[] args) {


        // ********** Admin Database - data entry {Initial data} **********
        // Create Admin users
        ArrayList<Admin> admins = new ArrayList<>();
        admins.add(new Admin("Abdelrhman", "Elshenawy", "abdelrhman225328@bue.edu.eg", "test123", true, true));
        admins.add(new Admin("Abdullah", "Elshal", "abdullah224005@bue.edu.eg", "test123", false, true));
        admins.add(new Admin("Test", "Test", "test@test.com", "test123", true, true));


        // Access the database file
        AdminDB adminDB = new AdminDB();
        adminDB.resetDatabase(); // reset the database {Delete all}

        // insert the admin users
        admins.forEach(admin -> {
            adminDB.addObject(admin, true);
        });
        System.out.println(adminDB.retrieveAll()); // print all admins





        // ********** Staff Database - data entry {Initial data} **********

        // Create Staff users
        ArrayList<Staff> staffUsers = new ArrayList<>();
        staffUsers.add(new Staff("Moutasem", "Mohamed", "moutasem219140@bue.edu.eg", "test123", "agent", Department.CustomerService));
        staffUsers.add(new Staff("Noureldine", "Lashine", "noureldine213527@bue.edu.eg", "test123", "data entry", Department.Operations));

        // Access the database file
        StaffDB staffDB = new StaffDB();
        staffDB.resetDatabase(); // reset the database {Delete all}

        // insert all staff users to the database
        staffUsers.forEach(staff -> {
            staffDB.addObject(staff, true);
        });
        System.out.println(staffDB.retrieveAll()); // print all staff






        // ********** Client Database - data entry {Initial data} **********

        // Create arrayList of all clients
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(new Client("Moatsem", "Mohamed", "moutasem219140@bue.edu.eg", "test123"));

        // Create Client com.airline.DAO to access the database file
        ClientDB clientDB = new ClientDB();
        // Reset the database (Delete all existed objects)
        clientDB.resetDatabase();

        // insert the client objects form the arrayList to the database
        clients.forEach(client -> {
            clientDB.addObject(client, true);
        });
        // Display all Clients
        System.out.println(clientDB.retrieveAll());







        // ********** Flight Database - data entry {Initial Flights} **********

        // Create a list of flights
        ArrayList<Flight> flights = new ArrayList<>();

        //Adding the flight objects to the arrayList
        flights.add(new Flight("Cairo", "Istanbul",
                LocalDateTime.of(2024, 1, 1, 1, 0),
                3.5f, 2552,  Airline.Egypt_Air,  createSeats(30)));
        flights.add(new Flight("Istanbul", "Ankara",
                LocalDateTime.of(2024, 1, 1, 5, 0),
                1.5f, 2552,  Airline.Qatar_Airways,  createSeats(40)));

        flights.add(new Flight("Ankara", "Antalya",
                LocalDateTime.of(2024, 1, 1, 9, 0),
                2f, 2552,  Airline.Qatar_Airways,  createSeats(20)));

        flights.add(new Flight("Cairo", "Dubai",
                LocalDateTime.of(2024, 1, 1, 1, 20),
                2.5f, 2552,  Airline.Emirates,  createSeats(30)));

        flights.add(new Flight("Dubai", "El-Sharka",
                LocalDateTime.of(2024, 1, 1, 5, 0),
                1.5f, 2552,  Airline.Emirates,  createSeats(30)));

        flights.get(0).bookSeat("A1", 1);
        flights.get(1).bookSeat("A1", 1);

        // Create the com.airline.DAO to access the flight database file
        FlightDB flightDB = new FlightDB();
        flightDB.resetDatabase(); // Reset the file (delete all flights)

        // add the flights from the arraylist to the database
        flights.forEach(flight -> {
            flightDB.addObject(flight, true);
        });

        // display all flights
        System.out.println(flightDB.retrieveAll());




        // ********** Booking Database - data entry {Initial bookings} **********

        // create a list of bookings
        ArrayList<Booking> bookings = new ArrayList<>();

        // list of booked flights
        Map<Flight, ArrayList<Seat>> bFlights = new HashMap<>();
        bFlights.put(flights.get(0), createSeats(30));
        bFlights.put(flights.get(1), createSeats(30));

        // create teh booking
        bookings.add(new Booking(1 ,LocalDateTime.of(2023, 4, 1, 1, 30), 1, bFlights));

        // create a booking com.airline.DAO to connect to the booing db file
        BookingDB bookingDB = new BookingDB();
        bookingDB.resetDatabase(); //reset the database (delete all)

        // adding the bookings to the database
        bookings.forEach(booking -> {
            bookingDB.addObject(booking, true);
        });
        // display all bookings
        System.out.println(bookingDB.retrieveAll());

    }
}