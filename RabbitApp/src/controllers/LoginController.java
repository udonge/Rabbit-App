/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.ErrorLabels;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import rabbitapp.RabbitFX;
import rabbitmethods.Session;

/**
 *
 * @author Reisen
 */
public class LoginController implements Initializable {
    
    Session session;
    RabbitFX rabbitfx;
    Glow glow = new Glow();
    
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX objects                                              #
    ######################################################################### */
    
    private Label login_Title;
    
    public Button login_LoginButton;
    public ImageView login_Logo;
    public ImageView login_LogoAlt;
    public Text login_ErrorText;
    public TextField login_UserField;
    public PasswordField login_PasswordField;
    public Hyperlink login_link_Register;
    
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */  
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        glow.setLevel(0.3);
                  
    }
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
   
    public void setSession(Session session) {
        this.session = session;
    }
    
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */
    
    public void loginButton() throws IOException {
        /* # When login button is clicked. */
        if(textFieldIsEmpty(login_UserField) && passwordFieldIsEmpty(login_PasswordField)) {
            login_ErrorText.setText(ErrorLabels.LOGIN_FAILURE_NO_FIELDS.toString());
            return;
        }
        if(textFieldIsEmpty(login_UserField)) {
            login_ErrorText.setText(ErrorLabels.LOGIN_FAILURE_NO_USER.toString());
            return;
            
        }
        if(passwordFieldIsEmpty(login_PasswordField)) {
            login_ErrorText.setText(ErrorLabels.LOGIN_FAILURE_NO_PASSWORD.toString());
            return;       
        }
        
        /* # Need to compare fields to list of users and passwords... */
        if(session.login(login_UserField.getText(), login_PasswordField.getText())) {
            /* # Get the stage currently displayed. */
            Stage stage = (Stage) login_LoginButton.getScene().getWindow();
            /* # Session to session. */
            session.login(login_UserField.getText(), login_PasswordField.getText());
            checkUserType(session.currentUser.getID(), stage);
            
        } else {
            login_ErrorText.setText(ErrorLabels.LOGIN_FAILURE_INCORRECT.toString());
        }      
    } // End of loginButton()
    
    public void checkUserType(String userID, Stage stage) throws IOException {
    /* # I think this set up will allow for future expansion in user types (e.g Freelancer) */

        char[] reader = userID.toCharArray();
        switch(reader[0]) {
            case 'B':
            case 'b':
                /* # Business type. */
                rabbitfx.businessStage(stage);
                break;
            case 'C':
            case 'c':
                /* # Customer type.*/
                rabbitfx.customerStage(stage);
                break;
            default:
                System.out.println("Unknown User Type");
                break;
        }   
    } // End of checkUserType()
    
    public void goToRegistration() throws IOException {
        Stage stage = (Stage) login_link_Register.getScene().getWindow();      
        rabbitfx.registrationStage(stage);
    } // End goToRegistration
    
 /* #########################################################################
  * #   Design Methods                                                      #
    ######################################################################### */
    
    public void onClickLogo() {
        login_Logo.setVisible(!login_Logo.isVisible());            
        login_LogoAlt.setVisible(!login_LogoAlt.isVisible());        
    } // End onClickLogo
    
    public void onHoverLogo() {
        /* # When mouse cursor reaches logo. */
        login_LogoAlt.setEffect(glow);
        login_Logo.setEffect(glow);    
    } // End onHoverLogo
    
    public void onHoverExitLogo() {
        login_LogoAlt.setEffect(null);
        login_Logo.setEffect(null);  
    } // End onHoverExitLogo
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */

    public boolean textFieldIsEmpty(TextField field) {
        return field.getText().isEmpty() || field.getText().trim().isEmpty();
    } // End textFieldIsEmpty
    
    public boolean passwordFieldIsEmpty(PasswordField field) {
        return field.getText().isEmpty() || field.getText().trim().isEmpty();
    } // End passwordFieldIsEmpty
}
