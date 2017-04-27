/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitobjects.Business;

/**
 *
 * @author Reisen
 */
public class BusinessManageTimeslotController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    Glow glow = new Glow();
    Image nodeDisabled = new Image("/GUI/fxml/assets/logo/logo_inactive.png");
    Image nodeFullHour = new Image("/GUI/fxml/assets/logo/logo_original.png");
    Image node00Hour = new Image("/GUI/fxml/assets/logo/logo_rightflop.png");
    Image node30Hour = new Image("/GUI/fxml/assets/logo/logo_leftflop.png");
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Objects                                              #
    ######################################################################### */               
    public TextField
            textfield_ActivityDesc;
    
    public Text
            text_AMorPM;
    
    public Button
            btn_Save;
    
    public ChoiceBox
            choicebox_SelectEmployee;
    
    public Label
            label_HoverIconDesc;
    
    public RadioButton
            radiobtn_Sunday,
            radiobtn_Monday,
            radiobtn_Tuesday,
            radiobtn_Wednesday,
            radiobtn_Thursday,
            radiobtn_Friday,
            radiobtn_Saturday,
            radiobtn_AM,
            radiobtn_PM;
    
    public ToggleButton
            toggle_FullHour,
            toggle_HalfHour00,
            toggle_HalfHour30;
    
    public ImageView
            img_Logo,
            img_00Hour,
            img_01Hour,
            img_02Hour,
            img_03Hour,
            img_04Hour,
            img_05Hour,
            img_06Hour,
            img_07Hour,
            img_08Hour,
            img_09Hour,
            img_10Hour,
            img_11Hour,
            img_12Hour,
            img_13Hour,
            img_14Hour,
            img_15Hour,
            img_16Hour,
            img_17Hour,
            img_18Hour,
            img_19Hour,
            img_20Hour,
            img_21Hour,
            img_22Hour,
            img_23Hour,
            img_Return;
    
    
    
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
    
    
    List<ImageView> hoursAM = new ArrayList<>();
    List<ImageView> hoursPM = new ArrayList<>();    
    public void setTimeslotList() {
        /* # Hour Lists */
        /* # Place the hour images into a list. */   
        hoursAM.add(img_00Hour);
        hoursAM.add(img_01Hour);
        hoursAM.add(img_02Hour);
        hoursAM.add(img_03Hour);
        hoursAM.add(img_04Hour);
        hoursAM.add(img_05Hour);
        hoursAM.add(img_06Hour);
        hoursAM.add(img_07Hour);
        hoursAM.add(img_08Hour);
        hoursAM.add(img_09Hour);
        hoursAM.add(img_10Hour);
        hoursAM.add(img_11Hour);
        /* # The list displayed depends on radio selection. */
        hoursPM.add(img_12Hour);
        hoursPM.add(img_13Hour);
        hoursPM.add(img_14Hour);
        hoursPM.add(img_15Hour);
        hoursPM.add(img_16Hour);
        hoursPM.add(img_17Hour);
        hoursPM.add(img_18Hour);
        hoursPM.add(img_19Hour);
        hoursPM.add(img_20Hour);
        hoursPM.add(img_21Hour);
        hoursPM.add(img_22Hour);
        hoursPM.add(img_23Hour);
        /* # Menu starts at AM.*/
        toggleAMorPM();
        setAvailableHours(true);
    }
    
    public void toggleAMorPM() {
        /* # As you toggle, make one set visible, and the other invisible. */
        hoursAM.forEach((node) -> {
            node.setOpacity(0.4);
            node.setImage(nodeDisabled);
            node.setVisible(!node.isVisible());
        });
        hoursPM.forEach((node) -> {
            node.setOpacity(0.4);
            node.setImage(nodeDisabled);
            node.setVisible(!node.isVisible());
        });
    }

    public void setAvailableHours(Boolean amORpm) {
        /* # Enable the hours that are within the business opening hours. */
        Business thisBusiness = (Business) session.currentUser;
        Time open = thisBusiness.getOpeningHours();
        Time close = thisBusiness.getClosingHours();
        
        Time amStart = new Time(00,00,00);
        Time pmStart = new Time(12,00,00);
        Time amStop = new Time(12,00,01);
        Time pmStop = new Time(00,00,01);
            
        /* amORpm - True = AM, False = PM */
        if(open==close) {
            /* # If 24 Hours... */
            if(amORpm) {
                enableHourNode(hoursAM, amStart, amStop);
            } else {
                enableHourNode(hoursPM, pmStart, null);
            }
            return;
        }        
        if(close.before(open)) {
            /* PM rolling over to AM. */
            if(amORpm) {
                enableHourNode(hoursAM, amStart, close); // Starting at 00:00 up to Close.          
            } else {              
                enableHourNode(hoursPM, open, pmStop); // Starting from Open up to 23:59.
            }
            return;
        }
        if(amORpm) {
            /* # View AM set, where AM rolls over to PM */
            if(close.after(pmStart)) {
                enableHourNode(hoursAM, open, amStop); // Starting from Open up to 11:59.
            } else {
                enableHourNode(hoursAM, open, close); // Starting from Open up to Close. (AM)
            }
            return;
        }
        if(!amORpm) {
            /* # View PM set. */
            if(open.before(pmStart)) { // Open is AM
                if(close.after(pmStart)) { // But Close is PM
                    enableHourNode(hoursPM, pmStart, close); // Starting from 12:00 up to Close.                    
                } else { // Close is not in PM therefore, nothing.
                    enableHourNode(hoursPM, amStart,amStart);
                }                             
            } else { // Open is also in PM.
                enableHourNode(hoursPM, open, close); // Starting from Open up to Close. (PM)
            }
        }              
    }
    
    public void enableHourNode(List<ImageView> list, Time start, Time end) {
        double s = start.getHours();
        double e = end.getHours();
        boolean foundStartNode = false;
        
        for(ImageView node : list) {
            double v = getHourThatObjectRepresents(turnStringToCharArray(node.getId()));
            System.out.println(v + " " + s + " " + e);
            if(v==s) {
                /* # We have found our starting point. */
                enableNode(node);
                foundStartNode = true;
                continue;
            }
            if(foundStartNode) {
                if(v==e) {
                    /* # We have reached the End time. */
                    return;
                } else {
                    enableNode(node);
                }
            }
        }        
    }
        
    public void enableNode(ImageView node) {
        node.setDisable(false);
        node.setOpacity(1);
        node.setImage(nodeFullHour);        
    }
    
    public void disableNode(ImageView node) {
        node.setDisable(true);
        node.setOpacity(0.4);
        node.setImage(nodeDisabled);
    }
    
    
 /* #########################################################################
  * #   Design Methods                                                      #
    ######################################################################### */
    
    public void setGlow(ImageView img) {
        img.setEffect(glow);
    }

    
 /* #########################################################################
  * #   On Click Methods                                                    #
    ######################################################################### */
    public void onClickHour(MouseEvent event) {
        /* # When user clicks on an Hour image. */
        System.out.println("Beep beep!");
        String idOfClickedNode = event.getPickResult().getIntersectedNode().getId();
        ImageView clickedImage = (ImageView) event.getSource();
        setGlow(clickedImage);
 
        /* # Some effect so we know it has been clicked. */      
    }
    
    public void onSelectAMorPM() {
        if(radiobtn_AM.isSelected()) {
            /* # First reset the states. */
            toggleAMorPM();
            setAvailableHours(true);
            text_AMorPM.setText("AM");
        }
        if(radiobtn_PM.isSelected()) {
            /* # First reset the states. */
            toggleAMorPM();
            setAvailableHours(false);
            text_AMorPM.setText("PM");
        }
    }
    
    public void onClickReturn() throws IOException {
        Stage stage = (Stage) img_Return.getScene().getWindow();
        rabbitfx.businessStage(stage);
    }
    
    
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */
    
    public double getHourThatObjectRepresents(char[] getDigits) {
        /* # This method is rather primitive, but it is better than a method per Image...
         * # Due to naming, we know that the 4th index and 5th index represent the Hours. 
         * # Consider img_XXHour. */
        int firstDigit = Character.getNumericValue(getDigits[4]);
        int secondDigit = Character.getNumericValue(getDigits[5]);
        double hour = 0;
        switch(firstDigit) {
            case 0:
                /* # If 0, it is between 00:00 and 09:59 */
                hour = secondDigit;
                break;
            case 1:
                /* # If 1, it is between 10:00 and 19:59 */
                hour = 10 + secondDigit;
                break;
            case 2:
                /* # If 2, it is between 20:00 and 23:59 */
                hour = 20 + secondDigit;
                break;
            default:
                System.out.println("Hour error.");
                break;
        }      
        return hour;
    }
    
    
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */    
    
    public char[] turnStringToCharArray(String input) {
        char[] array = input.toCharArray();
        return array;
    }
}
