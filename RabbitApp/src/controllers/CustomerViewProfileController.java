/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Date;
import enums.AlertLabels;
import enums.ErrorLabels;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitmethods.Validation;
import rabbitobjects.Customer;

/**
 *
 * @author Reisen
 * # The recommended set up for controller classes for RabbitFX.
 */
public class CustomerViewProfileController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    Validation validation = new Validation();
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    Customer dummyUser;
         
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Objects                                              #
    ######################################################################### */
    public Pane
            editPane;
    
    public Text
            text_ErrorText;
    
    public Button
            btn_Save,
            btn_Cancel;
    
    public TextField
            textfield_EditEmail,
            textfield_EditFirstName,
            textfield_EditLastName,
            textfield_EditAddress,
            textfield_EditContactNo;
    
    public DatePicker
            datepicker_EditDateOfBirth;
    
    public Label
            label_ID,
            label_HoverIconDesc,
            label_EmailValue,
            label_Email,
            label_ContactNoValue,
            label_ContactNo,
            label_FirstNameValue,
            label_FirstName,
            label_LastNameValue,
            label_LastName,
            label_AddressValue,
            label_Address,
            label_DateOfBirth,
            label_DateOfBirthValue;
    
    public ImageView
            img_Timeslots,
            img_Logo,
            img_EditProfile,
            img_Return;
            
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }    
    
    public void getProfileInformation() {
        Customer user = (Customer) session.currentUser;
        label_HoverIconDesc.setText("");
        label_ID.setText("ID: " + user.getID());
        label_FirstNameValue.setText(user.getFirstName());
        label_LastNameValue.setText(user.getLastName());
        label_EmailValue.setText(user.getEmail());
        label_DateOfBirthValue.setText(String.format("%02d/%02d/%d", user.getDateOfBirth().getDate(), (user.getDateOfBirth().getMonth()+1), (1900+user.getDateOfBirth().getYear())));
        /* # Optional Values **/
        if(user.getAddress()==null) {
            label_AddressValue.setText("None");
        } else {
            label_AddressValue.setText(user.getAddress());
        }
        if(user.getContactNo()==null) {
            label_ContactNoValue.setText("None");
        } else {
            label_ContactNoValue.setText(user.getContactNo());
        }   
    } // End getProfileInformation
    
    public void setProfileInformation() {
        /* # Allow user to edit their profile. */
        Customer user = (Customer) session.currentUser;
        textfield_EditFirstName.setText(user.getFirstName());
        textfield_EditLastName.setText(user.getLastName());
        textfield_EditEmail.setText(user.getEmail());
        datepicker_EditDateOfBirth.setValue(user.getDateOfBirth().toLocalDate());
        /* # Optionals Check */
        if(user.getAddress()!=null) {
            textfield_EditAddress.setText(user.getAddress());
        }
        if(user.getContactNo()!=null) {
            textfield_EditContactNo.setText(user.getContactNo());
        }      
    } // End setProfileInformation    
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        this.session = session;
    }
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */     
    
    public void saveProfileEdits() {
        if(processProfileEdit()) {
            text_ErrorText.setVisible(false);
            session.updateUser(dummyUser);
            getProfileInformation();
            onClickCancel();
        } else {
            text_ErrorText.setVisible(true);
            text_ErrorText.setText(ErrorLabels.EDIT_PROFILE_FAILURE_SOMETHINGISINVALID.toString());
        }
        
    } // End saveProfileEdits
    
    public boolean fieldIsEmpty(TextField field) {
        return field.getText().isEmpty();
    }
    
    public boolean processProfileEdit() {
        Customer user = (Customer) session.currentUser;
        String
                newEmail,
                newFName,
                newLName,
                newAddress,
                newContactNo;
        Date
                newDateOfBirth;
        
        /* # If Field is Empty, keep it as is - Required. */
        if(fieldIsEmpty(textfield_EditEmail)) {
            newEmail = user.getEmail();
        } else {
            newEmail = textfield_EditEmail.getText();
        }
        if(fieldIsEmpty(textfield_EditFirstName)) {
            newFName = user.getFirstName();
        } else {
            newFName = textfield_EditFirstName.getText();
        }
        if(fieldIsEmpty(textfield_EditLastName)) {
            newLName = user.getLastName();
        } else {
            newLName = textfield_EditLastName.getText();
        }
        newDateOfBirth = new Date((datepicker_EditDateOfBirth.getValue().getYear()-1900), (datepicker_EditDateOfBirth.getValue().getMonthValue()-1), datepicker_EditDateOfBirth.getValue().getDayOfMonth());
        /* # If Optionals are empty. 
         * # Optionals have to check if the object is not Null before calling... 
         * # Otherwise you get null pointer exception. */
        if(fieldIsEmpty(textfield_EditAddress)) {
            if(user.getAddress()!=null) {
                newAddress = user.getAddress();
            } else {
                newAddress = null;
            }
        } else {
            newAddress = textfield_EditAddress.getText();
        }
        if(fieldIsEmpty(textfield_EditContactNo)) {
            if(user.getContactNo()!=null) {
                newContactNo = user.getContactNo();
            } else {
                newContactNo = null;
            }
        } else {
            newContactNo = textfield_EditContactNo.getText();
        }
        
        return validateProfileEdits(newEmail, newFName, newLName, newAddress, newContactNo, newDateOfBirth);
    }

    public boolean validateProfileEdits(String email, String fname, String lname, String address, String contactNo, Date dob) {
        /* # Go through everything and validate. */
        Text error = new Text();
        /* # Parse in empty error text for now. */
        if(email!=null) {
            if(validation.emailFieldIsValid(email, error, session)) {
            } else {
                if(email.equals(session.currentUser.getEmail())) {
                } else {
                    return false;
                }
            }
        } else { // These are REQUIRED fields so should never be null.
            return false;
        }
        
        if(fname!=null && lname!=null) {
            if(validation.nameFieldsAreValid(fname, lname, error)) {
            } else {
                return false;
            }
        } else { // These are REQUIRED fields so should never be null.
            return false;
        }
        
        if(contactNo!=null) {
            if(validation.contactNoIsValid(contactNo, error)) {
                
            } else {
                return false;
            }
        } else { // This is optional so can be null.
            
        }
        
        //DOB VALIDATION MUST BE DONE HERE.
        
        /* # Given that everything is valid, we need*/
        String id = session.currentUser.getID();
        dummyUser = new Customer(id, null, email, address, contactNo, fname, lname, dob, null);
        /* # Update the user in the session List too.*/
        Customer updateUser = (Customer) session.currentUser;
        updateUser.setFirstName(fname);
        updateUser.setLastName(lname);
        updateUser.setEmail(email);
        updateUser.setDateOfBirth(dob);
        updateUser.setAddress(address);
        updateUser.setContactNo(contactNo);
        return true;
    }
    
 /* #########################################################################
  * #   On Hover Icon Methods                                               #
    ######################################################################### */
    
    /* # A better method to do this has been discovered, Marked for replacement. 
     * # See BusinessManageTimeslotController.java for example. */
    
    public void onHoverEffectsApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(glow);
    }

    public void onHoverExit() {
        label_HoverIconDesc.setText("");
        img_Timeslots.setEffect(null);
        img_EditProfile.setEffect(null);
        img_Return.setEffect(null);        
    }
    
    public void onHoverEditProfile() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_VIEW_BUSINESS_PROFILE.toString());
        onHoverEffectsApplyTo(img_EditProfile);        
    }
    
    public void onHoverReturn() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_RETURN.toString());
        onHoverEffectsApplyTo(img_Return);
    }    
    
 /* #########################################################################
  * #   On Click Icon Methods                                               #
    ######################################################################### */ 
    
    public void onClickSave() {
        saveProfileEdits();
    }
    
    public void onClickCancel() {
        editPane.setVisible(false);
        btn_Cancel.setVisible(false);
        btn_Save.setVisible(false);        
    }
    
    public void onClickEditProfile() throws IOException {
        setProfileInformation();
        editPane.setVisible(true);
        btn_Cancel.setVisible(true);
        btn_Save.setVisible(true);
    }
    
    public void onClickReturn() throws IOException {
        Stage stage = (Stage) img_Return.getScene().getWindow();
        rabbitfx.businessStage(stage);      
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
    
    public void onMouseHoldEditProfile() {
        onMouseHoldEffectApplyTo(img_EditProfile);        
    }
    
    public void onMouseHoldReturn() {
        onMouseHoldEffectApplyTo(img_Return);        
    }
}
