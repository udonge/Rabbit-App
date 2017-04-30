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
    
    private String eID;
    private int profilePicture;
    private String firstName;
    private String lastName;
    private String desc;
    private List<Timeslot> timeslots;
    
    public Employee(
            String eID,
            int profilePicture,
            String firstName,
            String lastName,
            String desc,
            List<Timeslot> timeslots) {
        
        this.eID = eID;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desc = desc;
        this.timeslots = timeslots;
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
    public void setEmployeeDesc(String desc) {
        this.desc = desc;
    }    
    public void setEmployeeTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
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
    public String getEmployeeDesc() {
        return this.desc;
    }
    public List<Timeslot> getEmployeeTimeslots() {
        return this.timeslots;
    }
    
}
