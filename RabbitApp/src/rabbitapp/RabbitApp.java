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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 * # Topmost level for application functioning.
 */
public class RabbitApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(RabbitApp.class.getName());
    @Override
    public void start(Stage primaryStage) throws IOException {
        /* # Initialises the loggers settings. */
        initLogger();
        LOGGER.fine("Application launching...");
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
           
        } catch(SQLException noDB) 
        {
           LOGGER.severe("Issue creating connection: " + noDB.getMessage());
        }

        /* # Upon start up, load session then present Login screen */
        rabbitfx.setSession(session);
        rabbitfx.loginStage(primaryStage);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void initLogger() throws IOException
     {
         Handler logOutput = new FileHandler("AppLogs.txt");
         logOutput.setFormatter(new SimpleFormatter());
         LOGGER.addHandler(logOutput);
         LOGGER.setLevel(Level.FINEST);
         LOGGER.fine("Logger initialised.");
     }
    
}
