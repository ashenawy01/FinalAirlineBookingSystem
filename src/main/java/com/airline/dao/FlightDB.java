package com.airline.dao;

import com.airline.entities.Flight;

import java.io.*;
import java.util.ArrayList;

public class FlightDB implements IDatabase {

    private static final String flightDBFile = "flightFile.bin";
    private final int firstID = 1;

    // This function will be called once only to create the file that stores Flight objects
    // Reset database (clear the file)
    @Override
    public void resetDatabase () {
        // buffering the ObjectOutputStream by BufferedOutputStream and with size of 8192 bytes (or 8 kilobytes)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(flightDBFile), 8192)) ) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addObject(Object obj, boolean isNew) {
        Flight flight = (obj instanceof Flight)? (Flight) obj : null;
        // return false if the parameter object is null
        if (flight == null) {
            return false;
        }

        // Opening the output stream (the second argument is true for appending )
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(flightDBFile, true))) {
            // Override ObjectOutputStream's writeStreamHeader method to reset the stream header
            // This is necessary to append new objects to an existing file
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            // giving ID to the new user
            if (isNew) {
                flight.setFlightID(generateID());
            }
            // Write the flight object to the file
            oos.writeObject(flight);
            return true;
        } catch (IOException e) {
            // Print stack trace for any IO exceptions
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Object> retrieveAll() {
        ArrayList<Object> flights = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(flightDBFile)))) {
            // Read all flight objects from the file and add them to the flights list
            // the loop will end once the "readObject()" function throw EOFException
            while (true) {
                Flight flight = (Flight) ois.readObject();
                flights.add(flight);
            }

        } catch (EOFException e) {
            // return all flights objects
            return flights;
        } catch (ClassNotFoundException | IOException e) {
            // Print stack trace for any exceptions
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int generateID() {
        // newID for the next user
        int newID;
        // last added user position
        int size = retrieveAll().size();

        // Check if it is the first entered user
        if (size < 1) {
            return firstID; // assign first user id the first id value
        }
        // Last added user
        Flight flight = (Flight) retrieveAll().get(size - 1);

        newID = flight.getFlightID() + 1;
        return newID;
    }
    
    public  Flight findFlight (int flightID) {
        Flight flight;
        for(Object obj : retrieveAll()){
            flight = (Flight) obj;
            if (flight.getFlightID() == flightID) {
                return flight;
            }
        }
        return null;
    }
    
    public boolean deleteFlight (int flightID) {
        // Find the unwanted account
        Flight unWantdFlight = (Flight) findFlight(flightID);
        if (unWantdFlight == null) {
            return false; // not existed
        }
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        resetDatabase(); // Reset the database
        // re-adding all the old objects except the unwanted one
        Flight flight;
        for (Object o : existedAccounts) {
            flight = (Flight) o;
            if (flight.getFlightID() != unWantdFlight.getFlightID()) {
                addObject(flight, false);
            }
        }
        return true;
    }

    public boolean updateFlight (int flightID, Flight newFlight) {
        Flight oldFlight =  (Flight) findFlight(flightID);
        // Check if is existed
        if (oldFlight == null) {
            return false;
        }
        // set the same id for the new update
        newFlight.setFlightID(flightID);
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        // reset database file (delete all objects)
        resetDatabase();

        // re-adding all objects again to the database file
        Flight flight;
        for (Object o : existedAccounts) {
            flight = (Flight) o;
            if (flight.getFlightID() == oldFlight.getFlightID()) {
                addObject(newFlight, false); // adding the updated object
            }
            else {
                addObject(flight, false); // adding the old objects
            }
        }
        return true;

    }

}
