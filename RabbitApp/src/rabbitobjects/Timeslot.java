/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import javafx.scene.text.Text;
import rabbitmethods.Formatters;

/**
 *
 * @author Reisen
 * # A Timeslot is an object representing the time and employee presented for a 
 * # Business that a Customer may browse and book. */
public class Timeslot {
    
    private String host;                // Business Providing Service. [HOST]
    private String patron;              // Customer Receiving Service. [PATRON]
    private String employeeID;          // Employee of Business assigned to Service. [EMPLOYEE]
    private Date appointmentDate;       // Date of Service. [DATE]
    private Time appointmentTimeEnd;    // How long the appointment is expected to last. [DURATION]
    private Time appointmentTime;       // Time of Service.
    private String description;         // Business sets a short 24 character description of service. [DESCRIPTION]
       
    public Timeslot(
            String businessID,
            String customerID,
            String employeeID,
            Date appointmentDate,
            Time appointmentTimeEnd,
            Time appointmentTime,
            String description) {
        
        this.host = businessID;
        this.patron = customerID;
        this.employeeID = employeeID;
        this.appointmentDate = appointmentDate;
        this.appointmentTimeEnd = appointmentTimeEnd;
        this.appointmentTime = appointmentTime;
        this.description = description;
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
    public void setAppointmentTime(Time time) {
        this.appointmentTime = time;
    }
    public void setAppointmentTimeEnd(Time duration) {
        this.appointmentTimeEnd = duration;
    }
    public void setDescription(String desc) {
        this.description = desc;
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
    public Time getAppointmentTime() {
        return this.appointmentTime;
    }
    public Time getAppointmentTimeEnd() {
        return this.appointmentTimeEnd;
    }
    public Date getAppointmentDate() {
        return this.appointmentDate;
    }
    public String getDescription() {
        return this.description;
    }
    
 /* #########################################################################
  * #   Timeslot Methods                                                    #
    ######################################################################### */     
    
    public static void setTextToDateOfTimeslot(Text text, Timeslot timeslot) {
        SimpleDateFormat toDateDay = Formatters.formatDateToStringDDMMEEEE();
        Date date = timeslot.getAppointmentDate();
        String newText = toDateDay.format(date);
        text.setText(newText);
    }    
}
