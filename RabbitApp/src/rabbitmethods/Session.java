/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmethods;

import com.sun.media.jfxmedia.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import rabbitapp.RabbitApp;
import rabbitobjects.Business;
import rabbitobjects.Customer;
import rabbitobjects.Employee;
import rabbitobjects.Timeslot;
import rabbitobjects.User;

/**
 *
 * @author Reisen
 * # User session with the application, login/logout/save/load/database population of List<User> of all users.
 */
public class Session 
{
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger( RabbitApp.class.getName() );
    public ArrayList<User> users;
    public User currentUser;
    public Connection connection;
    public String schema;
    
    public Session() {
        users = new ArrayList<>();
        currentUser = null;
        connection = null;
    }
    
    public void setConnection(Connection connection, String schema) throws SQLException {
        this.connection = connection;
        this.schema = schema;
    }
    /* # Methods */
    public boolean login(String email, String password) {
        /* # Query list of users. */
        for(User user : users) {
            if(user.getEmail().equals(email)) {
                if(user.getPassword().equals(password)) {
                    LOGGER.fine("Logged in " + user.getEmail());
                    currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }   
    public void logout() {
        /* # Set currentUser to null*/
        LOGGER.fine("Logged out " + currentUser.getEmail());
        currentUser = null;
    }
    
 /* #########################################################################
  * #   Load / Read from Database                                           #
    ######################################################################### */   
    
    public void readFromDatabase() {
        /* # Populate the User list with data stored in Database. 
         * # I have chosen to utilise the 'Table per Subclass' approach in modelling data. 
         * # This means heavier coding in reading and writing but the database will be more flexible. */             
        /* # Get all User IDs from database into List. */
        String command = "SELECT * FROM " + schema + ".USERS";
        LOGGER.finer("SQL Request to database: " + command);
        List<String> listOfID = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();            
            ResultSet rsUser = statement.executeQuery(command);
            while(rsUser.next()) {
                /* # Get common data first. */
                String id = rsUser.getString("ID");
                listOfID.add(id);
            }
        } catch(SQLException error) {
            LOGGER.severe("SQL error when attempting read: " + error.getMessage());
        }
        
        listOfID.forEach((index) -> {
            /* # Look at the first part of the ID to determine usertype. */
            constructBasedOnUserType(index);
        });        
    }
    
    public void constructBasedOnUserType(String userID) {
        char[] reader = userID.toCharArray();
        User user = null;
        switch(reader[0]) {
            case 'B':
            case 'b':
                /* # Business type. */
                user = constructBusinessFromDatabase(userID);
                break;
            case 'C':
            case 'c':
                /* # Customer type.*/
                user = constructCustomerFromDatabase(userID);
                break;
            default:
                System.out.println("Unknown User Type");
                return;
        }
        /* # We pull user data last because it cannot be instantiated. */
        getCommonUserData(userID, user);
    }
    public void getCommonUserData(String userID, User user) {
        String command = ("SELECT * FROM " + schema + ".USERS WHERE ID = '" + userID + "'");
        try {

            Statement statement = connection.createStatement();  
            ResultSet rsUser = statement.executeQuery(command);
            while(rsUser.next()) {
                String email = rsUser.getString("EMAIL");
                String password = rsUser.getString("PASSWORD");
                String contactNo = rsUser.getString("CONTACTNO");
                String address = rsUser.getString("ADDRESS");
                
                if(user instanceof Customer) {
                    user.setID(userID);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setContactNo(contactNo);
                    user.setAddress(address);
                }

                if(user instanceof Business) {
                    user.setID(userID);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setContactNo(contactNo);
                    user.setAddress(address);
                }                   
            }
            users.add(user);
                    
        }catch (SQLException error) {
            System.out.println(error.getMessage());
        }        
    }
    
    public Customer constructCustomerFromDatabase(String userID) {
        /* # Build Customer Data, note we must repeat for Bookings subtable. 
         * # State query command using user ID. */
        String command = ("SELECT * FROM " + schema + ".CUSTOMERS WHERE ID = '" + userID +"'");
        Customer customer = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rsCustomer = statement.executeQuery(command);
            while(rsCustomer.next()) {
            String firstName = rsCustomer.getString("FIRSTNAME");
            String lastName = rsCustomer.getString("LASTNAME");
            Date dob = rsCustomer.getDate("DATEOFBIRTH");
            /* # We build the object with our current information. */
            customer = new Customer(null,null,null,null,null, firstName, lastName, dob, null);
            }
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
        /* # The Bookings list should be populated here. */        

        return customer;
    }
    
    public List<Timeslot> constructCustomerBookings() {
        return null;
    }
    
    public Business constructBusinessFromDatabase(String userID) {
        /* # Build Business Data, note we must repeat for Employees and Timeslots */
        String command = ("SELECT * FROM " + schema + ".BUSINESS WHERE ID = '" + userID +"'");
        Business business = null;
        List<Employee> employees = new ArrayList<>();
        boolean[] daysOpen = new boolean[7];
        try {
            Statement statement = connection.createStatement();
            ResultSet rsBusiness = statement.executeQuery(command);
            while(rsBusiness.next()) {
                String businessName = rsBusiness.getString("BUSINESSNAME");
                String ownerFName = rsBusiness.getString("FIRSTNAME");
                String ownerLName = rsBusiness.getString("LASTNAME");
                Time openTime = rsBusiness.getTime("OPENTIME");
                Time closeTime = rsBusiness.getTime("CLOSETIME");
                String desc = rsBusiness.getString("DESCRIPTION");
                String openDays = rsBusiness.getString("OPENDAYS");
                Boolean visibility = rsBusiness.getBoolean("VISIBILITY");
                /* # Given this information, we build a Business */
                /* # Construct Days Open Array */
                constructDaysOpenArray(daysOpen, openDays);
                business = new Business(null,null,null,null,null,businessName,ownerFName,ownerLName,null,openTime,closeTime,desc,daysOpen,visibility); 
                business.setListOfEmployees(employees);
                          
            }
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
        /* # Construct Employee List */
        constructListOfEmployees(employees, userID);
        return business;
    }
    
    public void constructListOfEmployees(List<Employee> list, String userID) {
        String command = ("SELECT * FROM " + schema + ".EMPLOYEE WHERE BID = '" + userID + "'");
        Employee employee = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rsEmployee = statement.executeQuery(command);
            while(rsEmployee.next()) {
                List<Timeslot> timeslots = new ArrayList<>();
                String eID = rsEmployee.getString("EID");
                int profileID = rsEmployee.getInt("PROFILE");
                String fname = rsEmployee.getString("FIRSTNAME");
                String lname = rsEmployee.getString("LASTNAME");
                String desc = rsEmployee.getString("DESCRIPTION");
                
                constructTimeslotsOfEmployee(eID, timeslots);
                
                employee = new Employee(eID, profileID, fname, lname, desc, timeslots);
                list.add(employee);
            }
            
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
    }
    
    public void constructTimeslotsOfEmployee(String EID, List<Timeslot> list) {
        String command = ("SELECT * FROM " + schema + ".TIMESLOT  WHERE EID = '" + EID + "'");
        try {
            Statement statement = connection.createStatement();
            ResultSet rsTimeslot = statement.executeQuery(command);
            while(rsTimeslot.next()) {
                String host = rsTimeslot.getString("HOST");
                String patron = rsTimeslot.getString("PATRON");
                Date date = rsTimeslot.getDate("DATE");
                String desc = rsTimeslot.getString("DESCRIPTION");
                Time start = rsTimeslot.getTime("STARTTIME");
                Time end = rsTimeslot.getTime("ENDTIME");
                Timeslot timeslot = new Timeslot(host, patron, EID, date, start, end, desc);
                list.add(timeslot);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
    
    public void constructDaysOpenArray(boolean[] days, String dbValue) {
        char[] decipher = Validation.turnStringToCharArray(dbValue);
        /* # 0 = Sunday, 6 = Saturday */
        for(char i : decipher) {
            int index = Character.getNumericValue(i);
            if(index>=0 && index <= 6) {
                days[index] = true;
            } else {
                System.out.println(index);
                System.out.println("Error parsing day string, check Database. ");
            }
        }
    }
    
 /* #########################################################################
  * #   Save/Add to Database                                                #
    ######################################################################### */    
    
    public void saveToDatabase(User user) {
        /* # Given a user, we save their details to the database through an insert command. */      
        
        String userCommand = "INSERT INTO " + schema + ".USERS " + "VALUES (?,?,?,?,?)";
        
        String contactNo = null;
        String address = null;
        if(user.getContactNo()!=null)
            contactNo = user.getContactNo();
        if(user.getAddress()!=null)
            address = user.getAddress();
                
        try {
            PreparedStatement statement = connection.prepareStatement(userCommand);
            statement.setString(1, user.getID());
            statement.setString(2, user.getEmail());            
            statement.setString(3, user.getPassword());
            statement.setString(4, contactNo);
            statement.setString(5, address);
            statement.executeUpdate();            
        }catch(SQLException error) {
            System.out.println(error.getMessage());
        }
        
        if(user instanceof Customer) {
            saveCustomerToDatabase((Customer) user);
        }
        if(user instanceof Business) {
            saveBusinessToDatabase((Business) user);
        }        
    }
    
    public void saveCustomerToDatabase(Customer customer) {    
        String userCommand = "INSERT INTO " + schema + ".CUSTOMERS " + "VALUES (?, ?, ?, ?)";
        Date date = null;
        if(customer.getDateOfBirth()!=null) {
            System.out.println("There is a date.");
            date = customer.getDateOfBirth();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(userCommand);
            statement.setString(1, customer.getID());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());        
            statement.setDate(4, date);
            statement.executeUpdate();
            
        }catch(SQLException error) {
            System.out.println(error.getMessage());
        }
        
        /* # Save Bookings Here.*/
    }
    
    public void saveBusinessToDatabase(Business business) {
        String userCommand = "INSERT INTO " + schema + ".BUSINESS " + "VALUES (?,?,?,?,?,?,?,?,?)";
        Time openTime = null;
        Time closeTime = null;
        String dayStringCode = convertBooleanArrayToDayCode(business.getDaysOpen());
        if(business.getOpeningHours()!=null) {
            openTime = business.getOpeningHours();
        }
        if(business.getClosingHours()!=null) {
            closeTime = business.getClosingHours();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(userCommand);
            statement.setString(1, business.getID());
            statement.setString(2, business.getBusinessName());
            statement.setTime(3, openTime);
            statement.setTime(4, closeTime);
            statement.setBoolean(5, false);
            statement.setString(6, business.getBusinessOwnerFirstName());
            statement.setString(7, business.getBusinessOwnerLastName());
            statement.setString(8, business.getBusinessDescription());
            statement.setString(9, dayStringCode);
            statement.executeUpdate();            
        }catch(SQLException error) {
            System.out.println(error.getMessage());
        }        
    }
    
    public void saveEmployeeToDatabase(Employee employee, Business business) {
        String userCommand = "INSERT INTO " + schema + ".EMPLOYEE " + "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(userCommand);
            statement.setString(1, employee.getEID());
            statement.setString(2, business.getID());
            statement.setString(3, employee.getEmployeeFirstName());
            statement.setString(4, employee.getEmployeeLastName());
            statement.setString(5, employee.getEmployeeDesc());
            statement.setInt(6, employee.getProfilePicture());
            
            statement.executeUpdate();
            
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }       
    }
    
    public void saveTimeslotToDatabase(Timeslot timeslot) {
        String userCommand = "INSERT INTO " + schema + ".TIMESLOT " + "VALUES (?,?,?,?,?,?,?)";
        String patron = null;
        if(timeslot.getPatron()!=null) {
            patron = timeslot.getPatron();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(userCommand);
            statement.setString(1, timeslot.getHost());
            statement.setString(2, patron);
            statement.setString(3, timeslot.getEmployeeID());
            statement.setDate(4, timeslot.getAppointmentDate());
            statement.setString(5, timeslot.getDescription());
            statement.setTime(6, timeslot.getAppointmentTime());
            statement.setTime(7, timeslot.getAppointmentTimeEnd());
            
            statement.executeUpdate();
        } catch ( SQLException error ) {
            System.out.println(error.getMessage());
        }
    }
    
    public String convertBooleanArrayToDayCode(boolean[] days) {
        String dayCode = "";
        for(int i = 0 ; i < days.length ; i++) {
            if(days[i]) {
                dayCode = dayCode + i;
            }
        }
        System.out.println("Complete Day Code: " + dayCode);
        return dayCode;
    }
 /* #########################################################################
  * #   Update Database                                                     #
    ######################################################################### */     
    
    public void updateUser(User user) {
        /* # Update the details in database using a Dummy User. */
        String updateCommand = 
                "UPDATE " + schema + ".USERS SET " +
                "EMAIL = ?, " +
                "CONTACTNO = ?, " +
                "ADDRESS = ? " +
                "WHERE ID = '" + user.getID() + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(updateCommand);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getContactNo());
            statement.setString(3, user.getAddress());
            statement.executeUpdate();
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
        if(user instanceof Customer) {
            updateCustomerUser((Customer) user);
        }
        if(user instanceof Business) {
            updateBusinessUser((Business) user);
        }                
        
    } // End updateUser
    
    public void updateCustomerUser(Customer user) {
        String updateCommand = 
                "UPDATE " + schema + ".CUSTOMERS SET " +
                "FIRSTNAME = ?," +
                "LASTNAME = ?, " +
                "DATEOFBIRTH = ? " +
                "WHERE ID = '" + user.getID() + "'";
        
        try {
            PreparedStatement statement = connection.prepareStatement(updateCommand);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, user.getDateOfBirth());
            statement.executeUpdate();
            
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
    
    public void updateBusinessUser(Business user) {
        String updateCommand = 
                "UPDATE " + schema + ".BUSINESS SET " +
                "BUSINESSNAME = ?, " +
                "OPENTIME = ?, " +
                "CLOSETIME = ?, " +
                "VISIBILITY = ?, " +
                "FIRSTNAME = ?, " +
                "LASTNAME = ?, " +
                "DESCRIPTION = ?, " +
                "OPENDAYS = ? " +
                "WHERE ID = '" + user.getID() + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(updateCommand);
            statement.setString(1, user.getBusinessName());
            statement.setTime(2, user.getOpeningHours());
            statement.setTime(3, user.getClosingHours());
            statement.setBoolean(4, user.getVisibilityToPublic());
            statement.setString(5, user.getBusinessOwnerFirstName());
            statement.setString(6, user.getBusinessOwnerLastName());
            statement.setString(7, user.getBusinessDescription());
            statement.setString(8, convertBooleanArrayToDayCode(user.getDaysOpen()));
            statement.executeUpdate();
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }        
    }
    
    public void updateEmployee(Employee employee) {
        String updateCommand = 
                "UPDATE " + schema + ".EMPLOYEE SET " +
                "FIRSTNAME = ?, " +
                "LASTNAME = ?, " +
                "DESCRIPTION = ?, " +
                "PROFILE = ? " +
                "WHERE EID = '" + employee.getEID() + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(updateCommand);
            statement.setString(1, employee.getEmployeeFirstName());
            statement.setString(2, employee.getEmployeeLastName());
            statement.setString(3, employee.getEmployeeDesc());
            statement.setInt(4, employee.getProfilePicture());
            statement.executeUpdate();
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
    }
    
 /* #########################################################################
  * #   Remove From Database                                                #
    ######################################################################### */    
    
    public void removeTimeslotsOfEmployeeFromDatabase(Employee employee) {
        /* # Just remove the whole thing for now... too troublesome for individuals. */
        String removeCommand = 
                "DELETE FROM " + schema + ".TIMESLOT " +
                "WHERE EID = '" + employee.getEID() + "'";
        try {
            Statement statement = connection.createStatement();
            statement.execute(removeCommand);
        } catch(SQLException error) {
            System.out.println(error.getMessage());
        }
    }
    
    public String generateID(String type) {
        /* # Build an ID with inbuilt check for repeats. */
        String id = null;
        do{
            id = type;
            String generate = UUID.randomUUID().toString();
            generate = generate.substring(1, 10);
            id = id + generate;  
        } while(idAlreadyExists(id));

        
        return id;
    }
    
    /* # Existence Check Methods */
    public boolean idAlreadyExists(String id) {
        return users.stream().filter((user) -> (user!=null)).anyMatch((user) -> (user.getID().equals(id)));
    }
    public boolean emailAlreadyExists(String email) {
        return users.stream().filter((user) -> (user!=null)).anyMatch((user) -> (user.getEmail().equals(email)));
    }
    
}
