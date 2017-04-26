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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 */
public class BusinessManageTimeslotController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Objects                                              #
    ######################################################################### */   
    public Text
            label_HoverIconDesc;

    public TextField
            textfield_ActivityDesc;
    
    public Button
            btn_Save;
    
    public ChoiceBox
            choicebox_SelectEmployee;
    
    public RadioButton
            radiobtn_Sunday,
            radiobtn_Monday,
            radiobtn_Tuesday,
            radiobtn_Wednesday,
            radiobtn_Thursday,
            radiobtn_Friday,
            radiobtn_Saturday;
    
    public ToggleButton
            toggle_FullHour,
            toggle_HalfHour00,
            toggle_HalfHour30,
            toggle_AM,
            toggle_PM;
    
    public ImageView
            img_Logo,
            img_0Hour,
            img_1Hour,
            img_2Hour,
            img_3Hour,
            img_4Hour,
            img_5Hour,
            img_6Hour,
            img_7Hour,
            img_8Hour,
            img_9Hour,
            img_10Hour,
            img_11Hour;
    
    
    
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */    
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
