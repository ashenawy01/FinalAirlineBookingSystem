package com.airline.controllers.Admin.Controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.airline.controllers.AdminController;
import com.airline.entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminHome extends AdminViewController implements Initializable {


    @FXML
    private TableView table;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn fnameColumn;
    @FXML
    private TableColumn lnameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn passwordColumn;
    @FXML
    private TableColumn createdAtColumn;
    @FXML
    private ComboBox<String> filterComboBox;


    private static ObservableList<Employee> employeeData;
    private ArrayList<Employee> employees = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add all staff and employees to the Employee ArrayList
        employees.addAll(adminController.listAllStaff());
        employees.addAll(adminController.listAllAdmins());

        // Initialize the observableList and add all employees
        employeeData = FXCollections.observableArrayList(employees);

        // set the cellValueFactory of each TableColumn to the corresponding field name of the Employee class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAT"));

        // Set the observable list to the table
        table.setItems(employeeData);

        // Set up the filter combo box
        filterComboBox.setValue("All Employees");
        filterComboBox.setOnAction(e -> handleFilterComboBox());
    }




    // Handle the filter actions
    private void handleFilterComboBox() {
        if (filterComboBox.getValue().equals("Admins only")) {
            employeeData.clear();
            employeeData.addAll(adminController.listAllAdmins());
        } else if (filterComboBox.getValue().equals("Staff only")) {
            employeeData.clear();
            employeeData.addAll(adminController.listAllStaff());
        } else { // Add all admins and staff users
            employeeData.clear();
            employeeData.addAll(employees);
        }

    }
}

