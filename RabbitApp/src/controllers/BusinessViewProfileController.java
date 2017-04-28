/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.AlertLabels;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitmethods.Validation;
import rabbitobjects.Business;

/**
 *
 * @author Reisen
 */
public class BusinessViewProfileController implements Initializable {
    RabbitFX rabbitfx;
    Session session;
    Validation validation = new Validation();
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    Business thisBusiness;

    public String[] hours = { 
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23" };
    
    public String[] minutes = { "00", "30" };
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Object                                               #
    ######################################################################### */
    public Button
            btn_Save,
            btn_Cancel;
    public Text
            text_OwnerName,
            text_OpeningHours,
            text_Email,
            text_Description,
            text_ContactNo,
            text_NameOfBusiness,
            text_To,
            text_OpenColon,
            text_CloseColon,
            text_Address;
    
    public Label
            label_ID,
            label_Title,
            label_HoverIconDesc,
            label_BusinessName;
    
    public ImageView
            img_Logo,
            img_Timeslots,
            img_Employees,
            img_EditProfile,
            img_Return;
    
    public ChoiceBox<String>
            choicebox_CloseHH,
            choicebox_CloseMM,
            choicebox_OpenHH,
            choicebox_OpenMM;
    
    public TextField
            textfield_NewEmail,
            textfield_NewAddress,
            textfield_NewFirstName,
            textfield_NewLastName,
            textfield_NewBusinessName,
            textfield_NewContactNo;
    
    public TextArea
            textarea_NewDesc;
            
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
        this.thisBusiness = (Business) session.currentUser;
    }
    
    public void setChoiceBox() {
        choicebox_OpenHH.getItems().addAll(hours);
        choicebox_OpenMM.getItems().addAll(minutes);
        choicebox_CloseHH.getItems().addAll(hours);
        choicebox_CloseMM.getItems().addAll(minutes);        
    }    
    
    public void getBusinessProfileInformation() {
        
        label_ID.setText("ID: " + thisBusiness.getID());
        text_NameOfBusiness.setText(thisBusiness.getBusinessName());
        text_OwnerName.setText(thisBusiness.getBusinessOwnerFirstName() + " " + thisBusiness.getBusinessOwnerLastName());
        text_Email.setText(thisBusiness.getEmail());
        
        if(thisBusiness.getAddress()==null) {
            text_Address.setText("Unspecified.");
        }else {
            text_Address.setText(thisBusiness.getAddress());
        }
        
        if(thisBusiness.getContactNo()==null) {
            text_ContactNo.setText("Unspecified.");
        }else {
            text_ContactNo.setText(thisBusiness.getContactNo());
        }
        
        if(thisBusiness.getBusinessDescription()==null) {
            text_Description.setText("No description.");
        } else {
            text_Description.setText(thisBusiness.getBusinessDescription());
        }
        
        if(thisBusiness.getOpeningHours()==null || thisBusiness.getClosingHours()==null) {
            text_OpeningHours.setText("Unspecified");
        } else {
            if(thisBusiness.getOpeningHours().equals(thisBusiness.getClosingHours())) {
                text_OpeningHours.setText("24 Hours");
            } else {
                SimpleDateFormat s = new SimpleDateFormat("HH:mm");
                String open = s.format(thisBusiness.getOpeningHours());
                String close = s.format(thisBusiness.getClosingHours());
                text_OpeningHours.setText(open + " : " + close);
            }
        }
    }
    
