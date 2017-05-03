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

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    public void setDriver(RabbitFX rabbitfx)
    {        
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session)
    {
        this.session = session;
        this.thisCustomer = (Customer) session.currentUser;
    }

    @FXML
    public void searchBusinessByName(ActionEvent e) throws IOException
    {
        ArrayList<Business> businesses = setBusinesses();
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        
        String searchTerm = searchText.getText().toLowerCase();
        
        if(searchTerm.length() > 0)
        {
            for(int i=0;i<businesses.size();i++)
            {
                if (businesses.get(i).getBusinessName().toLowerCase().contains(searchTerm))
                {
                    outputBusinesses.add(businesses.get(i));
                }
            }
        }
        
        displayResults(outputBusinesses);
    }

    @FXML
    private void searchBusinessByDesc() throws IOException
    {
        ArrayList<Business> businesses = setBusinesses();
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        
        String searchTerm = searchText.getText().toLowerCase();
        
        if(searchTerm.length() > 0)
        {
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

        for(int i=0;i<businesses.size();i++)
        {
            listItems.add("Name: " + businesses.get(i).getBusinessName() + "\nDescription: " + businesses.get(i).getBusinessDescription());
        }
        resultsList.setItems(listItems);   
    }
}