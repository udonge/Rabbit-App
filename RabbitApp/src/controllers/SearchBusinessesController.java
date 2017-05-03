/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitobjects.*;

/**
 * FXML Controller class
 *
 * @author Harry
 */
public class SearchBusinessesController implements Initializable {

    RabbitFX rabbitfx;
    Session session;
    Customer thisCustomer;

    
    @FXML 
    private TextField searchText;
    @FXML
    private Button searchByName, searchByDesc, returnButton;
    @FXML
    private ListView<String> resultsList;
    
      final ObservableList<String> listItems = FXCollections.observableArrayList();        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("TEST: Initialise");
                
        ObservableList<String> data = FXCollections.observableArrayList();
        System.out.println("INITIALISE TEST: " + resultsList.getItems());
    }    
    
    public void setDriver(RabbitFX rabbitfx) {
        System.out.println("TEST: setDrive");
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        System.out.println("TEST: setSession");
        this.session = session;
        /* # Cast Business User. */
        this.thisCustomer = (Customer) session.currentUser;
    }

    @FXML
    public void searchBusinessByName(ActionEvent e) throws IOException
    {
        System.out.println("TEST: name");
        ArrayList<Business> businesses = setBusinesses();
        String searchTerm = searchText.getText().toLowerCase();
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        
        for(int i=0;i<businesses.size();i++)
        {
            if (businesses.get(i).getBusinessName().toLowerCase().contains(searchTerm))
            {
                outputBusinesses.add(businesses.get(i));
            }
        }
        
        displayResults(outputBusinesses);
    }

    @FXML
    private void searchBusinessByDesc() throws IOException
    {
        System.out.println("TEST: description");
        ArrayList<Business> businesses = setBusinesses();
        String searchTerm = searchText.getText().toLowerCase();
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        
        for(int i=0;i<businesses.size();i++)
        {
            if(businesses.get(i).getBusinessDescription() != null)
            {
                if (businesses.get(i).getBusinessDescription().toLowerCase().contains(searchTerm))
                {
                    outputBusinesses.add(businesses.get(i));
                }
            }
        }
        
        displayResults(outputBusinesses);
    }
    
    public void onClickReturn() throws IOException
    {
        Stage stage = (Stage) searchText.getScene().getWindow();
        rabbitfx.customerStage(stage);      
    }
    
    private ArrayList<Business> setBusinesses()
    {
        System.out.println("TEST: setBusinesses");
        ArrayList<Business> businesses = new ArrayList<>();
        for(int i=0;i<session.users.size();i++)
        {
            if(session.users.get(i) instanceof Business) businesses.add((Business)session.users.get(i));
        }
        return businesses;
    }
    
    private void displayResults(ArrayList<Business> businesses)
    {
        listItems.setAll();
        System.out.println("TEST: displayResults");
        for(int i=0;i<businesses.size();i++)
        {
            System.out.println("Name: " + businesses.get(i).getBusinessName() + '\n' + "Description: " + businesses.get(i).getBusinessDescription());
            System.out.println("-------------------");
            listItems.add(businesses.get(i).getBusinessName() + '\n' + businesses.get(i).getBusinessDescription() + '\n' + "----");
        }
        resultsList.setItems(listItems);
        
        System.out.println("TEST: Results: " + resultsList.getItems().toString());
    }
    
    private void displayAllBus()
    {
        ArrayList<Business> businesses = new ArrayList<Business>();
        for(int i=0;i<session.users.size();i++)
        {
            if(session.users.get(i) instanceof Business) businesses.add((Business)session.users.get(i));
        }
        
        for(int i=0;i<businesses.size();i++)
        {
            System.out.println("Name: " + businesses.get(i).getBusinessName() + '\n' + "Description: " + businesses.get(i).getBusinessDescription());
            System.out.println("-------------------");
        }
    }
}