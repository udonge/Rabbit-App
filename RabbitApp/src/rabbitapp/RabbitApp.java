/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import rabbitmethods.Session;
import rabbitobjects.Business;
import rabbitobjects.Customer;

/**
 *
 * @author Reisen
 * # Topmost level for application functioning.
 */
public class RabbitApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        /* # Declare class where menus will be constructed. */
        RabbitFX rabbitfx = new RabbitFX();
        /* # Declare a new session. */
        Session session = new Session();
        try {
           /* # Database Loading. */
           Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Rabbit! - DB", "rabbit_admin", "rabbit");
           /* # Set the schema that this program will read from for Session. */
           String schema = "RABBIT_DB";
           session.setConnection(connection, schema);
           session.readFromDatabase();
           
        } catch(SQLException noDB) {
           System.out.println(noDB.getMessage());
        }

        /* # Upon start up, load session then present Login screen */
        rabbitfx.setSession(session);
        rabbitfx.viewBusinessManageTimeslot(primaryStage);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
