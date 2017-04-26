/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

import java.util.List;

/**
 *
 * @author Reisen
 * # Employees are members of a Business roster, they are the ones assigned to a Timeslot object.
 */
public class Employee {
    
    public String eID;
    public int profilePicture;
    public String firstName;
    public String lastName;
    public List<Timeslot> timeslots;
    public Boolean[] daysWorking;
    
    public Employee(
            String eID,
            int profilePicture,
            String firstName,
            String lastName,
            List<Timeslot> timeslots,
            Boolean[] daysWorking) {
        
        this.eID = eID;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeslots = timeslots;
        this.daysWorking = daysWorking;
    }
    /* # Setter */
    public void setEmployeeFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setEmployeeProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setEmployeeLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmployeeTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
    public void setDaysWorking(Boolean[] daysWorking) {
        this.daysWorking = daysWorking;
    }
    
    /* # Getter */
    public String getEID() {
        return this.eID;
    }
    public int getProfilePicture() {
        return this.profilePicture;
    }
    public String getEmployeeFirstName() {
        return this.firstName;
    }
    public String getEmployeeLastName() {
        return this.lastName;
    }
    public List<Timeslot> getEmployeeTimeslots() {
        return this.timeslots;
    }
    public Boolean[] getDaysWorking() {
        return this.daysWorking;
    }
}
