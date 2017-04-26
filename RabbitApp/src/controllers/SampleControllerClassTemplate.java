/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 * # The recommended set up for controller classes for RabbitFX.
 */
public class SampleControllerClassTemplate implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    
    @FXML
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }    
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    
}
