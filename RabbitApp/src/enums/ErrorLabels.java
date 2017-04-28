/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author Reisen
 * # List of labels to produce and inform the user of an error or incorrect action.
 */
public enum ErrorLabels {
    /* # Login Failures */
    LOGIN_FAILURE_NO_FIELDS("Enter email and password."),
    LOGIN_FAILURE_NO_USER("Enter email."),
    LOGIN_FAILURE_NO_PASSWORD("Enter password."),
    LOGIN_FAILURE_INCORRECT("Incorrect details. Try again"),
    /* # Registration Failures */
        /* # Email Failures */
    REGISTRATION_FAILURE_EMAIL_FIELD_EMPTY("Please enter an email."),
    REGISTRATION_FAILURE_EMAIL_FIELD_INVALID("Please enter a valid email."),
    REGISTRATION_FAILURE_EMAIL_FIELD_IN_USE("This email is already registered."),
    
        /* # Password Failures */
    REGISTRATION_FAILURE_PASSWORD_FIELD_EMPTY("Please enter a nice password."),
    REGISTRATION_FAILURE_PASSWORD_FIELD_MINLENGTH("Your password is too short, try 6-24 characters."),
    REGISTRATION_FAILURE_PASSWORD_FIELD_MAXLENGTH("Your password is too long, try 6-24 characters."),
    REGISTRATION_FAILURE_PASSWORD_FIELD_MATCH_NAME("It is unwise to use your name as your password."),
    REGISTRATION_FAILURE_PASSWORD_FIELD_MATCH_EMAIL("It is unwise to use your email as your password."),
    REGISTRATION_FAILURE_CONFIRM_PASSWORD_FIELD_EMPTY("Please confirm your password."),
    REGISTRATION_FAILURE_CONFIRM_PASSWORD_FIELD_INCORRECT("Your passwords do not match."),
    
        /* # First name and Last name Failures: Applies to customer and business. */
    REGISTRATION_FAILURE_NAME_FIELD_EMPTY("Please enter your:"),
    REGISTRATION_FAILURE_NAME_FIELD_INVALID(" contains illegal characters."),
    REGISTRATION_FAILURE_FIRSTNAME_FIELD(" first name"),
    REGISTRATION_FAILURE_LASTNAME_FIELD(" last name"),
    
        /* # Contact No Failures. */
    REGISTRATION_FAILURE_CONTACTNO_FIELD_EMPTY("Please enter a phone number."),
    REGISTRATION_FAILURE_CONTACTNO_FIELD_INVALID("Please enter 10 digits"),
    
        /* # Address Failures. */
    REGISTRATION_FAILURE_ADDRESS_FIELD_EMPTY("Please enter an address."),
    REGISTRATION_FAILURE_ADDRESS_FIELD_INVALID("Please enter a valid address."),
    
        /* # Business Name Errors. */
    REGISTRATION_FAILURE_BUSINESSNAME_FIELD_EMPTY("Please enter a name for your business."),
    REGISTRATION_FAILURE_BUSINESSNAME_FIELD_MAX_LENGTH("Your business name is too long! Limit 64."),
    REGISTRATION_FAILURE_BUSINESSNAME_FIELD_INVALID("Please enter a valid name for your business."),
    REGISTRATION_FAILURE_BUSINESSNAME_FIELD_IN_USE("This business name is already registered."),
    
        /* # Date Of Birth Errors. */
    REGISTRATION_FAILURE_DOB_FIELD_EMPTY("Please enter your date of birth"),
    REGISTRATION_FAILURE_DOB_FIELD_INVALID("Please enter a valid date. DD/MM/YYYY"),
    
    EDIT_PROFILE_FAILURE_SOMETHINGISINVALID("Fill out required fields."),
    
    END_OF_ERRORS("DOORSTOP");
    private final String errorDescription;

    private ErrorLabels(String errorDescription) {
        this.errorDescription = errorDescription;
    }   
    
    @Override
    public String toString() {
        return errorDescription;
    }
}
