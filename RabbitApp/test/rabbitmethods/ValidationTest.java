/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmethods;

import java.util.List;
import javafx.scene.text.Text;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Harry
 */
public class ValidationTest
{
    
    Validation validation = new Validation();
    
    @Test
    public void testEmailIsValid()
    {
        String validEmail = "email@domain.com";
        Session session = new Session();
        Text text = new Text();
        
        assertTrue(validation.emailFieldIsValid(validEmail, text, session)); //valid email
        
        assertFalse(validation.emailFieldIsValid("", text, session)); //blank email
        assertFalse(validation.emailFieldIsValid("email", text, session));
        assertFalse(validation.emailFieldIsValid("@domain.com", text, session));
    }
    
    @Test
    public void testPasswordIsValid()
    {
        String validPassword = "password";
        String firstName = "John";
        String lastName = "Smith";
        String email = "email@domain.com";
        
        Session session = new Session();
        Text text = new Text();
        assertTrue(validation.passwordFieldIsValid(validPassword, validPassword, firstName, lastName, email, text)); //valid
        
        assertFalse(validation.passwordFieldIsValid("", "", firstName, lastName, email, text)); //password fields blank
        assertFalse(validation.passwordFieldIsValid(validPassword, "different", firstName, lastName, email, text)); //unmatching passwords
        assertFalse(validation.passwordFieldIsValid(firstName, firstName, firstName, lastName, email, text)); //matches firstName
        assertFalse(validation.passwordFieldIsValid(lastName, lastName, firstName, lastName, email, text)); //matches lastName
        assertFalse(validation.passwordFieldIsValid(email, email, firstName, lastName, email, text)); //matches email
        
        assertFalse(validation.passwordFieldIsValid("short", "short", firstName, lastName, email, text)); //too short
        assertFalse(validation.passwordFieldIsValid("ThisPasswordIsVeryLongAndShouldNotBeAllowed", "ThisPasswordIsVeryLongAndShouldNotBeAllowed", firstName, lastName, email, text)); //too long
        
    }
    
    @Test
    public void testNameIsValid()
    {
        Session session = new Session();
        Text text = new Text();
        
        String firstName = "John";
        String lastName = "Smith";
        
        assertTrue(validation.nameFieldsAreValid(firstName, lastName, text)); //valid
        
        assertFalse(validation.nameFieldsAreValid(firstName, "", text)); //lastName blank
        assertFalse(validation.nameFieldsAreValid("", lastName, text)); //firstName blank
        assertFalse(validation.nameFieldsAreValid("", "", text)); //both blank
        
        assertFalse(validation.nameFieldsAreValid("123", lastName, text)); //first name numeric
        assertFalse(validation.nameFieldsAreValid(firstName, "123", text)); //last name numeric
        
        assertFalse(validation.nameFieldsAreValid("J0hn", lastName, text)); //firstName contains number
        assertFalse(validation.nameFieldsAreValid(firstName, "Sm1th", text)); //lastName contains number
            }
    
    @Test
    public void testBusinessNameIsValid()
    {
        Session session = new Session();
        Text text = new Text();
        
        String validName = "Business";
        
        assertTrue(validation.businessNameIsValid(validName, text));
        
        assertFalse(validation.businessNameIsValid("", text)); //blank and/or too short
        assertFalse(validation.businessNameIsValid("ThisBusinessNameIsLudicrouslyLongToTestIfTheFunctionRejectsItWhenTheLengthIsOverSixtyFourCharacters", text)); //too long
        
    }
    
    @Test
    public void testContactNoIsValid()
    {
        Session session = new Session();
        Text text = new Text();
        
        String validNumber = "0412345678";
        
        assertTrue(validation.contactNoIsValid(validNumber, text)); //valid
        assertFalse(validation.contactNoIsValid("", text)); //blank
        assertFalse(validation.contactNoIsValid("041234", text)); //too short
        assertFalse(validation.contactNoIsValid("04123456789", text)); //too long
        assertFalse(validation.contactNoIsValid("abcdefghij", text)); //all non-numeric
        assertFalse(validation.contactNoIsValid("041234abcd", text)); //contains non-numeric
    }
    
    @Test
    public void testDOBIsValid()
    {
        Session session = new Session();
        Text text = new Text();
        String day = "20";
        String month = "07";
        String year = "1997";
        
        assertTrue(validation.dateOfBirthIsValid(day, month, year, text));
        
        assertFalse(validation.dateOfBirthIsValid("", month, year, text)); //day blank
        assertFalse(validation.dateOfBirthIsValid(day, "", year, text)); //month blank
        assertFalse(validation.dateOfBirthIsValid(day, month, "", text)); //year blank
        
        assertFalse(validation.dateOfBirthIsValid("32", month, year, text)); //day invalid
        assertFalse(validation.dateOfBirthIsValid(day, "13", year, text)); //month invalid
        assertFalse(validation.dateOfBirthIsValid(day, month, "2020", text)); //year invalid
        
        
    }
}
