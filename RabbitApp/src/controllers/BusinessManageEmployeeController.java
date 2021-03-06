/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.AlertLabels;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rabbitapp.RabbitFX;
import rabbitmethods.Formatters;
import rabbitmethods.Session;
import rabbitmethods.Validation;
import rabbitobjects.Business;
import rabbitobjects.Customer;
import rabbitobjects.Employee;
import rabbitobjects.Timeslot;

/**
 *
 * @author Reisen
 * # The recommended set up for controller classes for RabbitFX.
 */
public class BusinessManageEmployeeController implements Initializable{
    private RabbitFX rabbitfx;
    private Session session;
    private Validation validation = new Validation();
    
    /* # Controller Variables */
    private Business thisBusiness;
    private Employee thisEmployee;
    private List<Image> profileIcons = new ArrayList<>();
    private List<Text> texts = new ArrayList<>();
    private List<TextField> textfields = new ArrayList<>();
  
    @FXML
    Glow glow = new Glow();
    InnerShadow innershadow = new InnerShadow();
    /* # Root Pane */
    public ChoiceBox<String>
            choicebox_SelectEmployee;
    
    public Label
            label_Title,
            label_HoverIconDesc;
    
    public Button 
            btn_Return,
            btn_AddEmployee,
            btn_DeleteBooking;
    
    public Text
            text_EmployeeName,
            text_EmployeeDesc;
            
    public ImageView
            img_ProfilePicture,
            img_Return;
    /* # Timeslot Pane */
    public ListView<String>
            listview_Timeslots;
    
    public Button
            btn_EditTimeslot,
            btn_DeleteTimeslot;
    
    public TextField
            textfield_SetCustomerID,
            textfield_SetDetails;
    
    /* # Bookings Pane */
    public ListView<String>
            listview_Bookings;
    
    public Text
        text_EditTimeslotError;
    
    public Text
            text_BookingDate,
            text_BookingPatron,
            text_BookingDesc;
    
    /* # Add/Edit Pane */
    public TitledPane
            pane_Edit,
            pane_Timeslots,
            pane_Bookings;
    
    public ImageView
            img_EditProfilePicture;
            
    public ChoiceBox<Integer>
            choicebox_EditProfilePicture;
    
    public TextField
            textfield_NewFirstName,
            textfield_NewLastName,
            textfield_NewDesc;
    
    public Button
            btn_Save,
            btn_Cancel,
            btn_DeleteEmployee;
    
    public Text
            text_AddEmployeeError;
    
    
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_bear.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_bird.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_boy.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_flower.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_fox.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_girlA.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_girlB.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_leaf.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_lotus.png"));
        profileIcons.add(new Image("/GUI/fxml/assets/profile/profile_turtle.png"));
        
        textfields.add(textfield_NewFirstName);
        textfields.add(textfield_NewLastName);
        textfields.add(textfield_NewDesc);
        
        texts.add(text_EmployeeDesc);
        texts.add(text_EmployeeName);
        
