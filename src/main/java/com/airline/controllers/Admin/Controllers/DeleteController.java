package com.airline.controllers.Admin.Controllers;

import com.airline.entities.Admin;
import com.airline.entities.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class DeleteController extends AdminViewController {

    @FXML
    private ToggleGroup empType;
    @FXML
    private TextField empIdFiled;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField deptField;
    @FXML
    private Label errorMsg;
    @FXML
    private Button deActiveBtn;
    @FXML
    private Button activeBtn;
    private void displayAdmin(Admin admin) {
        firstNameField.setText(admin.getFirstName());
        lastNameField.setText(admin.getLastName());
        emailField.setText(admin.getEmail());
        passwordField.setText(admin.getPassword());
        if (admin.isGlobal() && admin.isActive()) {
            statusField.setText("Global Active Admin");
        } else if (admin.isActive() && !admin.isGlobal()) {
            statusField.setText("Local Active Admin");
        } else if (admin.isGlobal() && !admin.isActive()){
            statusField.setText("Global Non-Active Admin");
        } else {
            statusField.setText("Local Non-Active Admin");
        }
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        emailField.setVisible(true);
        passwordField.setVisible(true);
        statusField.setVisible(true);
    }
    private void clearAll (){
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);
        jobTitleField.setText(null);
        deptField.setText(null);
        statusField.setText(null);
        empIdFiled.setText(null);
        errorMsg.setText("Sorry! User is not existed");

        deptField.setVisible(false);
        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        emailField.setVisible(false);
        passwordField.setVisible(false);
        statusField.setVisible(false);
        jobTitleField.setVisible(false);
        errorMsg.setVisible(false);
        activeBtn.setDisable(true);
        deActiveBtn.setDisable(true);
    }



    public void searchHandle(ActionEvent actionEvent) {
        if (!empIdFiled.getText().matches("[0-9]+")) {
            errorMsg.setText("Please Enter positive numbers only");
            errorMsg.setVisible(true);
            return;
        }
        RadioButton selectedRadioButton = (RadioButton) empType.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedText = selectedRadioButton.getText();
            if (selectedText.equals("Admin")){
                Admin admin = (Admin) adminController.FindAdminByID(Integer.parseInt(empIdFiled.getText()));
                if (admin == null) {
                    errorMsg.setVisible(true);
                } else {
                    clearAll();
                    displayAdmin(admin);
                    deActiveBtn.setDisable(false);
                    activeBtn.setDisable(false);
                }
            } else {
                Staff staff = (Staff) adminController.FindStaffByID(Integer.parseInt(empIdFiled.getText()));
                if (staff == null) {
                    errorMsg.setVisible(true);
                } else {
                    clearAll();
                    displayStaff(staff);
                }
            }
        } else {
            errorMsg.setText("Please Select the account type");
            errorMsg.setVisible(true);
        }
    }

    private void displayStaff(Staff staff) {
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        emailField.setText(staff.getEmail());
        passwordField.setText(staff.getPassword());
        jobTitleField.setText(staff.getJobTitle());
        deptField.setText(staff.getDepartment().toString());
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        emailField.setVisible(true);
        passwordField.setVisible(true);
        jobTitleField.setVisible(true);
        deptField.setVisible(true);
    }

    public void deleteHandler () {
        RadioButton selectedRadioButton = (RadioButton) empType.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedText = selectedRadioButton.getText();
            if (selectedText.equals("Admin")){
                Admin admin = (Admin) adminController.FindAdminByID(Integer.parseInt(empIdFiled.getText()));
                if (admin == null) {
                    errorMsg.setVisible(true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Are you sure you want to delete: " + admin.getFirstName() +" with ID: " + admin.getID());
                    alert.setContentText("This action cannot be undone.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        // User clicked OK, delete admin
                        adminController.deleteEmployee(admin.getID(), true);
                    } else {
                        // User clicked Cancel or closed the dialog
                        System.out.println("Deleting cancel");
                    }
                    clearAll();
                }
            } else {
                Staff staff = (Staff) adminController.FindStaffByID(Integer.parseInt(empIdFiled.getText()));
                if (staff == null) {
                    errorMsg.setVisible(true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Are you sure you want to delete: " + staff.getFirstName() +" with ID: " + staff.getID());
                    alert.setContentText("This action cannot be undone.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        // User clicked OK, delete admin
                        adminController.deleteEmployee(staff.getID(), false);
                    } else {
                        // User clicked Cancel or closed the dialog
                        System.out.println("Deleting cancel");
                    }
                    clearAll();
                }
            }
        } else {
            errorMsg.setText("Please Select the account type");
            errorMsg.setVisible(true);
        }
    }
    @FXML
    public void activeHandler(MouseEvent event) {
        RadioButton selectedRadioButton = (RadioButton) empType.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedText = selectedRadioButton.getText();
            if (selectedText.equals("Admin")){
                Admin admin = (Admin) adminController.FindAdminByID(Integer.parseInt(empIdFiled.getText()));
                if (admin == null) {
                    errorMsg.setVisible(true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    if (event.getSource().equals(deActiveBtn)){
                        alert.setHeaderText("Are you sure you want to ban admin: " + admin.getFirstName() +" with ID: " + admin.getID());
                        alert.setContentText("This action cannot be undone.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            // User clicked OK, delete admin
                            adminController.activeAdmin(admin.getID(), false);
                        }
                    } else if (event.getSource().equals(activeBtn)) {
                        alert.setHeaderText("Are you sure you want to active admin: " + admin.getFirstName() +" with ID: " + admin.getID());
                        alert.setContentText("This action cannot be undone.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            // User clicked OK, delete admin
                            adminController.activeAdmin(admin.getID(), true);
                        }
                    }
                    clearAll();
                }
            }
        } else {
            errorMsg.setText("Please Select the account type");
            errorMsg.setVisible(true);
        }
    }

}