    public void setProfileInformationInTextFields() {
        
        SimpleDateFormat h = new SimpleDateFormat("HH");
        SimpleDateFormat m = new SimpleDateFormat("mm");
        
        textfield_NewBusinessName.setText(thisBusiness.getBusinessName());
        textfield_NewFirstName.setText(thisBusiness.getBusinessOwnerFirstName());
        textfield_NewLastName.setText(thisBusiness.getBusinessOwnerLastName());
        textfield_NewEmail.setText(thisBusiness.getEmail());
        
        if(thisBusiness.getAddress()!=null) {
            textfield_NewAddress.setText(thisBusiness.getAddress());
        }
        if(thisBusiness.getContactNo()!=null) {
            textfield_NewContactNo.setText(thisBusiness.getContactNo());
        }
        if(thisBusiness.getOpeningHours()!=null) {
            
            String hour = h.format(thisBusiness.getOpeningHours());
            String minute = m.format(thisBusiness.getOpeningHours());
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
        if(thisBusiness.getClosingHours()!=null) {
            
            String hour = h.format(thisBusiness.getClosingHours());
            String minute = m.format(thisBusiness.getClosingHours());
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
    }
    
    public void toggleEditObjectVisibility() {
        btn_Cancel.setVisible(!btn_Cancel.isVisible());
        btn_Save.setVisible(!btn_Save.isVisible());
        textfield_NewBusinessName.setVisible(!textfield_NewBusinessName.isVisible());
        textfield_NewFirstName.setVisible(!textfield_NewFirstName.isVisible());
        textfield_NewLastName.setVisible(!textfield_NewLastName.isVisible());
        textfield_NewEmail.setVisible(!textfield_NewEmail.isVisible());
        textfield_NewAddress.setVisible(!textfield_NewAddress.isVisible());
        textfield_NewContactNo.setVisible(!textfield_NewContactNo.isVisible());
        textarea_NewDesc.setVisible(!textarea_NewDesc.isVisible());
        choicebox_OpenHH.setVisible(!choicebox_OpenHH.isVisible());
        choicebox_CloseHH.setVisible(!choicebox_CloseHH.isVisible());
        choicebox_OpenMM.setVisible(!choicebox_OpenMM.isVisible());
        choicebox_CloseMM.setVisible(!choicebox_CloseMM.isVisible());
        text_To.setVisible(!text_To.isVisible());
        text_OpenColon.setVisible(!text_OpenColon.isVisible());
        text_CloseColon.setVisible(!text_CloseColon.isVisible());
    }
    
 /* #########################################################################
  * #   Listener & Handler Methods                                          #
    ######################################################################### */ 
    
    public void onHoverImg(MouseEvent event) {
        ImageView hoverNode = (ImageView) event.getSource();
        onHoverEffectsApplyTo(hoverNode);
        if(hoverNode.getId().equals(img_Timeslots.getId())) {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_TIMESLOTS.toString());
        }
        if(hoverNode.getId().equals(img_Employees.getId())) {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_MANAGE_EMPLOYEES.toString());
        }
        if(hoverNode.getId().equals(img_Return.getId())) {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_RETURN.toString());
        }
        if(hoverNode.getId().equals(img_EditProfile.getId())) {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_VIEW_BUSINESS_PROFILE.toString());
        }        
    }
    
    public void onHoverExitImg(MouseEvent event) {
        ImageView hoverExitNode = (ImageView) event.getSource();
        hoverExitNode.setEffect(null);
        label_HoverIconDesc.setText("");
    }
    
    public void onHoverEffectsApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(glow);
    }
    
    public void onClickSave() {
        saveProfileEdits();
    }
    
    public void onClickCancel() {
        setErrorTextField(textfield_NewEmail, true);
        setErrorTextField(textfield_NewFirstName, true);
        setErrorTextField(textfield_NewLastName, true);        
        toggleEditObjectVisibility();
    }
    
    public void onClickEdit() {
        setProfileInformationInTextFields();
        toggleEditObjectVisibility();
    }
    
    public void onClickReturn() throws IOException {
        Stage stage = (Stage) img_Return.getScene().getWindow();
        rabbitfx.businessStage(stage);            
    }
    
    public void onMouseHoldImg(MouseEvent event) {
       ImageView mouseHoldNode = (ImageView) event.getSource();
       onMouseHoldEffectApplyTo(mouseHoldNode);
    }
    
    public void onMouseHoldEffectApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(innershadow);            
    }
    
 /* #########################################################################
  * #   Logic Handling Methods                                              #
    ######################################################################### */
    
    public void saveProfileEdits() {
        if(processProfileEdit()) {
            System.out.println("Business Profile Edit Success.");            
            session.updateUser(thisBusiness);
            toggleEditObjectVisibility();
            getBusinessProfileInformation();
            
        } else {
            System.out.println("Business Profile Edit Fail.");
        }
    }
    
    public boolean processProfileEdit() {
        Text errorText = new Text();
        String
                newBName,
                newEmail,
                newBFName,
                newBLName,
                newAddress,
                newDesc,
                newContactNo,
                newOpenHH,
                newOpenMM,
                newCloseHH,
                newCloseMM;

        Time openHour = null;
        Time closeHour = null;
        
        /* # If Field is Empty, keep it as is - Required. */
        if(fieldIsEmpty(textfield_NewBusinessName)) {
            newBName = thisBusiness.getBusinessName();
        } else {
            newBName = textfield_NewBusinessName.getText();
        }
        if(fieldIsEmpty(textfield_NewEmail)) {
            newEmail = thisBusiness.getEmail();
        } else {
            newEmail = textfield_NewEmail.getText();
        }
        if(fieldIsEmpty(textfield_NewFirstName)) {
            newBFName = thisBusiness.getBusinessOwnerFirstName();
        } else {
            newBFName = textfield_NewFirstName.getText();
        }
        if(fieldIsEmpty(textfield_NewLastName)) {
            newBLName = thisBusiness.getBusinessOwnerLastName();
        } else {
            newBLName = textfield_NewLastName.getText();
        }
        
        /* # If Optionals are empty. 
         * # Optionals have to check if the object is not Null before calling... 
         * # Otherwise you get null pointer exception. */
        if(fieldIsEmpty(textfield_NewAddress)) {
            if(thisBusiness.getAddress()!=null) {
                newAddress = thisBusiness.getAddress();
            } else {
                newAddress = null;
            }
        } else {
            newAddress = textfield_NewAddress.getText();
        }
        if(textarea_NewDesc.getText().isEmpty()) {
            if(thisBusiness.getBusinessDescription()!=null) {
                newDesc = thisBusiness.getBusinessDescription();
            } else {
                newDesc = null;
            }
        } else {
            newDesc = textarea_NewDesc.getText();
        }
        
        if(fieldIsEmpty(textfield_NewContactNo)) {
            if(thisBusiness.getContactNo()!=null) {
                newContactNo = thisBusiness.getContactNo();
            } else {
                newContactNo = null;
            }
        } else {
            newContactNo = textfield_NewContactNo.getText();
        }
        
        if(choicebox_OpenHH.getSelectionModel().isEmpty() || choicebox_OpenMM.getSelectionModel().isEmpty()) {
            if(thisBusiness.getOpeningHours()!=null) {
                openHour = thisBusiness.getOpeningHours();
            } else {
                openHour = null;
            }
        } else {
            newOpenHH = (String) choicebox_OpenHH.getValue();
            newOpenMM = (String) choicebox_OpenMM.getValue();
            openHour = stringToSQLTime(newOpenHH, newOpenMM); 
        }
        
        if(choicebox_CloseHH.getSelectionModel().isEmpty() || choicebox_CloseMM.getSelectionModel().isEmpty()) {
            if(thisBusiness.getClosingHours()!=null) {
                closeHour = thisBusiness.getClosingHours();
            } else {
                closeHour = null;
            }
        } else {
            newCloseHH = (String) choicebox_CloseHH.getValue();
            newCloseMM = (String) choicebox_CloseMM.getValue();
            closeHour = stringToSQLTime(newCloseHH, newCloseMM);            
        }
        return validateProfileEdits(newBName, newEmail, newBFName, newBLName, newAddress, newContactNo, openHour, closeHour, newDesc);
    }    

    public boolean validateProfileEdits(String bname, String email, String fname, String lname, String address, String contactNo, Time open, Time close, String desc) {
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
                    setErrorTextField(textfield_NewEmail, false);
                    return false;
                }
            }
        } else { // These are REQUIRED fields so should never be null.
            return false;
        }
        
        if(fname!=null && lname!=null) {
            if(validation.nameFieldsAreValid(fname, lname, error)) {
            } else {
                setErrorTextField(textfield_NewFirstName, false);
                setErrorTextField(textfield_NewLastName, false);
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
        
        setUserChanges(thisBusiness, bname, email, fname, lname, address, contactNo, open, close, desc);
        
        return true;
    }

    public void setUserChanges(Business user, String bname, String email, String fname, String lname, String address, String contactNo, Time open, Time close, String desc) {
        user.setBusinessName(bname);
        user.setBusinessOwnerFirstName(fname);
        user.setBusinessOwnerLastName(lname);
        user.setEmail(email);
        user.setOpeningHours(open);
        user.setClosingHours(close);
        user.setAddress(address);
        user.setContactNo(contactNo);
        user.setBusinessDescription(desc);
    }
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */
    
    public void setErrorTextField(TextField tf, boolean toggle) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(toggle) { // Remove
            styleClass.removeAll(Collections.singleton("error")); 
        } else { // Add
            styleClass.add("error");
        }       
    }
    
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
    
    public boolean fieldIsEmpty(TextField field) {
        return field.getText().isEmpty();
    }    
      
}
