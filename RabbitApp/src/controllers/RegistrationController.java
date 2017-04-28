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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitmethods.Validation;
import rabbitobjects.Business;
import rabbitobjects.Customer;

/**
 *
 * @author Reisen
 */
public class RegistrationController implements  Initializable {
    
    RabbitFX rabbitfx;
    Session session;
    Validation validation = new Validation();
    
    @FXML
 /* #########################################################################
  * #   DECLARE JAVAFX OBJECTS                                              #
    ######################################################################### */
    public RadioButton 
            radiobtn_SelectCustomer, 
            radiobtn_SelectBusiness;
    
    public Button 
        /* # Customer Buttons */
            btn_CustomerReturn, 
            btn_CustomerRegister,
        /* # Business Buttons */
            btn_BusinessReturn, 
            btn_BusinessRegister;
    
    public Pane 
            customerPane, 
            businessPane;
    
    public Label 
            label_RabbitTitle, 
            label_SelectUserText, 
            label_UserTypeDesc,
            label_CustomerFinishedForm,
            label_BusinessFinishedForm;
    
    public PasswordField 
            passwordfield_Password, 
            passwordfield_ConfirmPassword;

    public Text 
        /* # Common Text */
            text_PasswordError, 
            text_EmailError, 
            text_DateOfBirthError,
            text_Success,
        /* # Customer Text */
            text_CustomerNameError,
            text_CustomerAddressError, 
            text_CustomerContactNoError,
        /* # Business Text */
            text_BusinessNameError, 
            text_BusinessAddressError, 
            text_BusinessOwnerError, 
            text_BusinessContactNoError;
    
