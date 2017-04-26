/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Reisen
 * # A Timeslot is an object representing the time and employee presented for a 
 * # Business that a Customer may browse and book. */
public class Timeslot {
    
    private String host;                // Business Providing Service. [HOST]
    private String patron;              // Customer Receiving Service. [PATRON]
    private String employeeID;          // Employee of Business assigned to Service. [EMPLOYEE]
    private Date appointmentDate;       // Date and Time of Service. [DATE]
    private Time duration;              // How long the appointment is expected to last. [DURATION]
       
    public Timeslot(
            String businessID,
            String customerID,
            String employeeID,
            Date appointmentDate) {
        this.host = businessID;
        this.patron = customerID;
        this.employeeID = employeeID;
        this.appointmentDate = appointmentDate;          
    }
    /* # Setter */
    public void setHost(String host) {
        this.host = host;
    }
    public void setPatron(String patron) {
        this.patron = patron;
    }
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public void setAppointmentDate(Date date) {
        this.appointmentDate = date;
    }
    /* # Getter */
    public String getHost() {
        return this.host;
    }
    public String getPatron() {
        return this.patron;
    }
    public String getEmployeeID() {
        return this.employeeID;
    }
    public Date getAppointmentDate() {
        return this.appointmentDate;
    }
}
