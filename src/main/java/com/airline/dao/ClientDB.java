package com.airline.dao;

import com.airline.entities.Client;

import java.io.*;
import java.util.ArrayList;

public class ClientDB implements UsersDatabase, IDatabase {
    private static final String clientDBFile = "clientFile.bin";
    private final int firstID = 1;


    // This function will be called once only to create the file that stores Client objects
    // Reset database (clear the file)
    @Override
    public void resetDatabase () {
        // buffering the ObjectOutputStream by BufferedOutputStream and with size of 8192 bytes (or 8 kilobytes)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(clientDBFile), 8192)) ) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append an object of Client to the database file
    @Override
    public boolean addObject(Object obj, boolean isNew) {
        Client client = (obj instanceof Client)? (Client) obj : null;


        // return false if the parameter object is null
        if (client == null) {
            return false;
        }

        // Opening the output stream (the second argument is true for appending )
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(clientDBFile, true))) {
            // Override ObjectOutputStream's writeStreamHeader method to reset the stream header
            // This is necessary to append new objects to an existing file
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        }) {
            // giving ID to the new user
            if (isNew) {
                client.setId(generateID());
            }

            // Write the client object to the file
            oos.writeObject(client);
            return true;
        } catch (IOException e) {
            // Print stack trace for any IO exceptions
            e.printStackTrace();
            return false;
        }
    }
    // update an object with a new one with the same id
    public boolean updateClient (int clientID, Client newClient) {
        Client oldClient =  (Client) findAccount(clientID);
        // Check if is existed
        if (oldClient == null) {
            return false;
        }
        // set the same id fo the new update
        newClient.setId(clientID);
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        // reset database file (delete all objects)
        resetDatabase();

        // re-adding all objects again to the database file
        Client client;
        for (Object o : existedAccounts) {
            client = (Client) o;
            if (client.getId() == oldClient.getId()) {
                addObject(newClient, false); // adding the updated object
            }
            else {
                addObject(client, false); // adding the old objects
            }
        }
        return true;

    }

    // retrieving all stored objects in the database file in an ArrayList of Client objects
    @Override
    public ArrayList<Object> retrieveAll() {
        ArrayList<Object> clients = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(clientDBFile)))) {
            // Read all client objects from the file and add them to the clients list
            // the loop will end once the "readObject()" function throw EOFException
            while (true) {
                Client client = (Client) ois.readObject();
                clients.add(client);
            }

        } catch (EOFException e) {
            // return all clients objects
            return clients;
        } catch (ClassNotFoundException | IOException e) {
            // Print stack trace for any exceptions
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Deleting a specific account by its id
    @Override
    public boolean deleteAccount(int userID) {
        // Find the unwanted account
        Client unWantdClient = (Client) findAccount(userID);
        if (unWantdClient == null) {
            return false; // not existed
        }
        // retrieve all objects
        ArrayList<Object> existedAccounts = retrieveAll();
        resetDatabase(); // Reset the database
        // re-adding all the old objects except the unwanted one
        Client client;
        for (Object o : existedAccounts) {
            client = (Client) o;
            if (client.getId() != unWantdClient.getId()) {
                addObject(client, false);
            }
        }
        return true;
    }

    // Find Client account by its ID
    @Override
    public Object findAccount(int userId) {

        Client client;
        for(Object obj : retrieveAll()){
            client = (Client) obj;
            if (client.getId() == userId) {
                return client;
            }
        }
        return null;
    }

    // Find Client account by its email and password
    @Override
    public Object findAccount(String email, String pass) {
        Client client;
        for(Object obj : retrieveAll()){
            client = (Client) obj;
            if (client.getEmail().equals(email) && client.getPassword().equals(pass)) {
                return client;
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
        Client client = (Client) retrieveAll().get(size - 1);

        newID = client.getId() + 1;
        return newID;
    }
}
