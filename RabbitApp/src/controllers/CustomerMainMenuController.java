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
import javafx.scene.effect.Glow;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitobjects.Customer;

/**
 *
 * @author Reisen
 */
public class CustomerMainMenuController implements Initializable {
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    RabbitFX rabbitfx;  
    Session session;
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Objects                                              #
    ######################################################################### */    
    private Label 
            user_Title,
            label_HoverIconDesc,
            label_GreetingText;
    
    public ImageView
            img_ViewBooking,
            img_MakeBooking,
            img_CustomerProfile,
            img_About,
            img_Logout;
    
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }    
    
    public void setCustomerMenu() {
        Customer sessionUser = (Customer) session.currentUser;
        label_GreetingText.setText("Hello, " + sessionUser.getFirstName());
        glow.setLevel(0.5);
        innershadow.setOffsetX(6);
        innershadow.setOffsetY(6);
        innershadow.setColor(Color.BLACK);  
    }
    
 /* #########################################################################
  * #   On Hover Icon Methods                                               #
    ######################################################################### */   
    
    public void onClickMakeBooking() {
    // TODO
    }   
    public void onMouseHoldMakeBooking() {
        onMouseHoldEffectApplyTo(img_MakeBooking);
    }
    public void onHoverMakeBooking() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MAKE_BOOKING.toString());
        onHoverEffectApplyTo(img_MakeBooking);
    }    
    
    public void onClickViewBooking () {
    // TODO   
    }
    public void onMouseHoldViewBooking() {
        onMouseHoldEffectApplyTo(img_ViewBooking);
        
    }
    public void onHoverViewBooking() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_VIEW_BOOKING.toString());   
        onHoverEffectApplyTo(img_ViewBooking);
    }
    
    public void onClickViewProfile() throws IOException {
        Stage stage = (Stage) img_CustomerProfile.getScene().getWindow();
        rabbitfx.viewProfileCustomer(stage);      
    }
    public void onMouseHoldViewProfile() {
        onMouseHoldEffectApplyTo(img_CustomerProfile);
    }
    public void onHoverViewProfile() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_VIEW_CUSTOMER_PROFILE.toString());
        onHoverEffectApplyTo(img_CustomerProfile);
    }
    
    public void onClickAbout() {
        
    }
    public void onMouseHoldAbout() {
        onMouseHoldEffectApplyTo(img_About);
        
    }
    public void onHoverAbout() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_ABOUT.toString());
        onHoverEffectApplyTo(img_About);
    }
    
    public void onClickLogout() throws IOException {
        Stage stage = (Stage) img_Logout.getScene().getWindow();
        session.logout();
        rabbitfx.loginStage(stage);             
    }
    public void onMouseHoldLogout() {
        onMouseHoldEffectApplyTo(img_Logout);
        
    }
    public void onHoverLogout() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_LOGOUT.toString());
        onHoverEffectApplyTo(img_Logout);

    }
    
    /* # Mouse leaves any icon. */
    public void onHoverExit() {
        label_HoverIconDesc.setText("");
        img_MakeBooking.setEffect(null);
        img_ViewBooking.setEffect(null);
        img_CustomerProfile.setEffect(null);
        img_About.setEffect(null);
        img_Logout.setEffect(null);
    }
    public void onHoverEffectApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(glow);
    }
    public void onMouseHoldEffectApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(innershadow);
    }
      
}
