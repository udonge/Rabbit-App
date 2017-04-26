/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.AlertLabels;
import enums.ErrorLabels;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import rabbitobjects.Business;

/**
 *
 * @author Reisen
 * # The recommended set up for controller classes for RabbitFX.
 */
public class BusinessViewProfileController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    Validation validation = new Validation();
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    Business dummyUser;
         
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
    
    public ChoiceBox<String>
            choicebox_OpenHH,
            choicebox_OpenMM,
            choicebox_CloseHH,
            choicebox_CloseMM;
    
    public TextField
            textfield_EditEmail,
            textfield_EditBusinessName,
            textfield_EditAddress,
            textfield_EditContactNo,
            textfield_EditOpenHour,
            textfield_EditCloseHour,
            textfield_EditFirstName,
            textfield_EditLastName;
    
    public Label
            label_ID,
            label_HoverIconDesc,
            label_OpeningHours,
            label_HoursOfOperation,
            label_EmailValue,
            label_Email,
            label_ContactNoValue,
            label_ContactNo,
            label_BusinessOwnerValue,
            label_BusinessOwner,
            label_BusinessNameValue,
            label_BusinessName,
            label_AddressValue,
            label_Address;
    
    public ImageView
            img_Timeslots,
            img_Logo,
            img_Employees,
            img_EditProfile,
            img_Return;
    
    public String[] hours = { 
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23" };
    
    public String[] minutes = { "00", "30" };
            
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }    
    
    public void getProfileInformation() {
        Business user = (Business) session.currentUser;
        label_HoverIconDesc.setText("");
        label_ID.setText("ID: " + user.getID());
        label_BusinessNameValue.setText(user.getBusinessName());
        label_EmailValue.setText(user.getEmail());
        label_BusinessOwnerValue.setText(user.getBusinessOwnerFirstName() + " " + user.getBusinessOwnerLastName());
        /* # Optional Values */
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
        if(user.getOpeningHours()==null || user.getClosingHours()==null) {
            
            label_HoursOfOperation.setText("Unspecified.");
        } else {
            SimpleDateFormat s = new SimpleDateFormat("HH:mm");
            String open = s.format(user.getOpeningHours());
            String close = s.format(user.getClosingHours());
            label_HoursOfOperation.setText(open + " : " + close);
        }       
    } // End getProfileInformation
    
    public void setProfileInformation() {
        /* # Allow user to edit their profile. */
        SimpleDateFormat h = new SimpleDateFormat("HH");
        SimpleDateFormat m = new SimpleDateFormat("mm");
        Business user = (Business) session.currentUser;
        textfield_EditBusinessName.setText(user.getBusinessName());
        textfield_EditEmail.setText(user.getEmail());
        textfield_EditFirstName.setText(user.getBusinessOwnerFirstName());
        textfield_EditLastName.setText(user.getBusinessOwnerLastName());
        
        /* # Optionals Check */
        if(user.getAddress()!=null) {
            textfield_EditAddress.setText(user.getAddress());
        }
        if(user.getContactNo()!=null) {
            textfield_EditContactNo.setText(user.getContactNo());
        }
        if(user.getOpeningHours()!=null) {
            
            String hour = h.format(user.getOpeningHours());
            String minute = m.format(user.getOpeningHours());
            for(String string : hours) {
                if(hour.equals(string)) {
                    choicebox_OpenHH.setValue(string);
                    break;
                }
            }
            for(String string : minutes) {
                if(minute.equals(string)) {
                    choicebox_OpenMM.setValue(string);
                    break;
                }
            }
        }
        if(user.getClosingHours()!=null) {
            
            String hour = h.format(user.getClosingHours());
            String minute = m.format(user.getClosingHours());
            for(String string : hours) {
                if(hour.equals(string)) {
                    choicebox_CloseHH.setValue(string);
                    break;
                }
            }
            for(String string : minutes) {
                if(minute.equals(string)) {
                    choicebox_CloseMM.setValue(string);
                    break;
                }
            }
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

    
    public void setChoiceBox() {
        choicebox_OpenHH.getItems().addAll(hours);
        choicebox_OpenMM.getItems().addAll(minutes);
        choicebox_CloseHH.getItems().addAll(hours);
        choicebox_CloseMM.getItems().addAll(minutes);        
    } // End setChoiceBox
    
    public void saveProfileEdits() {
        if(processProfileEdit()) {
            text_ErrorText.setVisible(false);
            session.updateUser(dummyUser);
            System.out.println("Successful update!");
            getProfileInformation();
        } else {
            text_ErrorText.setVisible(true);
            text_ErrorText.setText(ErrorLabels.EDIT_PROFILE_FAILURE_SOMETHINGISINVALID.toString());
            System.out.println("Unsuccessful update!");
        }
        
    } // End saveProfileEdits
    
    public boolean fieldIsEmpty(TextField field) {
        return field.getText().isEmpty();
    }
    
    public boolean processProfileEdit() {
        Business user = (Business) session.currentUser;
        Text errorText = text_ErrorText;
        String
                newBName,
                newEmail,
                newBFName,
                newBLName;
        String 
                newAddress,
                newContactNo,
                newOpenHH,
                newOpenMM,
                newCloseHH,
                newCloseMM;

        Time openHour = null;
        Time closeHour = null;
        
        /* # If Field is Empty, keep it as is - Required. */
        if(fieldIsEmpty(textfield_EditBusinessName)) {
            newBName = user.getBusinessName();
        } else {
            newBName = textfield_EditBusinessName.getText();
        }
        if(fieldIsEmpty(textfield_EditEmail)) {
            newEmail = user.getEmail();
        } else {
            newEmail = textfield_EditEmail.getText();
        }
        if(fieldIsEmpty(textfield_EditFirstName)) {
            newBFName = user.getBusinessOwnerFirstName();
        } else {
            newBFName = textfield_EditFirstName.getText();
        }
        if(fieldIsEmpty(textfield_EditLastName)) {
            newBLName = user.getBusinessOwnerLastName();
        } else {
            newBLName = textfield_EditLastName.getText();
        }
        
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
        
        if(choicebox_OpenHH.getSelectionModel().isEmpty() || choicebox_OpenMM.getSelectionModel().isEmpty()) {
            if(user.getOpeningHours()!=null) {
                openHour = user.getOpeningHours();
            } else {
                openHour = null;
            }
        } else {
            newOpenHH = (String) choicebox_OpenHH.getValue();
            newOpenMM = (String) choicebox_OpenMM.getValue();
            openHour = stringToSQLTime(newOpenHH, newOpenMM); 
        }
        
        if(choicebox_CloseHH.getSelectionModel().isEmpty() || choicebox_CloseMM.getSelectionModel().isEmpty()) {
            if(user.getClosingHours()!=null) {
                closeHour = user.getClosingHours();
            } else {
                closeHour = null;
            }
        } else {
            newCloseHH = (String) choicebox_CloseHH.getValue();
            newCloseMM = (String) choicebox_CloseMM.getValue();
            closeHour = stringToSQLTime(newCloseHH, newCloseMM);            
        }
        return validateProfileEdits(newBName, newEmail, newBFName, newBLName, newAddress, newContactNo, openHour, closeHour);
    }

    public boolean validateProfileEdits(String bname, String email, String fname, String lname, String address, String contactNo, Time open, Time close) {
        /* # Go through everything and validate. */
        Text error = new Text();
        /* # Parse in empty error text for now. */
        if(bname!=null) {
            if(validation.businessNameIsValid(bname, error)) {
            } else {
                return false;
            }
        } else { // These are REQUIRED fields so should never be null.
            return false;
        }
        
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
        
        if(open!=null || close!=null) {
            
        } else {
            // Decided not to set a time. Whatever.
        }
        /* # Given that everything is valid, we need*/
        String id = session.currentUser.getID();
        dummyUser = new Business(id, null, email, address, contactNo, bname, fname, lname, null, open, close, false);
        /* # Update the user in the session List too.*/
        Business updateUser = (Business) session.currentUser;
        updateUser.setBusinessName(bname);
        updateUser.setBusinessOwnerFirstName(fname);
        updateUser.setBusinessOwnerLastName(lname);
        updateUser.setEmail(email);
        updateUser.setOpeningHours(open);
        updateUser.setClosingHours(close);
        updateUser.setAddress(address);
        updateUser.setContactNo(contactNo);
        return true;
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
        img_EditProfile.setEffect(null);
        img_Employees.setEffect(null);
        img_Return.setEffect(null);        
    }
    
    public void onHoverTimeslots() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_TIMESLOTS.toString());
        onHoverEffectsApplyTo(img_Timeslots);
    }
    
    public void onHoverEmployees() {
        label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_EMPLOYEES.toString());
        onHoverEffectsApplyTo(img_Employees);        
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
    
    public void onClickTimeslots() {
        
    }
    
    public void onClickEmployees() {
        
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
    
    public void onMouseHoldEmployees() {
        onMouseHoldEffectApplyTo(img_Employees);        
    }
    
    public void onMouseHoldEditProfile() {
        onMouseHoldEffectApplyTo(img_EditProfile);        
    }
    
    public void onMouseHoldReturn() {
        onMouseHoldEffectApplyTo(img_Return);        
    }
    
 /* #########################################################################
  * #   Utility                                                             #
    ######################################################################### */    
    
    public Time stringToSQLTime(String hour, String minute) {
        try {
            String s = hour + "-" + minute;
            SimpleDateFormat sts = new SimpleDateFormat("HH-mm");
            long ms = sts.parse(s).getTime();
            Time t = new Time(ms);

            return t;            
        } catch (ParseException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }
}
