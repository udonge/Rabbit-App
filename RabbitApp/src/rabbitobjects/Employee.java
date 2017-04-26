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
    
    protected final String eID;
    protected String firstName;
    protected String lastName;
    protected List<Timeslot> timeslots;
    
    public Employee(
            String eID,
            String firstName,
            String lastName,
            List<Timeslot> timeslots) {
        
        this.eID = eID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeslots = timeslots;       
    }
    /* # Setter */
    public void setEmployeeFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setEmployeeLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmployeeTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
    
    /* # Getter */
    public String getEID() {
        return this.eID;
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
}
