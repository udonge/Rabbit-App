/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

import java.sql.Time;
import java.util.List;

/**
 *
 * @author Reisen
 * # Business is a type of user that offers time slots for Customers to book appointments in.
 * # Is expected to set opening hours and closing hours for themselves as well as have a list of employees.
 */
public class Business extends User {
    
    protected String businessName;
    protected String ownerFirstName;
    protected String ownerLastName;
    protected List<Employee> employees;
    protected Time openingHours;
    protected Time closingHours;
    protected String desc;
    boolean openToPublic;
    
    public Business
        (   String id, 
            String password, 
            String email,
            String address,
            String contactNo,
            String businessName,
            String firstName,
            String lastName,
            List<Employee> employees,
            Time openingHours,
            Time closingHours,
            String desc,
            boolean openToPublic) {
            
            super(id, password, email, address, contactNo);
            this.businessName = businessName;
            this.ownerFirstName = firstName;
            this.ownerLastName = lastName;
            this.employees = employees;
            this.openingHours = openingHours;
            this.closingHours = closingHours;
            this.desc = desc;
            this.openToPublic = false;
    }
    /* # Setters */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public void setBusinessOwnerFirstName(String firstName) {
        this.ownerFirstName = firstName;
    }
    public void setBusinessOwnerLastName(String lastName) {
        this.ownerLastName = lastName;
    }
    public void setListOfEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    public void setOpeningHours(Time openingHours) {
        this.openingHours = openingHours;
    }
    public void setClosingHours(Time closingHours) {
        this.closingHours = closingHours;
    }
    public void setBusinessDescription(String desc) {
        this.desc = desc;
    }
    public void toggleVisibilityToPublic() {
        this.openToPublic = !this.openToPublic;
    }
        
    /* # Getters */
    public String getBusinessName() {
        return this.businessName;
    }
    public String getBusinessOwnerFirstName() {
        return this.ownerFirstName;
    }
    public String getBusinessOwnerLastName() {
        return this.ownerLastName;
    }
    public List<Employee> getListOfEmployees() {
        return this.employees;
    }
    public Time getOpeningHours() {
        return this.openingHours;
    }
    public Time getClosingHours() {
        return this.closingHours;
    }
    public String getBusinessDescription() {
        return this.desc;
    }
    public boolean getVisibilityToPublic() {
        return this.openToPublic;
    }
    @Override
    public String toString() {
        String customer = this.getID();
        return customer;
    }
}
