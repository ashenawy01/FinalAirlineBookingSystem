package com.airline.entities;


import java.io.Serializable;
import java.util.Comparator;

public class Seat implements Serializable,  Comparable<Seat>, Comparator<Seat> {
    private String seatNumber;
    private ClassType classType;
    private boolean isBooked;

    private int clientID;

    public Seat() {}
    public Seat(String seatNumber, ClassType classType) {
        this.seatNumber = seatNumber;
        this.classType = classType;
        isBooked = false;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public void book(int clientID){
        this.clientID = clientID;
        this.isBooked = true;
    }
    public void cancelBook(){
        clientID = 0;
        this.isBooked = false;
    };
    @Override
    public int compareTo(Seat other) {
        // compare the seats by their seat numbers
        return this.getSeatNumber().compareTo(other.getSeatNumber());
    }

    @Override
    public int compare(Seat seat1, Seat seat2) {
        // compare two seats by their seat numbers
        return seat1.getSeatNumber().compareTo(seat2.getSeatNumber());
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                ", classType=" + classType +
                ", isBooked=" + isBooked +
                '}';
    }
}
