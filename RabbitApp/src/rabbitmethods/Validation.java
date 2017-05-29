/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmethods;

import enums.ErrorLabels;
import enums.NameFieldExceptions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.scene.text.Text;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Reisen
 * # Validation Methods class, so that you can call the methods from Registration AND editing.
 * # Note that some methods require the Session. 
 */
public class Validation {
    
 /* #########################################################################
  * #   Validation Methods                                                  #
    ######################################################################### */    
    
    public boolean emailFieldIsValid(String email, Text errorText, Session session) {
        if(email.isEmpty()) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_EMAIL_FIELD_EMPTY.toString());
            return false;
        }
        /* Email validation. */
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            errorText.setText("Invalid email! <email>@<domain>");
            return false;
        }      
        /* # If Email is already registered. */
        if(session.emailAlreadyExists(email)) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_EMAIL_FIELD_IN_USE.toString());
            return false;
        }
        errorText.setText("");
        return true;
    } // End emailFieldIsValid    
    
    public boolean passwordFieldIsValid(String password, String confirmPassword, String firstName, String lastName, String email, Text errorText) {
        int passwordMinLength = 6;
        int passwordMaxLength = 24;
        
        if(password.isEmpty()) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_PASSWORD_FIELD_EMPTY.toString());
            return false;
        }
        if(confirmPassword.isEmpty()) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_CONFIRM_PASSWORD_FIELD_EMPTY.toString());
            return false;
        }
        if(!(confirmPassword.equals(password))) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_CONFIRM_PASSWORD_FIELD_INCORRECT.toString());
            return false;
        }
        if(password.equals(firstName) || password.equals(lastName)) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_PASSWORD_FIELD_MATCH_NAME.toString());
            return false;
        }
        if(password.equals(email)) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_PASSWORD_FIELD_MATCH_EMAIL.toString());
            return false;
        }
        if(password.length()<passwordMinLength) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_PASSWORD_FIELD_MINLENGTH.toString());
            return false;            
        }
        if(password.length()>passwordMaxLength) {
            errorText.setText(ErrorLabels.REGISTRATION_FAILURE_PASSWORD_FIELD_MAXLENGTH.toString());
            return false;            
        }
        errorText.setText("");
        return true;
    } // End passwordFieldIsValid

    public boolean nameFieldsAreValid(String fname, String lname, Text errorLabel) {
        /* # This is abit tricky, due to both fields sharing one line, the error message must be constructed. */
        String firstName = fname;
        String lastName = lname;
        char[] firstNameToken = turnStringToCharArray(firstName);
        char[] lastNameToken = turnStringToCharArray(lastName);
        
        /* # We iterate through this List to form an error message. */
        List<String> constructNameError = new ArrayList<>();
        
        if(firstName.isEmpty() || lastName.isEmpty()) {
            constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_NAME_FIELD_EMPTY.toString());
            if(firstName.isEmpty()) {
                constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_FIRSTNAME_FIELD.toString());
            }
            if(lastName.isEmpty()) {
                constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_LASTNAME_FIELD.toString());
            }
            deconstructFrontListToMessage(constructNameError, errorLabel);
            return false;
        }
        if(charIsNotAlphabetic(firstNameToken) || charIsNotAlphabetic(lastNameToken)) {
            constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_NAME_FIELD_INVALID.toString());
            if(charIsNotAlphabetic(lastNameToken)) {
                constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_LASTNAME_FIELD.toString());
            }            
            if(charIsNotAlphabetic(firstNameToken)) {
                constructNameError.add(ErrorLabels.REGISTRATION_FAILURE_FIRSTNAME_FIELD.toString());
            }
            deconstructEndListToMessage(constructNameError, errorLabel);
            return false;
        }
        errorLabel.setText("");
        return true;
    } // End nameFieldsAreValid
 
    public boolean businessNameIsValid(String name, Text error) {
        if(name.isEmpty()) {
            error.setText(ErrorLabels.REGISTRATION_FAILURE_BUSINESSNAME_FIELD_EMPTY.toString());
            return false;
        }
        if(name.length() < 1 || name.length() > 64) {
            error.setText(ErrorLabels.REGISTRATION_FAILURE_BUSINESSNAME_FIELD_MAX_LENGTH.toString());
            return false;
        }
        return true;
    } // End businessNameIsValid
    
    public boolean contactNoIsValid(String contact, Text error) {
        String contactNo = contact;
        if(contactNo.isEmpty()) {           
            return false;
        }      
        if(charIsNotNumeric(turnStringToCharArray(contactNo))) {
            error.setText(ErrorLabels.REGISTRATION_FAILURE_CONTACTNO_FIELD_INVALID.toString());
            return false;
        }
        if(contactNo.length()!=10) {
            error.setText(ErrorLabels.REGISTRATION_FAILURE_CONTACTNO_FIELD_INVALID.toString());
            return false;                        
        }
        return true;
    } // End contactNoIsValid

    public boolean dateOfBirthIsValid(String day, String month, String year, Text errorText) {
        if(
        day.isEmpty() &&
        month.isEmpty() &&
        year.isEmpty()) {
            return true;
            
        }
        else if(dateIsValid(day,month,year)) {
            if(Integer.parseInt(year) >= Calendar.getInstance().get(Calendar.YEAR))
            {
                errorText.setText("Year must be prior to " + Calendar.getInstance().get(Calendar.YEAR));
                return false;
            }
            else
            {
                errorText.setText("");
                return true;
            }
        } 
        
        
        else {
            return false;
        }
        
        
    } // End of dateOfBirthIsValid
    
    public boolean dateIsValid(String day, String month, String year) {
        /* # This method is marked for replacement, Choicebox Should be used instead. */
        /* # Enforce DD / MM / YYYY */
        if(day.length() > 2 || month.length() > 2 || year.length() > 4) {            
            return false;
        }
        if(!dateIsNumeric(day, month, year)) {           
            return false;
        }
        if(day.isEmpty() || month.isEmpty() || year.isEmpty() ) {
            return false;
        }

        int dayInt = Integer.parseInt(day);
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        switch(month) {
            /* # 31 Day Months */
            case "01": // Jan
            case "1" :
            case "03": // March
            case "3" :
            case "05": // May
            case "5" :
            case "07": // July
            case "7" :
            case "08": // August
            case "8" :
            case "10": // October
            case "12": // December
                return !(dayInt > 31 || dayInt < 1);
            /* # 30 Day Months */
            case "04": // April
            case "4" :
            case "06": // June
            case "6" :
            case "09": // September
            case "9" :
            case "11": // November
                return !(dayInt > 30 || dayInt < 1);
            /* # 28 Day Months */
            case "02": // February
            case "2" :
                if( (yearInt%400 == 0) || (yearInt % 4 == 0) && (yearInt % 100 != 0)) {
                    /* # Consider a leap year... */
                    return !(dayInt > 29 || dayInt < 1);                    
                } else {
                    return !(dayInt > 28 || dayInt < 1);
                }
            default : 
                return false;
        }        
    } // End dayIsValid
    
    public boolean dateIsNumeric(String day, String month, String year) {
        if(charIsNotNumeric(turnStringToCharArray(day))) {
            return false;
        }
        if(charIsNotNumeric(turnStringToCharArray(month))) {
            return false;
        }
        if(charIsNotNumeric(turnStringToCharArray(year))) {
            return false;
        }
        
        return true;               
    } // End dateIsNumeric
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */ 
    
    public void deconstructFrontListToMessage(List<String> list, Text errorLabel) {
        /* Error message is at the front. */
        String errorMessage = null;
        if(list.isEmpty()) {
            return;
        }
        for(int i = 0 ; i < list.size() ; i++ ) {
            if(i==0) {
                /* # First String segment */
                errorMessage = list.get(i);
            } else {
                /* # If not first, add onto the string. */
                errorMessage = errorMessage + list.get(i);
                if(i!=list.size()-1) {
                    /* # Determine comma placement. */
                    errorMessage = errorMessage + ",";
                } else {
                    errorMessage = errorMessage + ".";
                }
            }           
        }        
        errorLabel.setText(errorMessage);
    } // End deconstructFrontListToMessage
    
    public void deconstructEndListToMessage(List<String> list, Text errorLabel) {
        /* Error message is at the end. */
        String errorMessage = null;
        if(list.isEmpty()) {
            return;
        }
        /* Just do it backwards lol.*/
        for(int i =  0 ; i < list.size() ; i++ ) {
            if(i==0) {
                /* # Assigning error message */
                errorMessage = list.get(i);
            } else {
                if(i!=list.size()-1) {
                    errorMessage = "," + list.get(i) + errorMessage; 
                } else {
                    /* # Make the first letter of the string capitilised. */
                    String newString = list.get(i).substring(1,2).toUpperCase() + list.get(i).substring(2);
                    errorMessage =  newString + errorMessage;
                }
            }
        }
        errorLabel.setText(errorMessage);
    } // End deconstructEndListToMessage
    
    int instancesOfException = 0;
    /* # Check if char value is alphabetic.*/
    public boolean charIsNotAlphabetic(char[] input) {
        /* # Make exceptions for certain chars */
        /* # Reset word being counted. */     
        instancesOfException = 0;
        String[] exceptions = getStringExceptions();
        char[] exceptionArray = new char[exceptions.length];
        for(int i = 0; i < exceptions.length ; i++) {
            char exceptionChar = getFirstCharOfString(exceptions[i]);
            exceptionArray[i] = exceptionChar;      
        }
        
        /* # Prevent exception from being the first letter.*/
        if((charIsNotAnException(exceptionArray, input[0]))) {
            return true;
        }
        /* # Prevent exception from being the last letter.*/
        if((charIsNotAnException(exceptionArray, input[input.length-1]))) {
            return true;
        }
        for(char i : input) {
            if(!(Character.isAlphabetic(i))) {
                if(!(charIsNotAnException(exceptionArray, i))) {
                    return true;
                }
            }          
        }  
        return false;
    } // End charIsNotAlphabetic
    
    public boolean charIsNotNumeric(char[] input) {
        for(char i : input) {
            if(!Character.isDigit(i)) {
                return true;
            }
        }
        return false;
    } // End charIsNumeric
    
    /* # Get all the exception strings for Names from enums.ExceptionClass */
    public String[] getStringExceptions() {
        String[] listOfExceptions = new String[NameFieldExceptions.values().length];
        int counter = 0;
        for(NameFieldExceptions n : NameFieldExceptions.values()) {
            listOfExceptions[counter] = n.toString();
            counter++;
        }
        return listOfExceptions;           
    } // End getStringExceptions
    
    public boolean charIsNotAnException(char[] input, char compare) {

        if(instancesOfException>0) {
            /* # Prevent multiple exceptions in one word (e.g '' or o'm'ly) 
             * # It is annoying, be considerate and this is the crap they pull. */
            return false;
        }
        for(int i = 0 ; i < input.length ; i++) {
            if(input[i]==compare) {
                instancesOfException++;
                return true;                
            }
        }
        return false;       
    } // End charIsNotAnException
    
    public static char[] turnStringToCharArray(String input) {
        char[] array = input.toCharArray();
        return array;
    }
    public char getFirstCharOfString(String input) {
        char[] array = input.toCharArray();
        return array[0];        
    } // End turnStringToCharArray    
}
