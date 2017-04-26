/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

import java.util.Date;

/**
 *
 * @author Reisen
 * # A Timeslot is an object representing the time and employee presented for a 
 * # Business that a Customer may browse and book. */
public class Timeslot {
    
    private String businessID;       // Business Providing Service.
    private String customerID;       // Customer Receiving Service.
    private int employeeID;          // Employee of Business assigned to Service.
    private Date appointmentDate;    // Date and Time of Service.
       
    public Timeslot(
            String businessID,
            String customerID,
            int employeeID,
            Date appointmentDate) {
        this.businessID = businessID;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.appointmentDate = appointmentDate;          
    }
    /* # Setter */
    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public void setAppointmentDate(Date date) {
        this.appointmentDate = date;
    }
    /* # Getter */
    public String getBusinessID() {
        return this.businessID;
    }
    public String getCustomerID() {
        return this.customerID;
    }
    public int getEmployeeID() {
        return this.employeeID;
    }
    public Date getAppointmentDate() {
        return this.appointmentDate;
    }
}
