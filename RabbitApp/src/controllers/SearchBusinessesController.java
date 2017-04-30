/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import rabbitobjects.Business;

/**
 * FXML Controller class
 *
 * @author Harry
 */
public class SearchBusinessesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        public String searchBusinessByName(String searchTerm, ArrayList<Business> businesses)
    {
        String output = "Not found";
        for(int i=0;i<businesses.size();i++)
        {
            if (businesses.get(i).getBusinessName().contains(searchTerm))
            {
                output = businesses.get(i).getID();
                break;
            }
        }
        return output;
    }
    
    public String searchBusinessByDesc(String searchTerm, ArrayList<Business> businesses)
    {
        String output = "Not found";
        for(int i=0;i<businesses.size();i++)
        {
            if (businesses.get(i).getBusinessDescription().contains(searchTerm))
            {
                output = businesses.get(i).getID();
                break;
            }
        }
        return output;
    }
    
}
