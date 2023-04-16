package com.airline.dao;

import java.util.ArrayList;

public interface IDatabase {

    public void resetDatabase();
    // Helper function to generate ID for each user as a unique identifier
    // it returns the last id in the database + 1
    int generateID ();

    // Main adding function to add object to its database
    boolean addObject(Object obj, boolean isNew);

    // retrieve all accounts of a specific database  in an arrayList
    // of object (Admin, Staff or Client) of these accounts
    ArrayList<Object> retrieveAll();

}
