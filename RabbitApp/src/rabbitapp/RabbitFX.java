/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitapp;

import controllers.*;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 * # Main Driver Class, holds the session information and 
 * # Loading GUIS and assigning controller classes occurs here.
 */
public class RabbitFX extends RabbitApp {
    private static final Logger LOGGER = Logger.getLogger(RabbitApp.class.getName());
    public int resWidth = 1280;
    public int resHeight = 800;
    Session session;
    
    private final String sceneTitle = ("Rabbit! - Booking Application");
    
    public void setSession(Session session) {
        this.session = session;    
    }
    
    public void showScene(Stage stage, Parent root) {
        Scene scene = new Scene(root, resWidth, resHeight); 
        scene.setRoot(root);
        scene.getStylesheets().add(RabbitApp.class.getResource("/GUI/fxml/RabbitFX.css").toExternalForm());
        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();            
    }
    
    /* # A driver method has a parameter of Stage, passed in by top layer. Typically works like this: 
     * #    1. Declare the loader: 
     * #        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/<NAME OF SCENE>.fxml")); 
     * #        It is done this way so that we can call constructors in the loader
     * #            <SCENE CONTROLLER> controller = loader.getController();
     * #            controller.setSession(session);
     * #            controller.setDriver(this);
     * #        and pass the RabbitFX driver and Session.
     * #        This means the database is loaded at start up only.
     * #    2. Call any methods you need to initialise the Controller. 
     * #    3. Call showScene(<Stage>, <The root you made in method>). */
    
    public void loginStage(Stage stage) throws IOException {
        /* # Display the Login scene. */
        LOGGER.fine("Loading Login stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        
        showScene(stage, root);
        LOGGER.fine("Loaded Login stage");
    }
    
    public void registrationStage(Stage stage) throws IOException {
        /* # Display the Registration scene. */
        LOGGER.fine("Loading Registration stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/registration.fxml"));
        Parent root = loader.load();
        RegistrationController controller = loader.getController();
        
        controller.setDriver(this);
        controller.setSession(session);
        
        showScene(stage, root);
        LOGGER.fine("Loaded Registration stage");
    }
    
    public void customerStage(Stage stage) throws IOException {
        /* # Display the Customer Menu scene. */    
        LOGGER.fine("Loading Customer Menu stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/customer.fxml"));
        Parent root = loader.load();
        CustomerMainMenuController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setCustomerMenu();
   
        showScene(stage,root);
        LOGGER.fine("Loaded Customer Menu stage");
    }
    
    public void businessStage(Stage stage) throws IOException {
        /* # Display the Business Menu scene. */
        LOGGER.fine("Loading Business Menu stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/business.fxml"));
        Parent root = loader.load();
        BusinessMainMenuController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setBusinessMenu();
        controller.disableTimeslotsIfNoEmployees();
        
        showScene(stage, root);
        LOGGER.fine("Loaded Business Menu stage");
    }
    
    public void viewProfileCustomer(Stage stage) throws IOException {
        /* # Display Profile for Currently logged in customer. */
        LOGGER.fine("Loading Customer Profile stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/ViewProfile_Customer.fxml"));
        Parent root = loader.load();
        CustomerViewProfileController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.getProfileInformation();
        
        showScene(stage, root); 
        LOGGER.fine("Loaded Customer Progile stage");
    }
    
    public void viewProfileBusiness(Stage stage) throws IOException {
        /* # Display Profile for Currently logged in business. */
        LOGGER.fine("Loading Business Profile stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/ViewProfile_Business.fxml"));
        Parent root = loader.load();
        BusinessViewProfileController controller = loader.getController();
       
        controller.setSession(session);
        controller.setDriver(this);
        controller.getBusinessProfileInformation();
        controller.setChoiceBox();
        controller.toggleEditObjectVisibility();
        
        showScene(stage, root);   
        LOGGER.fine("Loaded Business Profile stage");
        
    }
    
    public void viewBusinessManageTimeslot(Stage stage) throws IOException {
        /* # Display menu for managing timeslots for logged in business. */
        LOGGER.fine("Loading Timeslot Management stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/Timeslots.fxml"));
        Parent root = loader.load();
        BusinessManageTimeslotController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setTimeslotList();
        controller.setPossibleDays();
        controller.setProfilePictureChoices();
        controller.setEmployeeList();
        
        showScene(stage, root); 
        LOGGER.fine("Loaded Timeslot Management stage");
    }
    
    public void viewBusinessManageEmployee(Stage stage) throws IOException {
        /* # Display menu for managing employees for currently logged in business. */
        LOGGER.fine("Loading Employee Management stage...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/Business_ManageEmployees.fxml"));
        Parent root = loader.load();
        BusinessManageEmployeeController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        
        controller.setEditProfileIconChoiceBox();        
        controller.setEmployeeChoiceBox();

        showScene(stage, root);   
        LOGGER.fine("Loaded Employee Management stage");
    }
    
    public void makeBookingStage(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/SearchBusinesses.fxml"));
        Parent root = loader.load();
        SearchBusinessesController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        
        showScene(stage,root);
    }
    
}
