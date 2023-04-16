package com.airline.dao;

import com.airline.entities.Admin;

import java.io.*;
import java.util.ArrayList;

public class AdminDB implements UsersDatabase, IDatabase {
    private static final String adminDBFile = "adminFile.bin";
    private final int firstID = 1;

    // This function will be called once only to create the file that stores Admin objects
    // Reset database (clear the file)
    @Override
    public void resetDatabase () {
       // buffering the ObjectOutputStream by BufferedOutputStream and with size of 8192 bytes (or 8 kilobytes)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(adminDBFile), 8192)) ) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append an object of Admin to the database file
    @Override
    public boolean addObject(Object obj, boolean isNew) {

        Admin admin = (obj instanceof Admin)? (Admin) obj : null;

        // return false if the parameter object is null
        if (admin == null) {
            return false;
        }

        // Opening the output stream (the second argument is true for appending )
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(adminDBFile, true))) {
            // Override ObjectOutputStream's writeStreamHeader method to reset the stream header
            // This is necessary to append new objects to an existing file
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            // giving ID to the new user
            if (isNew) {
                admin.setID(generateID());
            }

            // Write the admin object to the file
            oos.writeObject(admin);
            return true;
        } catch (IOException e) {
            // Print stack trace for any IO exceptions
            e.printStackTrace();
            return false;
        }
    }
    // update an object with a new one with the same id
    public boolean updateAdmin (int adminID, Admin newAdmin) {
        Admin oldAdmin =  (Admin) findAccount(adminID);
        // Check if is existed
        if (oldAdmin == null) {
            return false;
        }
        // set the same id fo the new update
        newAdmin.setID(adminID);
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        // reset database file (delete all objects)
        resetDatabase();

        // re-adding all objects again to the database file
        Admin admin;
        for (Object o : existedAccounts) {
            admin = (Admin) o;
            if (admin.getID() == oldAdmin.getID()) {
                addObject(newAdmin, false); // adding the updated object
            }
            else {
                addObject(admin, false); // adding the old objects
            }
        }
        return true;

    }

    // retrieving all stored objects in the database file in an ArrayList of Admin objects
    @Override
    public ArrayList<Object> retrieveAll() {
        ArrayList<Object> admins = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(adminDBFile)))) {
            // Read all admin objects from the file and add them to the admins list
            // the loop will end once the "readObject()" function throw EOFException
            while (true) {
                Admin admin = (Admin) ois.readObject();
                admins.add(admin);
            }

        } catch (EOFException e) {
            // return all admins objects
            return admins;
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
        Admin unWantdAdmin = (Admin) findAccount(userID);
        if (unWantdAdmin == null) {
            return false; // not existed
        }
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        resetDatabase(); // Reset the database
        // re-adding all the old objects except the unwanted one
        Admin admin;
        for (Object o : existedAccounts) {
            admin = (Admin) o;
            if (admin.getID() != unWantdAdmin.getID()) {
                addObject(admin, false);
            }
        }
        return true;
    }

    // Find Admin account by its ID
    @Override
    public Object findAccount(int userId) {

        Admin admin;
        for(Object obj : retrieveAll()){
             admin = (Admin) obj;
             if (admin.getID() == userId) {
                 return admin;
             }
        }
        return null;
    }

    // Find Admin account by its email and password
    @Override
    public Object findAccount(String email, String pass) {
        Admin admin;
        for(Object obj : retrieveAll()){
            admin = (Admin) obj;
            if (admin.getEmail().equals(email) && admin.getPassword().equals(pass)) {
                return admin;
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
        Admin admin = (Admin) retrieveAll().get(size - 1);

        newID = admin.getID() + 1;
        return newID;
    }
}
