/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitobjects;

/**
 *
 * @author Reisen
 */
public abstract class User {
    
    protected String id;
    protected String password;
    protected String email;
    protected String address;
    protected String contactNo;
    
    /* # Constructor for User class. */
    public User (String id, String password, String email, String address, String contactNo) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.address = address;
        this.contactNo = contactNo;
    }
    /* # Setter */
    public void setID(String id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    
    /* # Getter */
    public String getID() {
        return this.id;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public String getAddress() {
        return this.address;
    }
    public String getContactNo() {
        return this.contactNo;
    }    
    
}
