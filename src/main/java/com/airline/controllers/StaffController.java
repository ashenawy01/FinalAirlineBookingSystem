package com.airline.controllers;
import com.airline.dao.BookingDB;
import com.airline.dao.ClientDB;
import com.airline.dao.FlightDB;
import com.airline.dao.StaffDB;
import com.airline.entities.Booking;
import com.airline.entities.Flight;
import com.airline.entities.Staff;

import java.time.LocalDate;
import java.util.ArrayList;
public class StaffController {
   private static final StaffDB staffdb = new StaffDB();
   private static final FlightDB flightdb = new FlightDB();
   private static final BookingDB bookingDB = new BookingDB();
   private static final ClientDB clientDB = new ClientDB();
   private static Staff currentStaff = null;

   public static Staff getCurrentStaff() {
      return currentStaff;
   }

   public static void setCurrentStaff(Staff currentStaff) {
      StaffController.currentStaff = currentStaff;
   }

   public static Staff signIn(String Email, String pass) {
      Staff staff=(Staff) staffdb.findAccount(Email,pass);
      if(staff != null){ //In case admin is not null, system will welcome admin and print out their employees.
         System.out.println("Welcome " + staff.getFirstName());
         currentStaff = staff;
         return staff;
      }
      else {System.out.println("Incorrect username or password"); // sign in is failed
         return null;}
   }

   public static StringBuilder generateFlightReport(){

      StringBuilder stringBuilder = new StringBuilder();
      if (currentStaff == null) {
         System.out.println("Error 403 - Access denied,Try to login again");
         return stringBuilder;
      }
      ArrayList<Object> flights = flightdb.retrieveAll();
      flights.forEach(fly -> {
         Flight myFlight = (Flight) fly;
         stringBuilder.append(myFlight.getFlightID() +
                 " - From { " + myFlight.getOrigin() + " }"+
                 " To { " + myFlight.getDestination() + " }"+
                 " on { " + myFlight.getFlightTime() + " } " +
                 " in " + myFlight.getDuration() + " h\n" +
                 "Seats : " + myFlight.getSeats() + "\n" +
                 "============================================\n\n");
      });
      return stringBuilder;
   }
   public static ArrayList<Flight> getAllFlights() {
      ArrayList<Flight> flights = new ArrayList<>();
      flightdb.retrieveAll().forEach(f ->{
         Flight flight = (Flight) f;
         flights.add(flight);
      });
      return flights;
   }
   public static ArrayList<Booking> getAllBookings() {
      ArrayList<Booking> bookings = new ArrayList<>();
      bookingDB.retrieveAll().forEach(b ->{
         Booking booking = (Booking) b;
         bookings.add(booking);
      });
      return bookings;
   }

   public static StringBuilder generateBookingReport(){
      StringBuilder stringBuilder = new StringBuilder();
      if (currentStaff == null) {
         System.out.println("Error 403 - Access denied,Try to login again");
         return stringBuilder;
      }

      ArrayList<Object> Books = bookingDB.retrieveAll();

      Books.forEach(book -> {

         Booking myBook = (Booking) book;
         double totalPrice = 0.0;
         for (Flight flight : myBook.getFlights().keySet()) {
            totalPrice += flight.getTicketPrice();
         }
         stringBuilder.append(" Booking ID { "+myBook.getBookingID() +
                 " } Client ID { " + myBook.getClintID() + " }"+
                 " Date { " + myBook.getDate() + " }"+
                 " Travelers " + myBook.getTravelers() + "\n" +
                 " AllFlights { " + myBook.getFlights() + " } " +"\n"+
                 " Total Fare { " + totalPrice + " $ } " +
                 "\n============================================\n\n");
      });
      return stringBuilder;
   }
   

   public static boolean updatePassword(String oldPass,String newPass){

      if (currentStaff == null) {
         System.out.println("Error 403 - Access denied,Try to login again");
         return false;
      } else {

         if (oldPass.equals(currentStaff.getPassword())){
            currentStaff.setPassword(newPass);
            return staffdb.updateStaff(currentStaff.getID(), currentStaff); // client is updated with new password
         }

         else
         {
            System.out.println("the old password is wrong, please try again ");
            return false; // function ends here with return false
         }

      }
   }

   // Display all managed flights by the current staff
   public static StringBuilder displayMyAddedFlights () {
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<Object> allFlights = flightdb.retrieveAll();
      allFlights.forEach(flightObj -> {
         Flight myFlight = (Flight) flightObj;
         if (currentStaff.getManagedFlights().contains(myFlight)) {
            stringBuilder.append(myFlight.getFlightID() +
                    " - From { " + myFlight.getOrigin() + " }"+
                    " To { " + myFlight.getDestination() + " }"+
                    " on { " + myFlight.getFlightTime() + " } " +
                    " in " + myFlight.getDuration() + " h\n" +
                    "Seats : " + myFlight.getSeats() + "\n" +
                    "============================================\n\n");
         }
      });
      return stringBuilder;
   }

   public static ArrayList<Booking> getBookingsOf(int clientId) {
      if (clientDB.findAccount(clientId) == null) {
         return null;
      }
      ArrayList<Booking> bookings = new ArrayList<>();
      ArrayList<Object> allBookings = bookingDB.retrieveAll();
      allBookings.forEach(obj ->{
         Booking booking = (Booking) obj;
         if (booking.getClintID() == clientId) {
            bookings.add(booking);
         }
      });
      return bookings;
   }
   public static ArrayList<Booking> getBookingsFilter(int clientId, LocalDate date) {
      if (clientDB.findAccount(clientId) == null) {
         return null;
      }
      ArrayList<Booking> bookings = new ArrayList<>();
      ArrayList<Object> allBookings = bookingDB.retrieveAll();
      allBookings.forEach(obj ->{
         Booking booking = (Booking) obj;
         if (booking.getClintID() == clientId
                 && booking.getDate().getDayOfYear() == date.getDayOfYear()) {
            bookings.add(booking);
         }
      });
      return bookings;
   }
   public static ArrayList<Booking> getBookingsOn(LocalDate date) {
      ArrayList<Booking> bookings = new ArrayList<>();
      ArrayList<Object> allBookings = bookingDB.retrieveAll();
      allBookings.forEach(obj ->{
         Booking booking = (Booking) obj;
         if (booking.getDate().getDayOfYear() == date.getDayOfYear()) {
            bookings.add(booking);
         }
      });
      return bookings;
   }

   public boolean updateMyInfo(String fName, String lName, String pass) {
      if (currentStaff == null) {
         System.out.println("Error 403 - Please login first");
      }
      else if(fName.length()<2){ //Validation check for first name by checking length if it is size less than 2
         System.out.println("Error! Please, Enter a valid name");
      }
      else if(lName.length()<2){ //Validation check for last name by checking length if it is size less than 2
         System.out.println("Error! Please, Enter a valid name");
      }
      else if ( pass.length() < 6 || !((pass.matches(".*[a-zA-Z].*") && pass.matches(".*\\d.*")))) { //Validation check for password inputted
         System.out.println("Error! Please, Enter a valid pass (more than 6 char, includes chars and numbers)");
      } else {
         currentStaff.setFirstName(fName);
         currentStaff.setLastName(lName);
         currentStaff.setPassword(pass);
         staffdb.updateStaff(currentStaff.getID(), currentStaff);
         return true;
      }
      return false;
   }
}
