/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitobjects.Business;
import rabbitobjects.Employee;
import rabbitobjects.Timeslot;

/**
 *
 * @author Reisen
 */
public class BusinessManageTimeslotController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    Glow glow = new Glow();
    Image nodeDisabled = new Image("/GUI/fxml/assets/logo/logo_inactive.png");
    Image nodeDefault = new Image("/GUI/fxml/assets/logo/logo_doubleflop.png");
    Image nodeFullHour = new Image("/GUI/fxml/assets/logo/logo_original.png");
    Image node00Hour = new Image("/GUI/fxml/assets/logo/logo_rightflop.png");
    Image node30Hour = new Image("/GUI/fxml/assets/logo/logo_leftflop.png");
    Business thisBusiness;
    List<ImageView> selection = new ArrayList<>();
    public String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    boolean[] daysSelected;
    List<Image> profilepics = new ArrayList<>();
    Employee thisEmployee;
    
    
    @FXML
 /* #########################################################################
  * #   Declare JavaFX Objects                                              #
    ######################################################################### */               
    public TextField
            textfield_ActivityDesc;
    
    public Text
            text_AMorPM,
            text_EmployeeName,
            text_HoursOfOperation,
            text_EmployeeDesc;
    
    public Button
            btn_Save;
    
    public ChoiceBox<String>
            choicebox_SelectEmployee;
    
    public Label
            label_HoverIconDesc;
    
    public RadioButton
            radiobtn_Sunday,
            radiobtn_Monday,
            radiobtn_Tuesday,
            radiobtn_Wednesday,
            radiobtn_Thursday,
            radiobtn_Friday,
            radiobtn_Saturday,
            radiobtn_AM,
            radiobtn_PM;
    
    public ToggleButton
            toggle_FullHour,
            toggle_HalfHour00,
            toggle_HalfHour30;
    
    public ImageView
            img_ProfilePicture,
            img_Logo,
            img_00Hour,
            img_01Hour,
            img_02Hour,
            img_03Hour,
            img_04Hour,
            img_05Hour,
            img_06Hour,
            img_07Hour,
            img_08Hour,
            img_09Hour,
            img_10Hour,
            img_11Hour,
            img_12Hour,
            img_13Hour,
            img_14Hour,
            img_15Hour,
            img_16Hour,
            img_17Hour,
            img_18Hour,
            img_19Hour,
            img_20Hour,
            img_21Hour,
            img_22Hour,
            img_23Hour,
            img_Return;
    
    
    
 /* #########################################################################
  * #   Constructor Methods                                                 #
    ######################################################################### */    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }

    public void setProfilePictureChoices() {
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_bear.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_bird.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_boy.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_flower.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_fox.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_girlA.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_girlB.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_leaf.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_lotus.png"));
        profilepics.add(new Image("/GUI/fxml/assets/profile/profile_turtle.png"));
    }
           
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        this.session = session;
        this.thisBusiness = (Business) session.currentUser;
        this.daysSelected = thisBusiness.getDaysOpen();
    }
    
    
    List<ImageView> hoursAM = new ArrayList<>();
    List<ImageView> hoursPM = new ArrayList<>();    
    public void setTimeslotList() {
        /* # Hour Lists */
        /* # Place the hour images into a list. */   
        hoursAM.add(img_00Hour);
        hoursAM.add(img_01Hour);
        hoursAM.add(img_02Hour);
        hoursAM.add(img_03Hour);
        hoursAM.add(img_04Hour);
        hoursAM.add(img_05Hour);
        hoursAM.add(img_06Hour);
        hoursAM.add(img_07Hour);
        hoursAM.add(img_08Hour);
        hoursAM.add(img_09Hour);
        hoursAM.add(img_10Hour);
        hoursAM.add(img_11Hour);
        /* # The list displayed depends on radio selection. */
        hoursPM.add(img_12Hour);
        hoursPM.add(img_13Hour);
        hoursPM.add(img_14Hour);
        hoursPM.add(img_15Hour);
        hoursPM.add(img_16Hour);
        hoursPM.add(img_17Hour);
        hoursPM.add(img_18Hour);
        hoursPM.add(img_19Hour);
        hoursPM.add(img_20Hour);
        hoursPM.add(img_21Hour);
        hoursPM.add(img_22Hour);
        hoursPM.add(img_23Hour);
        /* # Menu starts at AM.*/
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
        String open = s.format(thisBusiness.getOpeningHours());
        String close = s.format(thisBusiness.getClosingHours());
        text_HoursOfOperation.setText(open + " : " + close);
        setAvailableHours(true);
        toggleAMorPM();        
    }
    
    public void toggleAMorPM() {
        /* # As you toggle, make one set visible, and the other invisible. */
        toggleList(hoursAM);
        toggleList(hoursPM);
    }
    
    public void toggleList(List<ImageView> list) {
        list.forEach((node) -> {
            if(selection.contains(node)) {
                /* # If this node is in the selection list. */
                nodeStateSelected(node);
            } else {
                /* # If it is not selected. */
                if(node.isDisabled()) {
                    /* # Is it disabled? */
                    nodeStateDisabled(node);
                } else {
                    /* # Enabled but not selected. */
                    nodeStateDefault(node);
                }
            }
            node.setVisible(!node.isVisible());
        });
        
    }
    
    public void nodeStateSelected(ImageView node) {
        node.setOpacity(1);
        node.setEffect(glow);
        node.setImage(nodeFullHour);
    }
    
    public void nodeStateDisabled(ImageView node) {
        node.setOpacity(0.4);
        node.setImage(nodeDisabled);        
} 
    
    public void nodeStateDefault(ImageView node) {
        node.setOpacity(0.8);
        node.setImage(nodeDefault);        
    }

    public void setAvailableHours(Boolean amORpm) {
        /* # Enable the hours that are within the business opening hours. */
        Time open = thisBusiness.getOpeningHours();
        Time close = thisBusiness.getClosingHours();
        
        Time amStart = new Time(00,00,00);
        Time pmStart = new Time(12,00,00);
        Time amStop = new Time(12,00,01);
        Time pmStop = new Time(00,00,01);
            
        /* amORpm - True = AM, False = PM */
        if(open==close) {
            /* # If 24 Hours... */
            if(amORpm) {
                enableHourNode(hoursAM, amStart, amStop);
            } else {
                enableHourNode(hoursPM, pmStart, null);
            }
            return;
        }        
        if(close.before(open)) {
            /* PM rolling over to AM. */
            if(amORpm) {
                enableHourNode(hoursAM, amStart, close); // Starting at 00:00 up to Close.          
            } else {              
                enableHourNode(hoursPM, open, pmStop); // Starting from Open up to 23:59.
            }
            return;
        }
        if(amORpm) {
            /* # View AM set, where AM rolls over to PM */
            if(close.after(pmStart)) {
                enableHourNode(hoursAM, open, amStop); // Starting from Open up to 11:59.
            } else {
                enableHourNode(hoursAM, open, close); // Starting from Open up to Close. (AM)
            }
            return;
        }
        if(!amORpm) {
            /* # View PM set. */
            if(open.before(pmStart)) { // Open is AM
                if(close.after(pmStart)) { // But Close is PM
                    enableHourNode(hoursPM, pmStart, close); // Starting from 12:00 up to Close.                    
                } else { // Close is not in PM therefore, nothing.
                    enableHourNode(hoursPM, amStart,amStart);
                }                             
            } else { // Open is also in PM.
                enableHourNode(hoursPM, open, close); // Starting from Open up to Close. (PM)
            }
        }              
    }
    
    public void enableHourNode(List<ImageView> list, Time start, Time end) {
        int s = start.getHours();
        int e = end.getHours();
        boolean foundStartNode = false;
        
        for(ImageView node : list) {
            int v = getHourThatObjectRepresents(turnStringToCharArray(node.getId()));
            if(v==s) {
                /* # We have found our starting point. */
                enableNode(node);
                foundStartNode = true;
                continue;
            }
            if(foundStartNode) {
                if(v==e) {
                    /* # We have reached the End time. */
                    return;
                } else {
                    enableNode(node);
                }
            }
        }        
    }
        
    public void enableNode(ImageView node) {
        node.setDisable(false);     
    }
    
    public void disableNode(ImageView node) {
        node.setDisable(true);
    }
    
    public void setPossibleDays() {
        boolean[] daysOpen = thisBusiness.getDaysOpen();
        for(int i = 0 ; i < daysOpen.length ; i++ ) {
            if(daysOpen[i]) {
                switch(i) {
                    case 0: // Sunday
                        radiobtn_Sunday.setDisable(false);
                        radiobtn_Sunday.setSelected(true);
                        break;
                    case 1: // Monday
                        radiobtn_Monday.setDisable(false);
                        radiobtn_Monday.setSelected(true);
                        break;
                    case 2: // Tuesday
                        radiobtn_Tuesday.setDisable(false);
                        radiobtn_Tuesday.setSelected(true);
                        break;
                    case 3: // Wednesday
                        radiobtn_Wednesday.setDisable(false);
                        radiobtn_Wednesday.setSelected(true);
                        break;
                    case 4: // Thursday
                        radiobtn_Thursday.setDisable(false);
                        radiobtn_Thursday.setSelected(true);
                        break;
                    case 5: // Friday
                        radiobtn_Friday.setDisable(false);
                        radiobtn_Friday.setSelected(true);
                        break;
                    case 6: // Saturday
                        radiobtn_Saturday.setDisable(false);
                        radiobtn_Saturday.setSelected(true);
                        break;
                    default: // Wtf?
                        break;
                }
            }
        }
    }
    
    public void setEmployeeList() {
        thisBusiness.getListOfEmployees().stream().filter((e) -> (e.getEID()!=null)).forEachOrdered((e) -> {
            choicebox_SelectEmployee.getItems().add(e.getEID());
        });
        choicebox_SelectEmployee.getSelectionModel().selectedIndexProperty().addListener
        (new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                thisEmployee = thisBusiness.getListOfEmployees().get(new_value.intValue());
                img_ProfilePicture.setImage(profilepics.get(thisBusiness.getListOfEmployees().get(new_value.intValue()).getProfilePicture()));
                text_EmployeeName.setText(thisEmployee.getEmployeeFirstName() + " " + thisEmployee.getEmployeeLastName());
                text_EmployeeDesc.setText(thisEmployee.getEmployeeDesc());
                
            }
        });  
        choicebox_SelectEmployee.getSelectionModel().selectFirst();
    }
    
    
 /* #########################################################################
  * #   Design Methods                                                      #
    ######################################################################### */
    
    public void setGlow(ImageView img) {
        img.setEffect(glow);
    }

    
 /* #########################################################################
  * #   On Click Methods                                                    #
    ######################################################################### */
    
    public void onClickHour(MouseEvent event) {
        /* # When user clicks on an Hour image. */
        ImageView clickedImage = (ImageView) event.getSource();
        if(!imageIsAlreadySelected(clickedImage)) {
            addSelectedHourToList(clickedImage);
        } else {
            removeSelectedHourFromList(clickedImage);
        }
 
        /* # Some effect so we know it has been clicked. */      
    }
    
    public void onSelectAMorPM() {
        if(radiobtn_AM.isSelected()) {
            /* # First reset the states. */
            setAvailableHours(true);
            toggleAMorPM();
            text_AMorPM.setText("AM");
        }
        if(radiobtn_PM.isSelected()) {
            /* # First reset the states. */
            setAvailableHours(false);
            toggleAMorPM();
            text_AMorPM.setText("PM");
        }
    }
    
    public void onClickSave() {
        String thisEID = choicebox_SelectEmployee.getSelectionModel().getSelectedItem();
        generateTimeslots();
    }
    
    public void onClickReturn() throws IOException {
        Stage stage = (Stage) img_Return.getScene().getWindow();
        rabbitfx.businessStage(stage);
    }
    
    
 /* #########################################################################
  * #   Logic Handling                                                      #
    ######################################################################### */
    
    public int getHourThatObjectRepresents(char[] getDigits) {
        /* # This method is rather primitive, but it is better than a method per Image...
         * # Due to naming, we know that the 4th index and 5th index represent the Hours. 
         * # Consider img_XXHour. */
        int firstDigit = Character.getNumericValue(getDigits[4]);
        int secondDigit = Character.getNumericValue(getDigits[5]);
        int hour = 0;
        switch(firstDigit) {
            case 0:
                /* # If 0, it is between 00:00 and 09:59 */
                hour = secondDigit;
                break;
            case 1:
                /* # If 1, it is between 10:00 and 19:59 */
                hour = 10 + secondDigit;
                break;
            case 2:
                /* # If 2, it is between 20:00 and 23:59 */
                hour = 20 + secondDigit;
                break;
            default:
                System.out.println("Hour error.");
                break;
        }      
        return hour;
    }
    
    public void addSelectedHourToList(ImageView image) {
        selection.add(image);
        nodeStateSelected(image);
    }
    
    public void removeSelectedHourFromList(ImageView image) {
        selection.remove(image);
        image.setEffect(null);
        nodeStateDefault(image);
    }
    
    public boolean imageIsAlreadySelected(ImageView image) {
        return selection.stream().anyMatch((i) -> (image.getId().equals(i.getId())));
    }
    
    
    
 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */    
    
    public char[] turnStringToCharArray(String input) {
        char[] array = input.toCharArray();
        return array;
    }
    
    public long calculateDuration() {
        
        int daycount = 0;
        Time open = thisBusiness.getOpeningHours();
        Time close = thisBusiness.getClosingHours();
        if(close.before(open)) {
            daycount = daycount + 1;
        }
        
        LocalTime openHour = LocalTime.parse(open.toString());
        LocalTime closeHour = LocalTime.parse(close.toString());
        
        LocalDateTime openTime = LocalDateTime.of(LocalDate.now(), openHour);
        LocalDateTime closeTime = LocalDateTime.of(LocalDate.now().plusDays(daycount), closeHour);
        
        Duration duration = Duration.between(openTime, closeTime);
        
        long timespan = duration.toMinutes();
        System.out.println(openTime.getDayOfWeek());
        System.out.println(closeTime.getDayOfWeek());
        System.out.println(openTime);
        System.out.println("Timespan: " + timespan);
        System.out.println("That makes " + (timespan / 30) * calculateDaysOfOperation() + " possible timeslots.");
        System.out.println("With " + (timespan / 30) + " per day.");
          
        return timespan;
    }
    
    public int calculateDaysOfOperation() {
        boolean[] dayArray = thisBusiness.getDaysOpen();
        int daysActive = 0;
        for(boolean d : dayArray) {
            if(d) {
                daysActive++;
            }
        }
        return daysActive;
    }
    
    public void generateTimeslots() {
        List<LocalTime> list = convertListToSetOfTime(selection);
        List<LocalDate> listOfDates = new ArrayList<>();
        
        boolean[] daysOpen = thisBusiness.getDaysOpen();
        
        LocalDateTime currentDate = LocalDateTime.now();
        List<Timeslot> thisEmployeesTimeslots = thisEmployee.getEmployeeTimeslots();
                
        
        for(int i = 0 ; i < days.length ; i++) {
            /* # For each day selected.*/
            if(daysOpen[i]) {
                String day = days[i];
                /* # Get the next instances of selected dates. */
                LocalDate nextInstance = getNextInstanceOfDay(day);
                listOfDates.add(nextInstance);
                /* # For this day, construct a set of Timeslots. */                
                for(LocalTime l : list) {
                    Date date = Date.valueOf(nextInstance);
                    Time time = Time.valueOf(l);
                    Timeslot timeslot = new Timeslot(
                            thisBusiness.getID(),
                            null,
                            thisEmployee.getEID(),
                            date,
                            time,
                            "Test");
                    if(thisTimeSlotAlreadyExists(timeslot, thisEmployee.getEmployeeTimeslots())) {
                        thisEmployee.getEmployeeTimeslots().add(timeslot);
                    } else {
                        System.out.println("There is already this timeslot");
                    }
                }
            }
        }
        System.out.println("============================");
        System.out.println("[Timeslot for " + thisEmployee.getEID());
        System.out.println("============================");
        
        for(Timeslot t : thisEmployee.getEmployeeTimeslots()) {
            System.out.println(
                    "Host: " + t.getHost() + 
                    " EID: " + t.getEmployeeID() + 
                    " Date: " + t.getAppointmentDate().toString() + 
                    " Time: " + t.getAppointmentTime() + 
                    " Description: " + t.getDescription());
        }
        
        
        /* # For each day selected, generate a set of timeslots. */
    }
    
    public boolean thisTimeSlotAlreadyExists(Timeslot timeslot, List<Timeslot> employeeTimeSlot) {
        for(Timeslot t : employeeTimeSlot) {
            if(t.getEmployeeID().equals(timeslot.getEmployeeID())) {
                if(t.getAppointmentDate().equals(timeslot.getAppointmentDate())) {
                    if(t.getAppointmentTime().equals(timeslot.getAppointmentTime())) {
                        return false;
                    }
                }                
            }
        }
        return true;
    }
    
    
    public List<LocalTime> convertListToSetOfTime(List<ImageView> list) {
        List<LocalTime> listToReturn = new ArrayList<>();
        for(ImageView v : list) {
            int hour = getHourThatObjectRepresents(turnStringToCharArray(v.getId()));
            String hh;
            
            if(hour<10) {
                hh = "0" + hour;
            } else {
                hh = Integer.toString(hour);
            }
            
            LocalTime time = LocalTime.parse(hh+":00");
            listToReturn.add(time);
        }
        
        return listToReturn;
    }
    
    public LocalDate getNextInstanceOfDay(String day) {
        
        LocalDate currentDate = LocalDate.now();
        LocalDate nextInstanceOfDay;
        switch(day) {
            
            case "sunday":
            case "Sunday":
            case "SUNDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)); 
            case "monday":
            case "Monday":
            case "MONDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            case "tuesday":
            case "Tuesday":
            case "TUESDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
            case "wednesday":
            case "Wednesday":
            case "WEDNESDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
            case "thursday":
            case "Thursday":
            case "THURSDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
            case "friday":
            case "Friday":
            case "FRIDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            case "saturday":
            case "Saturday":
            case "SATURDAY":
                return nextInstanceOfDay = currentDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
            default:
                System.out.println("Error adjusting date.");
                return nextInstanceOfDay = currentDate;
        }
    }
}