        label_HoverIconDesc.setText("");
        text_EditTimeslotError.setText("");
        
        
    }    
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        this.session = session;
        this.thisBusiness = (Business) session.currentUser;
        
        if(thisBusiness.getListOfEmployees().isEmpty())
        {
            pane_Edit.setVisible(false);
            pane_Timeslots.setVisible(false);
            pane_Bookings.setVisible(false);
        }
    }
    
 /* #########################################################################
  * #   GUI Interaction                                                     #
    ######################################################################### */
    
    public void onClickImg(MouseEvent event) throws IOException {
        ImageView clickNode = (ImageView) event.getSource();
        imgPurpose(clickNode);
    }
    
    public void onClickButton(MouseEvent event) {
        Button button = (Button) event.getSource();
        btnPurpose(button);
    }
    
    public void onHoverImg(MouseEvent event) {
        ImageView hoverNode = (ImageView) event.getSource();
        onHoverEffectsApplyTo(hoverNode);
        setHoverIconDescText(hoverNode);
    }
    
    public void onHoverExitImg(MouseEvent event) {
        ImageView hoverNode = (ImageView) event.getSource();
        hoverNode.setEffect(null);
        label_HoverIconDesc.setText("");
    }        
    
    public void onMouseHoldImg(MouseEvent event) {
       ImageView mouseHoldNode = (ImageView) event.getSource();
       onMouseHoldEffectApplyTo(mouseHoldNode);
    }

 /* #########################################################################
  * #   GUI Effects                                                         #
    ######################################################################### */   
    
    public void onHoverEffectsApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(glow);
    }    
    
    public void onMouseHoldEffectApplyTo(ImageView image) {
        image.setEffect(null);
        image.setEffect(innershadow);            
    }
    
 /* #########################################################################
  * #   GUI Logic                                                           #
    ######################################################################### */
    
    public void setHoverIconDescText(ImageView v) {
        String id =  v.getId();
        
        if(id.equals(img_Return.getId())) {
            label_HoverIconDesc.setText(AlertLabels.HOVER_DESCRIPTION_RETURN.toString());
        }   
    }
    
    public void imgPurpose(ImageView v) throws IOException {
        String id = v.getId();
        
        
        if(id.equals(img_Return.getId())) {
            Stage stage = (Stage) img_Return.getScene().getWindow();
            rabbitfx.businessStage(stage); 
        }         
    }
    
    public void btnPurpose(Button b) {
        String id = b.getId();
        
        if(id.equals(btn_Save.getId())) {
            text_AddEmployeeError.setText("");     
            img_ProfilePicture.setVisible(true);
            pane_Timeslots.setVisible(true);
            pane_Bookings.setVisible(true);              
            if(pane_Edit.getText().equals("Edit")) {
                if(editEmployee()) {
                    setEmployeeDetails(thisEmployee);
                    clearTextFieldError();
                    clearTextFields();
                    System.out.println("Edit confirmed.");
                }
            } else {
                if(addEmployee()) {
                    
                    System.out.println("Adding confirmed.");
                    pane_Edit.setText("Edit");
                    pane_Edit.setExpanded(false);                    
                } else {
                    
                }

            }
        }
        
        if(id.equals(btn_AddEmployee.getId())) {
            clearTextFields();
            choicebox_EditProfilePicture.getSelectionModel().clearAndSelect(0);
            clearEmployeeTextTable();
            pane_Edit.setVisible(true);
            pane_Edit.setText("Add");
            pane_Edit.setExpanded(true);
            img_ProfilePicture.setVisible(false);            
            pane_Timeslots.setVisible(false);
            pane_Bookings.setVisible(false);
        }
        
        if(id.equals(btn_Cancel.getId())) {
            clearTextFields();
            text_AddEmployeeError.setText("");                   
            setEmployeeDetails(thisEmployee);            
            pane_Edit.setText("Edit");
            pane_Edit.setExpanded(false);
            img_ProfilePicture.setVisible(true);
            pane_Timeslots.setVisible(true);
            pane_Bookings.setVisible(true);            
        }
        
        if(id.equals(btn_EditTimeslot.getId())) {
            editTimeslot();
        }
        
        if(id.equals(btn_DeleteBooking.getId()))
        {
            ListView<String> list = listview_Bookings;
            String booking = list.getSelectionModel().getSelectedItem();
            int length = booking.length();
            String timeslotID = booking.substring(length-11, length-1);
            Employee e = thisEmployee;
            List<Timeslot> timeslots = e.getEmployeeTimeslots();
            System.out.println("Employee: " + e.getEmployeeFirstName() + " " + e.getEmployeeLastName());
            System.out.println("TID: " + timeslotID);
            for(int i=0;i<timeslots.size();i++)
            {
                if(timeslots.get(i).getTID().equals(timeslotID))
                {
                    e.getEmployeeTimeslots().get(i).setPatron(null);
                    session.updateTimeslot(e.getEmployeeTimeslots().get(i));
                    System.out.println("FOUND");
                    break;
                }
            }
            
            setListOfBookings(thisEmployee);
            
        }
    }
    
    public void setErrorTextField(TextField tf, boolean toggle) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(toggle) { // Remove
            styleClass.removeAll(Collections.singleton("error")); 
        } else { // Add
            styleClass.add("error");
        }      
    }    
    
    public void clearTextFieldError() {
        textfields.forEach((field) -> {
            setErrorTextField(field, true);
            text_EditTimeslotError.setText("");
        });
    }

    public void clearEmployeeTextTable() {
        texts.forEach((text) -> {
            text.setText("");
        });
    }    
    
 /* #########################################################################
  * #   Set-Up Employee Page                                                #
    ######################################################################### */

    public void setEmployeeChoiceBox() {
        /* # Populate Select Employee list. */
        List<Employee> employeeList = thisBusiness.getListOfEmployees();
        ChoiceBox<String> selectEmployee = choicebox_SelectEmployee;
        clearEmployeeTextTable();
        
        for(Employee e : employeeList) {
            if(e.getEmployeeFirstName()!=null) {
                String fullName = e.getEmployeeFirstName() + " " + e.getEmployeeLastName();
                selectEmployee.getItems().add(fullName);                
            }
        }
        
        setEmployeeChoiceBoxListener();
        selectEmployee.getSelectionModel().selectFirst();
    } // End setEmployeeChoiceBox
    
    private void setEmployeeChoiceBoxListener() {
        ChoiceBox<String> selectEmployee = choicebox_SelectEmployee;
        
        selectEmployee.getSelectionModel().selectedIndexProperty().addListener
        (new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                employeeChoiceBoxListener(new_value.intValue());
            }
        });        
    }
    
    private void employeeChoiceBoxListener(int index) {
        /* # Set current employee to selected index. */
        thisEmployee = thisBusiness.getListOfEmployees().get(index);
        
        img_ProfilePicture.setVisible(true);
        img_ProfilePicture.setImage(profileIcons.get(thisEmployee.getProfilePicture()));
        clearTextFields();
        setEmployeeDetails(thisEmployee);
    }
    
    private void setEmployeeDetails(Employee e) {
        img_ProfilePicture.setImage(profileIcons.get(e.getProfilePicture()));
        choicebox_EditProfilePicture.getSelectionModel().select(e.getProfilePicture());
        text_EmployeeName.setText(e.getEmployeeFirstName() + " " + e.getEmployeeLastName());
        text_EmployeeDesc.setText(e.getEmployeeDesc());
        
        setListOfTimeslots(e);
        setListOfBookings(e);
    }
    
    private void setListOfTimeslots(Employee e) {
        ListView<String> list = listview_Timeslots;
        ObservableList<String> timeslots = FXCollections.observableArrayList();
        Timeslot.sortThisListByDate(e.getEmployeeTimeslots());
        
        SimpleDateFormat s = Formatters.formatDateToStringddMMEEEE();
        for(Timeslot t : e.getEmployeeTimeslots()) {
            if(t.getPatron()==null) {
                String date = s.format(t.getAppointmentDate());
                String time = t.getAppointmentTime().toString();
                timeslots.add(date + " - " + time);                
            }

        }
        list.setItems(timeslots);
    }
    
    private void setListOfBookings(Employee e) {
        ListView<String> list = listview_Bookings;
        ObservableList<String> bookings = FXCollections.observableArrayList();
        Timeslot.sortThisListByDate(e.getEmployeeTimeslots());
        SimpleDateFormat s = Formatters.formatDateToStringddMMEEEE();
        String desc;
        for(Timeslot t : e.getEmployeeTimeslots()) {
            if(t.getPatron()!=null) {
                String date = s.format(t.getAppointmentDate());
                String time = t.getAppointmentTime().toString();
                String timeslotID = t.getTID();
                if(t.getDescription()!=null) {
                    desc = t.getDescription();
                } else {
                    desc = "None";
                }
                bookings.add(desc + " | " + t.getPatron() + " | " + date + " - " + time + " | (" + timeslotID + ")");
            }
        }
        list.setItems(bookings);
        
    }
    

    
 /* #########################################################################
  * #   Set-Up Edit Page                                                    #
    ######################################################################### */    
    
    public void setEditProfileIconChoiceBox() {
        int index = 0;
        ChoiceBox<Integer> editIcon = choicebox_EditProfilePicture;
        
        for(Image i : profileIcons) {
            editIcon.getItems().add(index);
            index++;
        }
        
        setProfileChoiceBoxListener(editIcon);
        
    }
    
    private void setProfileChoiceBoxListener(ChoiceBox box) {
           
        box.getSelectionModel().selectedIndexProperty().addListener
        (new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                editProfileChoiceBoxListener(new_value.intValue());
            }
        });         
    }
    
    private void editProfileChoiceBoxListener(int index) {       
        img_EditProfilePicture.setImage(profileIcons.get(index));
    }    
 /* #########################################################################
  * #   Add Employee                                                        #
    ######################################################################### */
    
    public boolean addEmployee() {
        int profileid = choicebox_EditProfilePicture.getSelectionModel().getSelectedIndex();
        String fname = textfield_NewFirstName.getText();
        String lname = textfield_NewLastName.getText();
        String desc = textfield_NewDesc.getText();
        

        if(validateFields(fname, lname)) {
            String id = session.generateID("E");
            List<Timeslot> list = new ArrayList<>();
            Employee dummyEmployee = new Employee(id, profileid, fname, lname, desc, list);
            session.saveEmployeeToDatabase(dummyEmployee, thisBusiness);
            thisBusiness.getListOfEmployees().add(dummyEmployee);
            /* # Update Choicebox. */
            String employeeFullName = dummyEmployee.getEmployeeFirstName() + dummyEmployee.getEmployeeLastName();
            choicebox_SelectEmployee.getItems().add(employeeFullName);
            /* # Set selection to this employee.*/
            choicebox_SelectEmployee.getSelectionModel().selectLast();  
            text_AddEmployeeError.setText("");
            return true;
        } else {
            text_AddEmployeeError.setVisible(true);
            text_AddEmployeeError.setText("Invalid Fields.");
            System.out.println("Unsuccessful.");
            return false;
        }
    }    
    
 /* #########################################################################
  * #   Edit Employee                                                       #
    ######################################################################### */          
    
    public boolean editEmployee() {
        int profileid = choicebox_EditProfilePicture.getSelectionModel().getSelectedIndex();
        String fname, lname, desc;
        Employee editMe = thisEmployee;
        
        /* # Check First Name. */
        if(fieldIsEmpty(textfield_NewFirstName)) {
            /* # If empty, leave name as it is. */
            fname = editMe.getEmployeeFirstName();
        } else {
            /* # If not, set as what is in the textfield. */
            fname = textfield_NewFirstName.getText();
        }
        
        /* # Check Last Name. */        
        if(fieldIsEmpty(textfield_NewLastName)) {
            lname = editMe.getEmployeeLastName();
        } else {
            lname = textfield_NewLastName.getText();
        }
        
        /* # Check Description */        
        if(textfield_NewDesc.getText().isEmpty()) {
            desc = editMe.getEmployeeDesc();
        } else {
            desc = textfield_NewDesc.getText();
        }
        
        if(validateFields(fname, lname)) {
            editMe.setEmployeeFirstName(fname);
            editMe.setEmployeeFirstName(fname);
            editMe.setEmployeeLastName(lname);
            editMe.setEmployeeProfilePicture(profileid);
            editMe.setEmployeeDesc(desc);
            session.updateEmployee(editMe);
            
            System.out.println("Editing Employee Success: " + editMe.getEID());
            return true;
            
        } else {
            System.out.println("Editing Employee Failure: " + editMe.getEID());
            return false;
        }
    }
    
 /* #########################################################################
  * #   Edit Timeslot                                                       #
    ######################################################################### */ 
    
    public void editTimeslot() {
        boolean found = false;
        ArrayList<Customer> customers = new ArrayList<Customer>();
        int index = listview_Timeslots.getSelectionModel().getSelectedIndex();
        if(index>=0) {
            Timeslot selectedTimeslot = thisEmployee.getEmployeeTimeslots().get(index);
            
            String userDetails = textfield_SetCustomerID.getText();
            String newDesc = textfield_SetDetails.getText();
            if(session.emailAlreadyExists(userDetails)) //If user entered email, gets ID
            {
                for(int i=0;i<session.users.size();i++)
                { //Creates array of customers
                    if(session.users.get(i) instanceof Customer) customers.add((Customer)session.users.get(i));
                }
                for(int i=0;i<customers.size();i++)
                { //searches for email, to convert to ID
                    if(customers.get(i).getEmail().equals(userDetails))
                    {
                        userDetails=customers.get(i).getID();
                        found = true;
                        break;
                    }
                }
            }
            
            if(session.idAlreadyExists(userDetails)) { //makes booking based on ID
                text_EditTimeslotError.setText("");
                selectedTimeslot.setPatron(userDetails);
                selectedTimeslot.setDescription(newDesc);
                session.updateTimeslot(selectedTimeslot);
                setEmployeeDetails(thisEmployee);
                System.out.println("Test Passed");
            } 
            if(!found) {
                text_EditTimeslotError.setText("Customer does not exist.");
                System.out.println("This user does not exist");
            }
        }
    }

 /* #########################################################################
  * #   Utility Methods                                                     #
    ######################################################################### */ 
    public void clearTextFields() {
        for(TextField t : textfields) {
            t.setText("");
        }
    }
    
    public boolean validateFields(String fname, String lname) {
        Text text = new Text(); // Dummy text.
        TextField fnameField = textfield_NewFirstName;
        TextField lnameField = textfield_NewLastName;
        /* # Clear Error status before you validate again. */
        clearTextFieldError();
        
        int requirementCheck = 0;
        if(!validation.nameFieldsAreValid(fname, lname, text)) {
            setErrorTextField(fnameField, false);
            setErrorTextField(lnameField, false);
        } else {
            requirementCheck = requirementCheck + 1;
        }
        
        return requirementCheck>0;      
    }
  
    public boolean fieldIsEmpty(TextField field) {
        return field.getText().isEmpty();
    }   
    
    public void updateBooking()
    {
        ListView<String> list = listview_Bookings;
        String input, bookingDate, bookingID, bookingDesc;
        String[] inputs;
        if(!list.getSelectionModel().isEmpty())
        {
            input = list.getSelectionModel().getSelectedItem();
            inputs = input.split("\\|");
            bookingDesc = inputs[0].replaceAll("\\s","");
            bookingID = inputs[1].replaceAll("\\s","");
            bookingDate = inputs[2].replaceAll("\\s","");

            text_BookingDate.setText(bookingDate);
            text_BookingPatron.setText(bookingID);
            text_BookingDesc.setText(bookingDesc);
        }
    }
}



