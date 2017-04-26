/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;


/**
 *
 * @author Reisen
 * # ENUM class holding a string version of characters that are not alphabetical
 * # but require an exception in validation (e.g "Mac O'Reilly")
 * # having it in an ENUM makes it easier for reuse across multiple controllers,
 * # you can add and remove entries here without affecting controllers.
 * # You only need a method to convert this into a String/Char Array.
 * # Sample provided below.
 */
public enum NameFieldExceptions {
    NAME_EXCEPTION_CHAR0("'"), // Brian O'conner
    NAME_EXCEPTION_CHAR1("-"); // Frederick Smith-John
    
    private final String exception;
    private NameFieldExceptions(String exception) {
        this.exception = exception;    
    }
    
    @Override
    public String toString() {
        return exception;
    }
    
    /* # Get all the exception strings for Names from enums.ExceptionClass */
    int instancesOfException = 0;
    /* # Check if char value is legal.*/
    private boolean charIsNotLegal(char[] input) {
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
    }
    
    /* # Get all the exception strings for Names from enums.ExceptionClass */
    private String[] getStringExceptions() {
        String[] listOfExceptions = new String[NameFieldExceptions.values().length];
        int counter = 0;
        for(NameFieldExceptions n : NameFieldExceptions.values()) {
            listOfExceptions[counter] = n.toString();
            counter++;
        }
        return listOfExceptions;           
    }
    private boolean charIsNotAnException(char[] input, char compare) {

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
    }
    
    private char[] turnStringToCharArray(String input) {
        char[] array = input.toCharArray();
        return array;
    }
    private char getFirstCharOfString(String input) {
        char[] array = input.toCharArray();
        return array[0];        
    }    

    
}
