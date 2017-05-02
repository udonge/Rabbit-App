/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmethods;

import java.text.SimpleDateFormat;

/**
 *
 * @author Reisen
 */
public class Formatters {
    
    public static SimpleDateFormat formatDateToStringDDMM() {
        return new SimpleDateFormat("DD/MM");
    }
    
    public static SimpleDateFormat formatDateToStringDDMMYY() {
        return new SimpleDateFormat("DD/MM/YYYY");
    }
    
    public static SimpleDateFormat  formatDateToStringddMMEEEE() {
        return new SimpleDateFormat("dd/MM, EEEE");
    }
    
    public static SimpleDateFormat formatDateToStringEEEE() {
        return new SimpleDateFormat("EEEE");
    }
    
    public static SimpleDateFormat formatTimeToStringHHmm() {
        return new SimpleDateFormat("HH:mm");
    }   
    
    
}
