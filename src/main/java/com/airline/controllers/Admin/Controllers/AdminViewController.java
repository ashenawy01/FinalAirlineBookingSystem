package com.airline.controllers.Admin.Controllers;

import com.airline.controllers.AdminController;
import com.airline.controllers.StaffController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminViewController{

    protected static final AdminController adminController = new AdminController();
    private final String viewPath = "/com/airlineview/views/";
    private final String adminPath = viewPath + "AdminViews/";


    @FXML
    Hyperlink signOut;
    @FXML
    Hyperlink home;
    @FXML
    Hyperlink createAdmin;
    @FXML
    Hyperlink createStaff;
    @FXML
    Hyperlink deleteEmp;
    @FXML
    Hyperlink myProfile;

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Hyperlink link = (Hyperlink) event.getSource();

        switch (link.getId()) {
            case "home":
                loadContent("AdminHome.fxml");
                break;
            case "createAdmin":
                loadContent("create_admin.fxml");
                break;
            case "createStaff":
                loadContent( "create_staff.fxml");
                break;
            case "deleteEmp":
                System.out.println("manage");
                loadContent( "delete_employee.fxml");
                break;
            case "myProfile":
                System.out.println("my profile");
                loadContent( "my_profile.fxml");
                break;
            case "signOut":
                loadContent( "AdminLoginForm.fxml");
                // handle sign out here
                break;
            default:
                // handle unknown link here
                break;
        }
    }

    private void loadContent(String page) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource(adminPath + page));
        Scene scene = new Scene(root);
        Stage stage = (Stage) home.getScene().getWindow();
        stage.setScene(scene);
    }

}
