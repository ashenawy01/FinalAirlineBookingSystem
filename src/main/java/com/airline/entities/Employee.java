package com.airline.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Employee implements Serializable {
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdAT;
    private int createdByID;

    public Employee(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAT = LocalDateTime.now();
    }
    public Employee() {}

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public int getCreatedByID() {
        return createdByID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return ID == employee.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
