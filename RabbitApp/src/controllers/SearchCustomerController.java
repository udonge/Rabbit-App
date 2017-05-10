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
 * @author Ruaraidh
 */
public class SearchCustomerController implements Initializable {

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
    public void searchCustomerByFirstName(ActionEvent e) throws IOException
    {
        ArrayList<Customer> customers = setCustomers();
        ArrayList<Customer> outputCustomers = new ArrayList<Customer>();
        
        String searchTerm = searchText.getText().toLowerCase();
        
        if(searchTerm.length() > 0)
        {
            for(int i=0;i<customers.size();i++)
            {
                if (customers.get(i).getFirstName().toLowerCase().contains(searchTerm))
                {
                    outputCustomers.add(customers.get(i));
                }
            }
        }
        
        displayCustomerResults(outputCustomers);
    }
    
    @FXML
    public void searchCustomerByLastName(ActionEvent e) throws IOException
    {
        ArrayList<Customer> customers = setCustomers();
        ArrayList<Customer> outputCustomers = new ArrayList<Customer>();
        
        String searchTerm = searchText.getText().toLowerCase();
        
        if(searchTerm.length() > 0)
        {
            for(int i=0;i<customers.size();i++)
            {
                if (customers.get(i).getLastName().toLowerCase().contains(searchTerm))
                {
                    outputCustomers.add(customers.get(i));
                }
            }
        }
        
        displayCustomerResults(outputCustomers);
    }

    public void onClickReturn() throws IOException
    {
        Stage stage = (Stage) searchText.getScene().getWindow();
        rabbitfx.customerStage(stage);      
    }
    
    private ArrayList<Customer> setCustomers()
    {
        ArrayList<Customer> customers = new ArrayList<>();
        
        for(int i=0;i<session.users.size();i++)
        {
            if(session.users.get(i) instanceof Customer) customers.add((Customer)session.users.get(i));
        }
        
        return customers;
    }
    
    private void displayCustomerResults(ArrayList<Customer> customers)
    {
        listItems.setAll();

        for(int i=0;i<customers.size();i++)
        {
            listItems.add("Name: " + customers.get(i).getFirstName() + customers.get(i).getLastName());
        }
        resultsList.setItems(listItems);   
    }
}