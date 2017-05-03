/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.AlertLabels;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitobjects.Business;

/**
 *
 * @author Reisen
 */
public class BusinessMainMenuController implements Initializable {
    RabbitFX rabbitfx;
    Session session;
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX objects                                              #
    ######################################################################### */
    public ImageView 
            img_Timeslots,
            img_ViewProfile,
            img_Employees,
            img_Logout,
            img_About;
    public Label
            label_Title,
            label_GreetingText,
            label_HoverIconDesc;
    
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
    
    public void setBusinessMenu() {
        Business sessionUser = (Business) session.currentUser;
        label_GreetingText.setText(sessionUser.getBusinessName() + " management menu.");
        glow.setLevel(0.5);
        innershadow.setOffsetX(6);
        innershadow.setOffsetY(6);
        innershadow.setColor(Color.BLACK);
        
        if(sessionUser.getOpeningHours()==null || sessionUser.getClosingHours()==null) {
            disableNode(img_Timeslots);
        }
    }
    
    public void enableNode(ImageView node) {
        node.setDisable(false);
        node.setOpacity(0.8);      
    }
    
    public void disableNode(ImageView node) {
        node.setDisable(true);
        node.setOpacity(0.4);
    }
    
    public void disableTimeslotsIfNoEmployees() {
        Business business = (Business) session.currentUser;
        if(business.getListOfEmployees().isEmpty()) {
            img_Timeslots.setOpacity(0.4);
            img_Timeslots.setDisable(true);
        }
    }
    

    
 /* #########################################################################
  * #   On Hover Icon Methods                                               #
    ######################################################################### */
    
    public void onHoverEffectsApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(glow);
    }

    public void onHoverExit() {
        label_HoverIconDesc.setText("");
        img_Timeslots.setEffect(null);
        img_ViewProfile.setEffect(null);
        img_Employees.setEffect(null);
        img_Logout.setEffect(null);
        img_About.setEffect(null);        
    }
    
    public void onHoverTimeslots() {
        if(img_Timeslots.isDisabled()) {
            label_HoverIconDesc.setText("You must set your opening hours first.");
        } else {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_WORKSHIFTS.toString());
            onHoverEffectsApplyTo(img_Timeslots);            
        }
    }
    
    public void onHoverEmployees() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_EMPLOYEES.toString());
        onHoverEffectsApplyTo(img_Employees);        
    }
    
    public void onHoverViewProfile() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_VIEW_BUSINESS_PROFILE.toString());
        onHoverEffectsApplyTo(img_ViewProfile);        
    }
    
    public void onHoverLogout() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_LOGOUT.toString());
        onHoverEffectsApplyTo(img_Logout);
    }
    
    public void onHoverAbout() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_ABOUT.toString());
        onHoverEffectsApplyTo(img_About);
    }   
    
 /* #########################################################################
  * #   On Click Icon Methods                                               #
    ######################################################################### */ 
    
    public void onClickTimeslots() throws IOException {
        Stage stage = (Stage) img_Timeslots.getScene().getWindow();
        rabbitfx.viewBusinessManageTimeslot(stage);
        
    }
    
    public void onClickEmployees() throws IOException {
        Stage stage = (Stage) img_Employees.getScene().getWindow();
        rabbitfx.viewBusinessManageEmployee(stage);
    }
    
    public void onClickViewProfile() throws IOException {
        Stage stage = (Stage) img_ViewProfile.getScene().getWindow();
        rabbitfx.viewProfileBusiness(stage);           
    }
    
    public void onClickLogout() throws IOException {
        Stage stage = (Stage) img_Logout.getScene().getWindow();
        session.logout();
        rabbitfx.loginStage(stage);      
    }
    
    public void onClickAbout() {
        
    }
    
 /* #########################################################################
  * #   On Mouse Hold Methods                                               #
    ######################################################################### */     
    public void onMouseHoldEffectApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(innershadow);        
    }
    
    public void onMouseHoldTimeslots() {
        onMouseHoldEffectApplyTo(img_Timeslots);
    }
    
    public void onMouseHoldEmployees() {
        onMouseHoldEffectApplyTo(img_Employees);        
    }
    
    public void onMouseHoldViewProfile() {
        onMouseHoldEffectApplyTo(img_ViewProfile);        
    }
    
    public void onMouseHoldLogout() {
        onMouseHoldEffectApplyTo(img_Logout);        
    }
    
    public void onMouseHoldAbout() {
        onMouseHoldEffectApplyTo(img_About);        
    }
}
