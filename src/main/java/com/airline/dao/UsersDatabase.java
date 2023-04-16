package com.airline.dao;

public interface UsersDatabase {

    // delete a specific account from the system (Admin, Staff or Client) with the id (userID)
    boolean deleteAccount(int userID);


    // Finding a specific user from his id
    // this function should be implemented (private)
    // it should only assess the other functions
     Object findAccount (int userId);

    // This function should be used in other classes in the controller package for login
    // return the required user with his email and password or null if not existed
    Object findAccount (String email, String pass);



}
