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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Formatters;
import rabbitmethods.Session;
import rabbitobjects.Business;
import rabbitobjects.Employee;
import rabbitobjects.Timeslot;

/**
 *
 * @author Reisen
 * # Manage Employee Timeslots and Workshifts.
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
    
    public Text
            text_00or12,
            text_01or13,
            text_02or14,
            text_03or15,
            text_04or16,
            text_05or17,
            text_06or18,
            text_07or19,
            text_08or20,
            text_09or21,
            text_10or22,
            text_11or23,
            text_EmployeeName,
            text_HoursOfOperation,
            text_EmployeeDesc;
    
    public Button
            btn_Save;
    
    public ChoiceBox<String>
            choicebox_SelectEmployee;
    
    public Label
            label_HoverIconDesc;
    
    public CheckBox
            checkbox_Sunday,
            checkbox_Monday,
            checkbox_Tuesday,
            checkbox_Wednesday,
            checkbox_Thursday,
            checkbox_Friday,
            checkbox_Saturday;
    
    public RadioButton
            radiobtn_AM,
            radiobtn_PM;
    
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
        if(radiobtn_AM.isSelected()) {
            text_00or12.setText("00:00");
            text_01or13.setText("01:00");
            text_02or14.setText("02:00");
            text_03or15.setText("03:00");
            text_04or16.setText("04:00");
            text_05or17.setText("05:00");
            text_06or18.setText("06:00");
            text_07or19.setText("07:00");
            text_08or20.setText("08:00");
            text_09or21.setText("09:00");
            text_10or22.setText("10:00");   
            text_11or23.setText("11:00");
        }
        if(radiobtn_PM.isSelected()) {
            text_00or12.setText("12:00");
            text_01or13.setText("13:00");
            text_02or14.setText("14:00");
            text_03or15.setText("15:00");
            text_04or16.setText("16:00");
            text_05or17.setText("17:00");
            text_06or18.setText("18:00");
            text_07or19.setText("19:00");
            text_08or20.setText("20:00");
            text_09or21.setText("21:00");
            text_10or22.setText("22:00");   
            text_11or23.setText("23:00");
        }        
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
                        checkbox_Sunday.setDisable(false);
                        checkbox_Sunday.setSelected(true);
                        break;
                    case 1: // Monday
                        checkbox_Monday.setDisable(false);
                        checkbox_Monday.setSelected(true);
                        break;
                    case 2: // Tuesday
                        checkbox_Tuesday.setDisable(false);
                        checkbox_Tuesday.setSelected(true);
                        break;
                    case 3: // Wednesday
                        checkbox_Wednesday.setDisable(false);
                        checkbox_Wednesday.setSelected(true);
                        break;
                    case 4: // Thursday
                        checkbox_Thursday.setDisable(false);
                        checkbox_Thursday.setSelected(true);
                        break;
                    case 5: // Friday
                        checkbox_Friday.setDisable(false);
                        checkbox_Friday.setSelected(true);
                        break;
                    case 6: // Saturday
                        checkbox_Saturday.setDisable(false);
                        checkbox_Saturday.setSelected(true);
                        break;
                    default: // Wtf?
                        break;
                }
            }
        }
    }
    
    public void setEmployeeList() {
        List<Employee> employeeList = thisBusiness.getListOfEmployees();
        ChoiceBox<String> selectEmployee = choicebox_SelectEmployee;
        
        for(Employee e : employeeList) {
            if(e.getEmployeeFirstName()!=null) {
                String fullName = e.getEmployeeFirstName() + " " + e.getEmployeeLastName();
                selectEmployee.getItems().add(fullName);                
            }
        }
        
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
    
    public void setEmployeeHours() {
        List<Timeslot> list = thisEmployee.getEmployeeTimeslots();
        SimpleDateFormat d = Formatters.formatDateToStringEEEE();
        
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
        }
        if(radiobtn_PM.isSelected()) {
            /* # First reset the states. */
            setAvailableHours(false);
            toggleAMorPM();
        }
    }
    
    public void onClickSave() {
        if(generateWorkshift()) {
            clearSelectedHoursFromList();
        } else {
            System.out.println("User cancelled changes.");
        }
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
    
    public void setSelectedDays() {
        if(checkbox_Sunday.isSelected()) {
            daysSelected[0] = true;
        } else {
            daysSelected[0] = false;
        }
 
        if(checkbox_Monday.isSelected()) {
            daysSelected[1] = true;
        } else {
            daysSelected[1] = false;
        }

        if(checkbox_Tuesday.isSelected()) {
            daysSelected[2] = true;
        } else {
            daysSelected[2] = false;
        }
 
        if(checkbox_Wednesday.isSelected()) {
            daysSelected[3] = true;
        } else {
            daysSelected[3] = false;
        }

        if(checkbox_Thursday.isSelected()) {
            daysSelected[4] = true;
        } else {
            daysSelected[4] = false;
        }
 
        if(checkbox_Friday.isSelected()) {
            daysSelected[5] = true;
        } else {
            daysSelected[5] = false;
        } 

        if(checkbox_Saturday.isSelected()) {
            daysSelected[6] = true;
        } else {
            daysSelected[06] = false;
        }
        
    }
    
    public void addSelectedHourToList(ImageView image) {
        selection.add(image);
        nodeStateSelected(image);
    }
    
    public void removeSelectedHourFromList(ImageView image) {
        image.setEffect(null);
        nodeStateDefault(image);
        selection.remove(image);        
    }
    
    public void clearSelectedHoursFromList() {
        ImageView x;
        while(!selection.isEmpty()) {
            x = selection.get(0);
            removeSelectedHourFromList(x);
        }
        System.out.println("Selection cleared.");
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
    
    public long calculateDuration(Time open, Time close) {
        
        int daycount = 0;

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
    
    public boolean generateWorkshift() {
        List<LocalTime> listOfStartTime = getStartTimeFromSelection(selection);
        List<LocalDate> listOfDates = new ArrayList<>();
        
        boolean[] daysOpen = thisBusiness.getDaysOpen();
        
        LocalDateTime currentDate = LocalDateTime.now();
        List<Timeslot> newTimeslots = new ArrayList<>();            
        
        setSelectedDays();
        if(!thisEmployee.getEmployeeTimeslots().isEmpty()) {
            if(alertOverwrite()) {
                
            } else {
                System.out.println("Changes cancelled.");
                return false;
            }
        }
        
        for(int i = 0 ; i < days.length ; i++) {
            /* # For each day selected.*/
            if(daysOpen[i]) {
                String day = days[i];
                /* # Get the next instances of selected dates. */
                LocalDate nextInstance = getNextInstanceOfDay(day);
                listOfDates.add(nextInstance);
                /* # For this day, construct a set of Timeslots. */                
                for(LocalTime l : listOfStartTime) {
                    Date date = Date.valueOf(nextInstance);
                    LocalTime end = l.plusHours(1);
                    Time startTime = Time.valueOf(l);
                    Time endTime = Time.valueOf(end);
                    Timeslot timeslot = new Timeslot(
                            session.generateID("T"),
                            thisBusiness.getID(),
                            null,
                            thisEmployee.getEID(),
                            date,
                            endTime,
                            startTime,
                            "None");
                        newTimeslots.add(timeslot);                        
                        System.out.println(
                        "Successfully Added: " +
                        "Host: " + timeslot.getHost() + 
                        " EID: " + timeslot.getEmployeeID() + 
                        " Date: " + timeslot.getAppointmentDate().toString() + 
                        " Time: " + timeslot.getAppointmentTime() + " to " + timeslot.getAppointmentTimeEnd() +
                        " Description: " + timeslot.getDescription());
                }
            }
        }
        session.removeTimeslotsOfEmployeeFromDatabase(thisEmployee);
        for(Timeslot t : newTimeslots) {
            session.saveTimeslotToDatabase(t);
        }
        thisEmployee.setEmployeeTimeslots(newTimeslots);
        
        System.out.println("============================");
        System.out.println("[Timeslot for " + thisEmployee.getEID());
        System.out.println("============================");
        
        for(Timeslot t : thisEmployee.getEmployeeTimeslots()) {
            System.out.println(
                    "Host: " + t.getHost() + 
                    " EID: " + t.getEmployeeID() + 
                    " Date: " + t.getAppointmentDate().toString() + 
                    " Time: " + t.getAppointmentTime() + " to " + t.getAppointmentTimeEnd() +
                    " Description: " + t.getDescription());
        }
               
        return true;
    }
    
    public boolean alertOverwrite() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm overwrite?");
        alert.setContentText("You are about to overwrite your previous settings.");
        ButtonType okConfirm = new ButtonType("OK");
        ButtonType noCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(okConfirm, noCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        return result.get() == okConfirm;
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
    
    
    public List<LocalTime> getStartTimeFromSelection(List<ImageView> list) {
        List<LocalTime> listToReturn = new ArrayList<>();
        for(ImageView v : list) {
            int hour = getHourThatObjectRepresents(turnStringToCharArray(v.getId()));
            String hh;
            
            if(hour<10) {
                hh = "0" + hour;
            } else {
                hh = Integer.toString(hour);
            }
            
            
            LocalTime time = LocalTime.parse(hh+":"+"00");
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
