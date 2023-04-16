package com.airline.dao;

import com.airline.entities.Staff;

import java.io.*;
import java.util.ArrayList;

public class StaffDB implements UsersDatabase, IDatabase {
    private static final String staffDBFile = "staffFile.bin";
    private final int firstID = 1;


    // This function will be called once only to create the file that stores Staff objects
    // Reset database (clear the file)
    @Override
    public void resetDatabase () {
        // buffering the ObjectOutputStream by BufferedOutputStream and with size of 8192 bytes (or 8 kilobytes)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(staffDBFile), 8192)) ) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append an object of Staff to the database file
    @Override
    public boolean addObject(Object obj, boolean isNew) {

        Staff staff = (obj instanceof Staff)? (Staff) obj : null;
        // return false if the parameter object is null
        if (staff == null) {
            return false;
        }

        // Opening the output stream (the second argument is true for appending )
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(staffDBFile, true))) {
            // Override ObjectOutputStream's writeStreamHeader method to reset the stream header
            // This is necessary to append new objects to an existing file
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            // giving ID to the new user
            if (isNew) {
                staff.setID(generateID());
            }

            // Write the staff object to the file
            oos.writeObject(staff);
            return true;
        } catch (IOException e) {
            // Print stack trace for any IO exceptions
            e.printStackTrace();
            return false;
        }
    }
    // update an object with a new one with the same id
    public boolean updateStaff (int staffID, Staff newStaff) {
        Staff oldStaff =  (Staff) findAccount(staffID);
        // Check if is existed
        if (oldStaff == null) {
            return false;
        }
        // set the same id fo the new update
        newStaff.setID(staffID);
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        // reset database file (delete all objects)
        resetDatabase();

        // re-adding all objects again to the database file
        Staff staff;
        for (Object o : existedAccounts) {
            staff = (Staff) o;
            if (staff.getID() == oldStaff.getID()) {
                addObject(newStaff, false); // adding the updated object
            }
            else {
                addObject(staff, false); // adding the old objects
            }
        }
        return true;

    }

    // retrieving all stored objects in the database file in an ArrayList of Staff objects
    @Override
    public ArrayList<Object> retrieveAll() {
        ArrayList<Object> staffs = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(staffDBFile)))) {
            // Read all staff objects from the file and add them to the staffs list
            // the loop will end once the "readObject()" function throw EOFException
            while (true) {
                Staff staff = (Staff) ois.readObject();
                staffs.add(staff);
            }

        } catch (EOFException e) {
            // return all staffs objects
            return staffs;
        } catch (IOException | ClassNotFoundException e) {
            // Print stack trace for any exceptions
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Deleting a specific account by its id
    @Override
    public boolean deleteAccount(int userID) {
        // Find the unwanted account
        Staff unWantdStaff = (Staff) findAccount(userID);
        if (unWantdStaff == null) {
            return false; // not existed
        }
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        resetDatabase(); // Reset the database
        // re-adding all the old objects except the unwanted one
        Staff staff;
        for (Object o : existedAccounts) {
            staff = (Staff) o;
            if (staff.getID() != unWantdStaff.getID()) {
                addObject(staff, false);
            }
        }
        return true;
    }

    // Find Staff account by its ID
    @Override
    public Object findAccount(int userId) {

        Staff staff;
        for(Object obj : retrieveAll()){
            staff = (Staff) obj;
            if (staff.getID() == userId) {
                return staff;
            }
        }
        return null;
    }

    // Find Staff account by its email and password
    @Override
    public Object findAccount(String email, String pass) {
        Staff staff;
        for(Object obj : retrieveAll()){
            staff = (Staff) obj;
            if (staff.getEmail().equals(email) && staff.getPassword().equals(pass)) {
                return staff;
            }
        }
        return null;
    }

    // Generate new id for the new user
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
        Staff staff = (Staff) retrieveAll().get(size - 1);

        newID = staff.getID() + 1;
        return newID;
    }
}
