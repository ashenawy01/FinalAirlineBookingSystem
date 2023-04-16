package com.airline.controllers;
import com.airline.dao.AdminDB;
import com.airline.dao.StaffDB;
import com.airline.entities.Admin;
import com.airline.entities.Department;
import com.airline.entities.Employee;
import com.airline.entities.Staff;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminController {

    private static AdminDB admindb = new AdminDB();
    private static StaffDB staffdb = new StaffDB();
    private static Admin currentAdmin = null;


    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        AdminController.currentAdmin = currentAdmin;
    }

    //Function to sign in for only admins
    //it will return admin object
    public Admin signIn(String Email, String pass) {

        if (Email.isEmpty() || pass.isEmpty()) {
            System.out.println("Enter a valid email and password");
            return null;
        }
        //first...the email and the password should be not null
        Admin admin = (Admin) admindb.findAccount(Email,pass);

        if(admin != null){ //In case admin is not null, system will welcome admin and print out their employees.
            System.out.println("Welcome " + admin.getFirstName()); // A welcome message will be shown to user
            this.currentAdmin = admin;
            return admin;
        }
        else {System.out.println("Incorrect username or password"); // sign in is failed
            return null;}
    }

    // update the current admin info
    public boolean updateMyInfo(String fName, String lName, String pass) {
        if (currentAdmin == null) {
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
            currentAdmin.setFirstName(fName);
            currentAdmin.setLastName(lName);
            currentAdmin.setPassword(pass);
            admindb.updateAdmin(currentAdmin.getID(), currentAdmin);
            return true;
        }
        return false;
    }
    public Admin createAdmin(String firstName, String lastName, String email, String password, boolean isGlobal, boolean isActive){
        if(firstName.length()<2){ //Validation check for first name by checking length if it is size less than 2
            System.out.println("Error! Please, Enter a valid name");
            return null; // function ends here if length <2

        }
        else if(lastName.length()<2){ //Validation check for last name by checking length if it is size less than 2
            System.out.println("Error! Please, Enter a valid name");
            return null; // // function ends here if length <2

        }

        else if ( password.length() < 6 || !((password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*")))) { //Validation check for password inputted
            System.out.println("Error! Please, Enter a valid pass (more than 6 char, includes chars and numbers)");
            return null; // function ends here if password is invalid

        }
        else if (!isValid(email)){ //validation check for email
        System.out.println("Error! Please, Enter a valid Email ");
        return null; // function ends here if email is invalid

        }
            else { // All parameters obtained are entered correctly by user and function controller will start doing its purpose
            if (this.currentAdmin.isActive()) { // validation check to check admin if admin is not banned
                Admin admin=new Admin( firstName, lastName, email, password,isGlobal, isActive); // A new admin object is created.
                if(admindb.addObject(admin,true)){ // to check admin is added.
                    System.out.println("Admin : "+admin.getFirstName() + " was added successfully"); // A welcome message to the new admin
                    return admin; // function ends here after admin is added to database
                }

                else{
                    System.out.println("Error with database connection, please try again");
                }

            } else  {
                System.out.println("sorry! your account is not active");
            }
        }
        return null; // function ends here if either account is not active or error connection with database.
    }

    public boolean UpdatePassword(String oldPass,String newPass){

            if (oldPass.equals(currentAdmin.getPassword())){
                currentAdmin.setPassword(newPass);
                return admindb.updateAdmin(currentAdmin.getID(), currentAdmin); // client is updated with new password
            } else {
                System.out.println("the old password is wrong, please try again ");
                return false; // function ends here with return false
            }
    }
    private boolean isStaffExisted(String email) {
        ArrayList<Object> staffUsers =  staffdb.retrieveAll();
        Staff staff;
        for (int i = 0; i < staffUsers.size(); i++) {
            staff = (Staff) staffUsers.get(i);
            if (staff.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }


    public Staff createStaff(String firstName, String lastName, String email, String password, String jobTitle, Department department){
        if( firstName.length()<2){ // Validation check for length of firstname less than 2
            System.out.println("Error! Please, Enter a valid name");
            return null;

        } else if (isStaffExisted(email)){ // check if the email is already existed
            System.out.println("Error - the email is already existed");
            return null;

        } else if(lastName.length()<2){ // Validation check for length of lastname less than 2
            System.out.println("Error! Please, Enter a valid name");
            return null;
        }

        else if (email==null){ // Validation check if email is null.
            System.out.println("Error! Please, Enter a valid Email");
            return null;
        }

        else if(!isValid(email)){ // Validation check if email is not valid.
            System.out.println("Error! Please, Enter a valid Email ");
            return null;
        }
        else if ( password.length() < 6 || !((password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*")))) { // if password entered is not valid
            System.out.println("Error! Please, Enter a valid pass (more than 6 char, includes chars and numbers)");
            return null;
        }

        else {
            if (this.currentAdmin.isActive()) { // To check if admin is active
                Staff staff = new Staff( firstName,lastName,email, password, jobTitle, department); // a new object of staff is created to store data from parameters.

                if(staffdb.addObject(staff,true)){ // To check object is added to database successfully
                    System.out.println("added successfully");
                    return staff;//function ends here
                }
                else
                    System.out.println("Error with database connection, please try again");
            }

            else  {
                System.out.println("sorry! your account is not active");
            }
        }
        return null; // function ends here if either account is not active or error with database connection.
    }
    public boolean deleteEmployee(int empID, boolean isAdmin){
        if(isAdmin){
            // to see if account exists in database
            if (admindb.findAccount(empID) == null) {
                System.out.println("User is not existed in admin database");
                return false;
            }

            if(currentAdmin.isGlobal()){ // to check account is admin and global
                //admin account is deleted and function ends here
                return admindb.deleteAccount(empID);
            } else {
                System.out.println("Error 403 - Access denied");
                return false; // in case admin account is not global and function ends here
            }
        }else {
            if (staffdb.findAccount(empID) == null) {
                System.out.println("User is not existed in staff database");
                return false;
            }
            return staffdb.deleteAccount(empID); // employee account is deleted
        }
    }

    public boolean activeAdmin(int adminID, boolean apply){
        if (!(currentAdmin.isActive() && currentAdmin.isGlobal())) {
            System.out.println("Error 403 - Access denied!");
            return false;
        }

        Admin myAdmin = (Admin) admindb.findAccount(adminID); //admin with ID taken from parameter is searched in database.
        if (myAdmin!= null){
            myAdmin.setActive(apply);
            return admindb.updateAdmin(adminID, myAdmin); //Admin is updated in database.
        }
        else // adminID is null
            {
                System.out.println("the id you entered is not exist ...please try again");
                return false; //function ends here
            }
    }

    public ArrayList<Staff> listAllStaff(){
        ArrayList<Staff> allStaff = new ArrayList<>(); // a new arraylist of type Admin is created

        staffdb.retrieveAll().forEach(staffObj -> {
            Staff staff = (Staff) staffObj;
            allStaff.add(staff);
        });

        return allStaff; //function ends here with Admn returned
    }

    public static ArrayList<Admin> listAllAdmins(){
        ArrayList<Admin> allAdmins = new ArrayList<>(); // a new arraylist of type Admin is created

        admindb.retrieveAll().forEach(adminObj -> {
            Admin admin = (Admin) adminObj;
            allAdmins.add(admin);
        });

        return allAdmins; //function ends here with Admn returned
    }


    public static Employee FindAdminByID(int EmpID){
        Admin myAdmin = (Admin)admindb.findAccount(EmpID);
        if(myAdmin != null){ // employee account searched using ID inside admin database and if condition checks it dose not return null
            return myAdmin; //function ends here with admin being found and returned
        } else {
            return null;
        }
    } public static Employee FindStaffByID(int EmpID){
        Staff myStaff = (Staff) staffdb.findAccount(EmpID);
        if(myStaff != null){ // employee account searched using ID inside admin database and if condition checks it dose not return null
            return myStaff; //function ends here with admin being found and returned
        } else {
            return null;
        }
    }

    //to check email validation
    public boolean isValid(String email)
    {
        if (email == null) {
            System.out.println("Error - invalid email address");
            return false;
        }
        String userEmail;

        for (Admin admin : listAllAdmins()) {
            if (admin.getEmail().equals(email)) {
                System.out.println("User is already existed!");
                return false;
            }
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"; //This line defines a regular expression pattern that is used to validate whether the input email string is in a valid format or not.

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches(); //This line uses the matcher() method of the Pattern object to create a Matcher object that can match the input email string against the regular expression pattern. The matches() method of the Matcher object is then used to check whether the input email string matches the pattern or not. If it does, the method returns true, indicating that the email is valid. Otherwise, it returns false, indicating that the email is not valid.
    }
}

