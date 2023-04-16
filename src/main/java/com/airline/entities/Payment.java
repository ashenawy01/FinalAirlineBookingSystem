package com.airline.entities;

public class Payment {
    private int id;
    private double amount;
    private int clientID;
    private int bookingID;

    public Payment() {}
    public Payment(int id, double amount, int clientID, int bookingID) {
        this.id = id;
        this.amount = amount;
        this.clientID = clientID;
        this.bookingID = bookingID;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getClientID() {
        return clientID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", clientID=" + clientID +
                ", bookingID=" + bookingID +
                '}';
    }
}
