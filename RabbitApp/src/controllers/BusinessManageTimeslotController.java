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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    Glow glow = new Glow();
    
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
            img_12Hour;
    
    
    
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
        String idOfClickedNode = event.getPickResult().getIntersectedNode().getId();
        ImageView clickedImage = (ImageView) event.getSource();
        /* # Some effect so we know it has been clicked. */
        setGlow(clickedImage);
        
        getHourThatObjectRepresents(turnStringToCharArray(idOfClickedNode));

               
    }
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */
    
    public void getHourThatObjectRepresents(char[] getDigits) {
        /* # This method is rather primitive, but it is better than a method per Image...
         * # Due to naming, we know that the 4th index and 5th index represent the Hours. 
         * # Consider img_XXHour. */
        int firstDigit = Character.getNumericValue(getDigits[4]);
        int secondDigit = Character.getNumericValue(getDigits[5]);
        int hour = 0;
        /* # 0 - 11 Hours, therefore if first digit is not 0, it is 10 or 11 or 12.*/
        switch(firstDigit) {
            case 0:
                /* # If 0, it is between 1 and 9 o clock. */
                hour = secondDigit;
                break;
            case 1:
                /* # If 1, it is between 10 and 12 o clock. */
                hour = 10 + secondDigit;
                break;
            default:
                System.out.println("Hour error.");
                break;
        }
        
        System.out.println(hour);
    }
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */    
    
    public char[] turnStringToCharArray(String input) {
        char[] array = input.toCharArray();
        return array;
    }
}
