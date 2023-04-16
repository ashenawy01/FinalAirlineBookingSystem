package com.airline.Testing;

import com.airline.controllers.AdminController;
import com.airline.entities.Admin;
import com.airline.entities.Department;

/*
    This class is for testing
    all available features for Admin user(AdminController methode).
    Results :
    1 - All functions work as expected
    2 - Some methods throw unhandled exception in some cases (Wrong inputs)

    Notes:
    1 - The message "User is already existed" is because the code has been run before
    so, the users were already added. Change the email to add new users
    2 - The message "User is not existed in staff database" is because the user was deleted before as well


    ## Last update : 9/4/2023 - 11:10AM
*/
public class AdminViewTest {

    static AdminController adminController = new AdminController();

    public static void main(String[] args) {



//        #################  Admin controller testing  #################

        Admin admin;

        // login by Abdelrhman account (Global Admin)
        admin = adminController.signIn("abdelrhman225328@bue.edu.eg", "test123");


        // create account for Rodina Ahmed as a global Admin
        adminController.createAdmin("Rodina", "Ahmed",
                "rodina.ahmed@bue.edu.eg", "test123",
                true, true);

        // login by rodina account
        admin = adminController.signIn("rodina.ahmed@bue.edu.eg", "test123");
        System.out.println(admin);


        //Update password
        System.out.println(adminController.UpdatePassword("test123", "test123"));

        //Create a new staff account
        adminController.createStaff("Noura", "Ahmed", "noura123@bue.edu.eg", "test123", "Flight Manager", Department.Reservations);

        // Deleting staff
        adminController.deleteEmployee(5, false);
        System.out.println(adminController.listAllStaff());
        adminController.deleteEmployee(6, false);
        System.out.println(adminController.listAllStaff());

        // list all employees (admins & staff)
        System.out.println("\n***********  All staff  ***********  ");
        System.out.println(adminController.listAllStaff());
        System.out.println("\n***********  All Admins  ***********  ");
        System.out.println(adminController.listAllAdmins());


    }
}
