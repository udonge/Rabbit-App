/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author Reisen
 * # List of labels to inform the user about something.
 */
public enum AlertLabels {
    /* # Helpful Labels for the User. */
    REGISTRATION_USERTYPE_CUSTOMER_DESC("I want to view and make bookings!"),
    REGISTRATION_USERTYPE_BUSINESS_DESC("I want to advertise my open booking slots!"),
    /* # Hover Icon Descriptions. */
    
        /* # Common Descriptions */
        HOVER_DESCRIPTION_LOGOUT("'End your current session.'"),
        HOVER_DESCRIPTION_RETURN("'Return to the previous menu.'"),
        HOVER_DESCRIPTION_ABOUT("'About  Rabbit!'"),
        /* # Customer Descriptions */
        HOVER_DESCRIPTION_MAKE_BOOKING("'Browse businesses and make bookings.'"),
        HOVER_DESCRIPTION_VIEW_BOOKING("'View your bookings and booking history.'"),
        HOVER_DESCRIPTION_VIEW_CUSTOMER_PROFILE("'Manage your personal profile and settings.'"),
        HOVER_DESCRIPTION_EDIT_CUSTOMER_PROFILE("'Edit the details for your profile'"),
        /* # Business Descriptions */
        HOVER_DESCRIPTION_VIEW_BUSINESS_PROFILE("'Manage your business profile and settings.'"),
        HOVER_DESCRIPTION_EDIT_BUSINESS_PROFILE("'Edit the details for your business profile'"),
        HOVER_DESCRIPTION_MANAGE_EMPLOYEES("'Manage your employees and their shifts.'"),
        HOVER_DESCRIPTION_MANAGE_TIMESLOTS("'View and manage your business appointments.'");
    /* # */
    
    private final String alertDescription;

    private AlertLabels(String userDescription) {
        this.alertDescription = userDescription;
    }
    
    public String getUserDescription() {
        return alertDescription;
    }
    
    @Override
    public String toString() {
        return alertDescription;
    }
}
