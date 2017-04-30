/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import rabbitobjects.*;

/**
 * FXML Controller class
 *
 * @author Harry
 */
public class SearchBusinessesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField searchText;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private ArrayList<Business> searchBusinessByName(InputMethodEvent event)
    {
        ArrayList<Business> businesses = new ArrayList<Business>();
        
        String searchTerm = searchText.getText();
        String output = "Not found";
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        for(int i=0;i<businesses.size();i++)
        {
            if (businesses.get(i).getBusinessName().contains(searchTerm))
            {
                outputBusinesses.add(businesses.get(i));
                break;
            }
        }
        return outputBusinesses;
    }

    @FXML
    private ArrayList<Business> searchBusinessByDesc(ActionEvent event)
    {
        ArrayList<Business> businesses = new ArrayList<Business>();

        String searchTerm = searchText.getText();
        ArrayList<Business> outputBusinesses = new ArrayList<Business>();
        for(int i=0;i<businesses.size();i++)
        {
            if (businesses.get(i).getBusinessDescription().contains(searchTerm))
            {
                outputBusinesses.add(businesses.get(i));
                break;
            }
        }
        return outputBusinesses;
    }
    
}
