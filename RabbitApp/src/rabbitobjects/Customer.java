/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;


import java.sql.Date;
import java.util.List;

/**
 * @author Reisen
 * # Customer is a subclass of abstract User class, so it will inherit fields from User.
 */
public class Customer extends User {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    /* # Customers will have a list of Bookings */
    private List<Timeslot> bookings;
    
    public Customer
        (   String id, 
            String password, 
            String email,
            String address,
            String contactNo, 
            String firstName, 
            String lastName, 
            Date dateOfBirth,
            List<Timeslot> bookings) {
            
        super(id, password, email, address, contactNo);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bookings = bookings;
    }
    /* # Setters */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setListOfBookings(List<Timeslot> bookings) {
        this.bookings = bookings;
    }
    /* # Getters */
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    public List<Timeslot> getListOfBookings() {
        return this.bookings;
    }
    /* # To String */
    @Override
    public String toString() {
        String customer = this.getID();
        return customer;
    }
    
}
