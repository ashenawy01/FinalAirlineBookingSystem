package com.airline.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Booking implements Serializable, Comparator<Booking>, Comparable<Booking> {
    private static final long serialVersionUID = 1L;

    private int bookingID;
    private int clintID;
    private LocalDateTime date;
    private int  travelers;
    private Map<Flight, ArrayList<Seat>> flights = new HashMap<>();

    public Booking() {}

    @Override
    public int hashCode() {
        return Objects.hash(bookingID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Booking)) return false;
        return this.bookingID == ((Booking) obj).getBookingID();
    }

    public Booking(int clintID, LocalDateTime date, int travelers, Map<Flight, ArrayList<Seat>> flights) {
        this.clintID = clintID;
        this.date = date;
        this.travelers = travelers;
        this.flights = flights;
    }

    public Booking(int clintID, LocalDateTime date, int travelers) {
        this.clintID = clintID;
        this.date = date;
        this.travelers = travelers;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getClintID() {
        return clintID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getTravelers() {return travelers;}

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setClintID(int clintID) {
        this.clintID = clintID;
    }

    public void setTravelers(int travelers) {
        this.travelers = travelers;
    }

    public void setFlights(Map<Flight, ArrayList<Seat>> flights) {
        this.flights = flights;
    }
    public int getFlightListSize() {
        return flights.size();
    }
    public double getTotalFare() {
         double price = 0.0;
         for (Flight flight : flights.keySet()) {
             price += flight.getTicketPrice();
         }
         return price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", clintID=" + clintID +
                ", date=" + date +
                ", travelers=" + travelers +
                '}';
    }

    public Map<Flight, ArrayList<Seat>> getFlights() {
        return flights;
    }

    public boolean addFlight(Flight flight, ArrayList<Seat> seats) { //This function adds flight to database
        if (flight != null) { // Checks if flight is not empty
            flights.put(flight, seats);
            return true;
        }
        return false;
    }

    public void deleteFlight  (Flight flight) { // This function removes flight from database
         flights.remove(flight);
    }

    @Override
    public int compareTo(Booking booking) {
        return Integer.valueOf(this.bookingID).compareTo(booking.bookingID);
    }

    @Override
    public int compare(Booking b1, Booking b2) {
        return Integer.valueOf(b1.bookingID).compareTo(Integer.valueOf(b2.bookingID));
    }
}