    public TextField 
        /* # Common Fields*/
            textfield_RegisterEmail,
        /* # Customer Fields */
            textfield_CustomerFirstName, 
            textfield_CustomerLastName, 
            textfield_CustomerAddress, 
            textfield_CustomerContactNo,
            textfield_Day,
            textfield_Month,
            textfield_Year,
        /* # Business Fields */
            textfield_BusinessName,
            textfield_BusinessFirstName, 
            textfield_BusinessLastName, 
            textfield_BusinessContactNo,
            textfield_BusinessAddress;
    
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert label_CustomerFinishedForm != null : "fx:id=\"label_CustomerFinishedForm\" was not injected: check your FXML file 'registration.fxml'.";
        assert label_BusinessFinishedForm != null : "fx: id=\"label_BusinessFinishedForm\" was not injected: check your FXML file 'registration.fxml'.";
        
    }
    public void setSession(Session session) {
        this.session = session;
    }
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */
    
    /* # Toggle between forms depending on Radio Button selected Customer or Business. */
    public void onSelectRegistrationForm() {
        toggleViewPane(customerPane, businessPane);
        resetUserText();  
    } // End onSelectRegistrationForm
    
    public void resetUserText() {
        textfield_RegisterEmail.setText("");
        passwordfield_Password.setText(""); 
        passwordfield_ConfirmPassword.setText("");
        text_Success.setVisible(false);
        if(radiobtn_SelectCustomer.isSelected()) {
            label_UserTypeDesc.setText("'" + AlertLabels.REGISTRATION_USERTYPE_CUSTOMER_DESC + "'");
        }
        if(radiobtn_SelectBusiness.isSelected()) {
            label_UserTypeDesc.setText("'" + AlertLabels.REGISTRATION_USERTYPE_BUSINESS_DESC + "'");
        }
        label_CustomerFinishedForm.setVisible(false);
        label_BusinessFinishedForm.setVisible(false);
        text_EmailError.setText("");
        text_PasswordError.setText("");
        text_CustomerNameError.setText("");
        text_CustomerAddressError.setText("");
        text_CustomerContactNoError.setText("");
        text_DateOfBirthError.setText("");        
        text_BusinessNameError.setText(""); 
        text_BusinessAddressError.setText("");
        text_BusinessOwnerError.setText(""); 
        text_BusinessContactNoError.setText("");        
        textfield_CustomerFirstName.setText("");
        textfield_CustomerLastName.setText(""); 
        textfield_CustomerAddress.setText("");
        textfield_CustomerContactNo.setText("");
        textfield_Day.setText("");
        textfield_Month.setText("");
        textfield_Year.setText("");
        textfield_BusinessName.setText("");
        textfield_BusinessFirstName.setText(""); 
        textfield_BusinessLastName.setText(""); 
        textfield_BusinessContactNo.setText("");
        textfield_BusinessAddress.setText("");
    } // End resetUserText
    
 /* #########################################################################
  * #   Customer Registration Logic                                         #
    ######################################################################### */
    public boolean validateCustomerTextFields() {
        String fname = textfield_CustomerFirstName.getText();
        String lname = textfield_CustomerLastName.getText();
        String email = textfield_RegisterEmail.getText();
        String password = passwordfield_Password.getText();
        String confirmPassword = passwordfield_ConfirmPassword.getText();  
        String contact = textfield_CustomerContactNo.getText();        
        String day = textfield_Day.getText();
        String month = textfield_Month.getText();
        String year = textfield_Year.getText();
        Text nameError = text_CustomerNameError;
        Text emailError = text_EmailError;
        Text passwordError = text_PasswordError;
        Text contactError = text_CustomerContactNoError;
        Text dateError = text_DateOfBirthError;
        
        int requirementCheck = 0; // Use this to determine if requirements are met and optionals if any, are valid.
        if(validation.emailFieldIsValid(email, emailError, session)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;
        }
        if(validation.passwordFieldIsValid(password, confirmPassword, fname, lname, email, passwordError)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;            
        }
        if(validation.nameFieldsAreValid(fname, lname, nameError)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;
        }
        if(validation.dateOfBirthIsValid(day, month, year, dateError)) {
            
        } else {
            text_DateOfBirthError.setText(ErrorLabels.REGISTRATION_FAILURE_DOB_FIELD_INVALID.toString());
            requirementCheck = requirementCheck - 1;
        }
        if(validation.contactNoIsValid(contact, contactError)) {
            text_CustomerContactNoError.setText("");
        } else {  
            requirementCheck = requirementCheck - 1;
        }
        return requirementCheck>2;
    } // End validateCustomerTextFields
    
    public void onClickCustomerConfirm() {
        /* # Run textfield checks for appropriate selections. */
        if(validateCustomerTextFields()) {
            registerCustomer();
        } else {
            System.out.println("Customer Registration Failure.");
        }
    } // End onClickCustomerConfirm
    
    public void registerCustomer() {
        /* # This is valid so lets get their values. */
        /* # Required */
        String id = session.generateID("C");
        String password = passwordfield_Password.getText();
        String email = textfield_RegisterEmail.getText();
        String firstName = textfield_CustomerFirstName.getText();
        String lastName = textfield_CustomerLastName.getText();
        /* # Optionals */
        String address = null;
        String contactNo = null;
        Date dateOfBirth = null;
        /* # Check for optionals */
        if(!textfield_CustomerAddress.getText().isEmpty()) {
            address = textfield_CustomerAddress.getText();
        }
        if(!textfield_CustomerContactNo.getText().isEmpty()) {
            contactNo = textfield_CustomerContactNo.getText();
        }
        if(!textfield_Day.getText().isEmpty() && !textfield_Month.getText().isEmpty() && !textfield_Year.getText().isEmpty()) {
            String date = textfield_Day.getText() + "-" + textfield_Month.getText() + "-" + textfield_Year.getText();
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                java.util.Date convertDate = sdf1.parse(date);
                dateOfBirth = new java.sql.Date(convertDate.getTime());
            } catch(ParseException error) {
                System.out.println(error.getMessage());
            }
        }
        Customer customer = new Customer(id,password, email, address, contactNo, firstName, lastName, dateOfBirth, null);
        session.users.add(customer);
        session.saveToDatabase(customer);
        resetUserText();
        text_Success.setVisible(true);
    } // End registerCustomer
    
 /* #########################################################################
  * #   Business Registration Logic                                         #
    ######################################################################### */

    public boolean validateBusinessTextFields() {
        String fname = textfield_BusinessFirstName.getText();
        String lname = textfield_BusinessLastName.getText();
        String email = textfield_RegisterEmail.getText();
        String password = passwordfield_Password.getText();
        String confirmPassword = passwordfield_ConfirmPassword.getText();        
        String bName = textfield_BusinessName.getText();
        String contact = textfield_BusinessContactNo.getText();
        Text emailError = text_EmailError;
        Text nameError = text_BusinessOwnerError;
        Text passwordError = text_PasswordError;
        Text bnameError = text_BusinessNameError;
        Text contactError = text_BusinessContactNoError;
        int requirementCheck = 0; // Use this to determine if requirements are met and optionals if any, are valid.
        if(validation.emailFieldIsValid(email, emailError, session)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;
        }
        if(validation.passwordFieldIsValid(password, confirmPassword, fname, lname, email, passwordError)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;            
        }
        if(validation.nameFieldsAreValid(fname, lname, nameError)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;
        }
        /* # Note that business requires an extra step to register. */
        if(validation.businessNameIsValid(bName, bnameError)) {
            requirementCheck = requirementCheck + 1;
        } else {
            requirementCheck = requirementCheck - 1;
        }
        if(validation.contactNoIsValid(contact, contactError)) {
            text_BusinessContactNoError.setText("");
        } else {  
            requirementCheck = requirementCheck - 1;
        }
        return requirementCheck>3;
    } // End validateCustomerTextFields
    
    public void onClickBusinessConfirm() {
        /* # Run textfield checks for appropriate selections. */
        if(validateBusinessTextFields()) {
            registerBusiness();
        } else {
            System.out.println("Business Registration Failure.");
        }
  
    }
    
    public void registerBusiness() {
        String id = session.generateID("B");
        String password = passwordfield_Password.getText();
        String email = textfield_RegisterEmail.getText();
        String firstName = textfield_BusinessFirstName.getText();
        String lastName = textfield_BusinessLastName.getText();
        String businessName = textfield_BusinessName.getText();
        /* # Optionals */
        String address = null;
        String contactNo = null;
        if(!textfield_BusinessAddress.getText().isEmpty()) {
            address = textfield_BusinessAddress.getText();
        }
        if(!textfield_BusinessContactNo.getText().isEmpty()) {
            contactNo = textfield_BusinessContactNo.getText();
        }
        Business business = new Business(id,password, email, address, contactNo, businessName, firstName, lastName, null, null, null, false);
        session.users.add(business);
        session.saveToDatabase(business);
        resetUserText();
        text_Success.setVisible(true);        
    }
    
 /* #########################################################################
  * #   Scene Traversal Methods                                             #
    ######################################################################### */    
    
    /* # Return to Login screen. */
    public void onClickReturn() throws IOException {
        /* # btn_CustomerReturn and btn_BusinessReturn are functionally the same. */
        Stage stage = (Stage) btn_CustomerReturn.getScene().getWindow();      
        rabbitfx.loginStage(stage);        
    } //End onClickReturn
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */
    
    public void toggleViewPane(Pane pane1, Pane pane2) {
        pane1.setVisible(!pane1.isVisible());
        pane2.setVisible(!pane2.isVisible());
    } //End toggleViewPane
    
    
}
