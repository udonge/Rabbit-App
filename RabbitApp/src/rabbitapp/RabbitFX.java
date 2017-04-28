/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitapp;

import controllers.BusinessMainMenuController;
import controllers.BusinessManageEmployeeController;
import controllers.BusinessManageTimeslotController;
import controllers.BusinessViewProfileController;
import controllers.CustomerMainMenuController;
import controllers.LoginController;
import controllers.RegistrationController;
import java.io.IOException;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        
        showScene(stage, root);
    }
    
    public void registrationStage(Stage stage) throws IOException {
        /* # Display the Registration scene. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/registration.fxml"));
        Parent root = loader.load();
        RegistrationController controller = loader.getController();
        
        controller.setDriver(this);
        controller.setSession(session);
        
        showScene(stage, root);
    }
    
    public void customerStage(Stage stage) throws IOException {
        /* # Display the Customer Menu scene. */    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/customer.fxml"));
        Parent root = loader.load();
        CustomerMainMenuController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setCustomerMenu();
   
        showScene(stage,root);
    }
    
    public void businessStage(Stage stage) throws IOException {
        /* # Display the Business Menu scene. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/business.fxml"));
        Parent root = loader.load();
        BusinessMainMenuController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setBusinessMenu();
        
        showScene(stage, root);
    }
    
    public void viewProfileCustomer(Stage stage) throws IOException {
        
    }
    
    public void viewProfileBusiness(Stage stage) throws IOException {
        /* # Display Profile for Currently logged in business. */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/ViewProfile_Business.fxml"));
        Parent root = loader.load();
        BusinessViewProfileController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.getProfileInformation();
        controller.setChoiceBox();
        
        showScene(stage, root);        
        
    }
    
    public void viewBusinessManageTimeslot(Stage stage) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/Timeslots.fxml"));
        Parent root = loader.load();
        BusinessManageTimeslotController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setTimeslotList();
        
        showScene(stage, root);           
    }
    
    public void viewBusinessManageEmployee(Stage stage) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/Manage_Employees.fxml"));
        Parent root = loader.load();
        BusinessManageEmployeeController controller = loader.getController();
        
        controller.setSession(session);
        controller.setDriver(this);
        controller.setProfilePictureChoices();
        controller.hideEditFields();
        controller.setTextFieldList();
        controller.setEmployeeChoices();
        
        showScene(stage, root);            
    }
    
}
