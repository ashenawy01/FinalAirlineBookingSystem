package com.airline.dao;

import com.airline.entities.Booking;

import java.io.*;
import java.util.ArrayList;

public class BookingDB implements IDatabase {
    private static final String bookingDBFile = "bookingFile.bin";
    private final int firstID = 1;
    
    // This function will be called once only to create the file that stores booking objects
    // Reset database (clear the file)
    @Override
    public void resetDatabase () {
        // buffering the ObjectOutputStream by BufferedOutputStream and with size of 8192 bytes (or 8 kilobytes)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(bookingDBFile), 8192)) ) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addObject(Object obj, boolean isNew) {
        Booking booking = (obj instanceof Booking)? (Booking) obj : null;

        // return false if the parameter object is null
        if (booking == null) {
            return false;
        }

        // Opening the output stream (the second argument is true for appending )
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(bookingDBFile, true))) {
            // Override ObjectOutputStream's writeStreamHeader method to reset the stream header
            // This is necessary to append new objects to an existing file
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            // giving ID to the new user
            if (isNew) {
                booking.setBookingID(generateID());
            }

            // Write the booking object to the file
            oos.writeObject(booking);
            return true;
        } catch (IOException e) {
            // Print stack trace for any IO exceptions
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Object> retrieveAll() {
        ArrayList<Object> bookings = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(bookingDBFile)))) {
            // Read all booking objects from the file and add them to the bookings list
            // the loop will end once the "readObject()" function throw EOFException
            while (true) {
                Booking booking = (Booking) ois.readObject();
                bookings.add(booking);
            }

        } catch (EOFException e) {
            // return all bookings objects
            return bookings;
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
        Booking booking = (Booking) retrieveAll().get(size - 1);

        newID = booking.getBookingID() + 1;
        return newID;
    }

    public Booking findBooking(int bookingID){
    Booking booking;
    for(Object obj : retrieveAll()){
        booking = (Booking) obj;
        if(booking.getBookingID()==bookingID){
            return booking;
        }
    }
        return null;
    }

    public boolean deleteBooking (int bookingID){
        // Find unwanted booking
        Booking unWantedBooking = (Booking) findBooking(bookingID);
        if(unWantedBooking == null){
            return false; // not existed
        }
        // retrieve all objects
        ArrayList<Object> existingBooking = retrieveAll();
        resetDatabase(); // Reset the database
        // re-adding all the old objects except the unwanted one
        Booking booking;
        for (Object o : existingBooking){
            booking = (Booking) o;
            if(booking.getBookingID() != unWantedBooking.getBookingID()){
                addObject(booking, false);
            }
        }
        return true;
    }

    public boolean updateBooking(int bookingID, Booking newBooking){
        Booking oldBooking = (Booking) findBooking(bookingID);
        // Check if is existed
        if(oldBooking == null){
            return false;
        }
        // set the same id for the new update
        newBooking.setBookingID(bookingID);
        // retrieve all object
        ArrayList<Object> existingBooking = retrieveAll();
        // reset database file (delete all objects)
        resetDatabase();
        // re-adding all the old objects except the unwanted one
        Booking booking;
        for (Object o : existingBooking){
            booking = (Booking) o;
            if(booking.getBookingID() == oldBooking.getBookingID()){
                addObject(newBooking, false); //adding the updated booking
            }
            else{
                addObject(booking, false); //adding all the old bookings
            }
        }
        return true;

    }

}
