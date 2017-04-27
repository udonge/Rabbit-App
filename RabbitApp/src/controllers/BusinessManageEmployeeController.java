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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 */
public class BusinessManageEmployeeController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    
    Boolean editOrAdding = false;
    
    @FXML
 /* #########################################################################
  * #   DECLARE JAVAFX OBJECTS                                              #
    ######################################################################### */
    public Button
            btn_AddEmployee,
            btn_EditEmployee,
            btn_DeleteEmployee,
            btn_EditTimeslot,
            btn_SaveEdit,
            btn_RemoveTimeslot,
            btn_CancelEdit;
    
    public ChoiceBox
            choicebox_ProfilePicture,
            choicebox_SelectEmployee,
            choicebox_SelectTimeslot;
    
    public ImageView
            img_Logo,
            img_ProfilePicture,
            img_Return,
            img_Timeslots;
    
    public Text
            text_EmployeeDesc,
            text_EmployeeID,
            text_EmployeeFirstName,
            text_EmployeeLastName,
            text_TimeslotTime,
            text_TimeslotDuration;
    
    public TextField
            textfield_EditFirstName,
            textfield_EditLastName;
    
    public TextArea
            textarea_EditDesc;

 /* #########################################################################
  * #   CONSTRUCTOR METHODS                                                 #
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
    
    public void hideEditFields() {
        /* # Hide what needs to be hidden... */
        textfield_EditFirstName.setVisible(false);
        textfield_EditLastName.setVisible(false);
        btn_SaveEdit.setVisible(false);
        btn_CancelEdit.setVisible(false);
    }

 /* #########################################################################
  * #   LOGIC METHODS                                                       #
    ######################################################################### */   
    
    public void addEmployee() {
        
    }
    
    public void editEmployee() {
        
    }
    
    public void deleteEmployee() {
        
    }
    
    public void getEmployeeInformation() {
        
    }
    
 /* #########################################################################
  * #   EVENT LISTENERS                                                     #
    ######################################################################### */   
    
    public void onClickAddEmployee() {
        /* # Set mode to Adding Employee */
        /* # Edit = False, Add = True */
        editOrAdding = true;
    }
}
