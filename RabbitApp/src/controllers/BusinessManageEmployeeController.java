/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import rabbitapp.RabbitFX;
import rabbitmethods.Session;
import rabbitmethods.Validation;
import rabbitobjects.Business;
import rabbitobjects.Employee;

/**
 *
 * @author Reisen
 */
public class BusinessManageEmployeeController implements Initializable{
    RabbitFX rabbitfx;
    Session session;
    Validation validation = new Validation();
    Business thisBusiness;
    
    Boolean editOrAdding = false;
    
    List<Image> profilepics = new ArrayList<>();
    List<TextField> textfields = new ArrayList<>();
    List<Text> texts = new ArrayList<>();
    
    @FXML
 /* #########################################################################
  * #   DECLARE JAVAFX OBJECTS                                              #
    ######################################################################### */
    public Button
            btn_AddEmployee,
            btn_EditEmployee,
            btn_DeleteEmployee,
            btn_EditTimeslot,
            btn_SaveEdit,
            btn_RemoveTimeslot,
            btn_CancelEdit;
    
    public ChoiceBox<String>
            choicebox_SelectEmployee,
            choicebox_SelectTimeslot;
    
    public ChoiceBox<Integer> 
            choicebox_ProfilePicture;
    
    public ImageView
            img_Logo,
            img_ProfilePicture,
            img_Return,
            img_Timeslots;
    
    public Text
            text_EmployeeDesc,
            text_EmployeeID,
            text_EmployeeFirstName,
            text_EmployeeLastName,
            text_TimeslotTime,
            text_TimeslotDuration;
    
    public TextField
            textfield_EditFirstName,
            textfield_EditLastName;
    
    public TextArea
            textarea_EditDesc;

 /* #########################################################################
  * #   CONSTRUCTOR METHODS                                                 #
    ######################################################################### */    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
    }    
    
    public void setDriver(RabbitFX rabbitfx) {
        this.rabbitfx = rabbitfx;
    }
    public void setSession(Session session) {
        this.session = session;
        /* # Cast Business User. */
        this.thisBusiness = (Business) session.currentUser;
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
        
        int profileindex = 0;
        for(Image i : profilepics) {
            choicebox_ProfilePicture.getItems().add(profileindex);
            profileindex++;
        }       
        
        /* # Set the profile picture as options are selected. */
        choicebox_ProfilePicture.getSelectionModel().selectedIndexProperty().addListener
        (new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                img_ProfilePicture.setImage(profilepics.get(new_value.intValue()));
            }
        });
        
        choicebox_ProfilePicture.getSelectionModel().select(0);
    }
    
    public void setEmployeeChoices() {
        for(Employee e : thisBusiness.getListOfEmployees()) {
            if(e.getEID()!=null) {
                choicebox_SelectEmployee.getItems().add(e.getEID()); 
            }
        }
        choicebox_SelectEmployee.getSelectionModel().selectedIndexProperty().addListener
        (new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                setEmployeeDetails(thisBusiness.getListOfEmployees().get(new_value.intValue()));
                img_ProfilePicture.setImage(profilepics.get(thisBusiness.getListOfEmployees().get(new_value.intValue()).getProfilePicture()));
            }
        });        
    }
    
    public void setEmployeeDetails(Employee employee) {
        text_EmployeeID.setText(employee.getEID());
        text_EmployeeFirstName.setText(employee.getEmployeeFirstName());
        text_EmployeeLastName.setText(employee.getEmployeeLastName());
        text_EmployeeDesc.setText(employee.getEmployeeDesc());
    }
    
    public void showEditFields() {
        /* # Toggle edit field visibility. 
         * # Since we are using the same field for edit and add. */
        textfield_EditFirstName.setVisible(true);
        textfield_EditLastName.setVisible(true);
        textarea_EditDesc.setVisible(true);
        btn_SaveEdit.setVisible(true);
        btn_CancelEdit.setVisible(true);
        choicebox_ProfilePicture.setVisible(true);
    }
    
    public void hideEditFields() {
        textfield_EditFirstName.setVisible(false);
        textfield_EditLastName.setVisible(false);
        textarea_EditDesc.setVisible(false);
        btn_SaveEdit.setVisible(false);
        btn_CancelEdit.setVisible(false);
        choicebox_ProfilePicture.setVisible(false);        
    }
    
    public void disableEditIfNoEmployees() {
        if(thisBusiness.getListOfEmployees().isEmpty()) {
            btn_EditEmployee.setOpacity(0.4);
            btn_EditEmployee.setDisable(true);
        } else {
            choicebox_SelectEmployee.getSelectionModel().selectFirst();
        }        
    }
    
    public void setTextFieldList() {
        textfields.add(textfield_EditFirstName);
        textfields.add(textfield_EditLastName);
        
        texts.add(text_EmployeeDesc);
        texts.add(text_EmployeeID);
        texts.add(text_EmployeeFirstName);
        texts.add(text_EmployeeLastName);
        texts.add(text_TimeslotTime);
        texts.add(text_TimeslotDuration);
        clearEmployeeTextTable();      
    }       

 /* #########################################################################
  * #   LOGIC METHODS                                                       #
    ######################################################################### */   
    
    public void addEmployee() {
        int profileid = choicebox_ProfilePicture.getSelectionModel().getSelectedIndex();
        String fname = textfield_EditFirstName.getText();
        String lname = textfield_EditLastName.getText();
        String desc = textarea_EditDesc.getText();
        
        if(validateFields(fname, lname)) {
            commitEmployeeToDatabase(fname, lname, desc, profileid, thisBusiness);
            
        } else {
            System.out.println("Add New Employee Fail: Employee Details Invalid");
        }                              
    }
    
    public void editEmployee(String eid) {
        int profileid = choicebox_ProfilePicture.getSelectionModel().getSelectedIndex();
        String 
            fname,
            lname,
            desc;
        Employee editMe = getThisEmployee(eid);
        
        if(fieldIsEmpty(textfield_EditFirstName)) {
            fname = editMe.getEmployeeFirstName();
        } else {
            fname = textfield_EditFirstName.getText();
        }
        
        if(fieldIsEmpty(textfield_EditLastName)) {
            lname = editMe.getEmployeeLastName();
        } else {
            lname = textfield_EditLastName.getText();
        }
        
        if(textarea_EditDesc.getText().isEmpty()) {
            desc = editMe.getEmployeeDesc();
        } else {
            desc = textarea_EditDesc.getText();
        }
        
        if(validateFields(fname, lname)) {
            editMe.setEmployeeFirstName(fname);
            editMe.setEmployeeLastName(lname);
            editMe.setEmployeeProfilePicture(profileid);
            editMe.setEmployeeDesc(desc);
            session.updateEmployee(editMe);
            System.out.println("Editing Employee successful: " + editMe.getEID());
            hideEditFields();
            
        } else {
            System.out.println("Edit Employee: " + editMe.getEID() + " failed.");
        }
    }
    
    public void commitEmployeeToDatabase(String fname, String lname, String desc, int profileid, Business business) {
        /* # Build a dummy Employee to pass into Session. */
        String id = session.generateID("E");
        
        Employee dummyEmployee = new Employee(id, profileid, fname, lname, desc, null);
        session.saveEmployeeToDatabase(dummyEmployee, business);
        /* # Add this employee to business database. */
        business.getListOfEmployees().add(dummyEmployee);
        /* # Update Choicebox. */
        choicebox_SelectEmployee.getItems().add(dummyEmployee.getEID());
        /* # Set selection to this employee.*/
        choicebox_SelectEmployee.getSelectionModel().selectLast();
        System.out.println("Adding New Employee successful: " + dummyEmployee.getEID());
        /* # Enable Edit button. */
        btn_EditEmployee.setDisable(false);
        btn_EditEmployee.setOpacity(1);
        hideEditFields();
    }
    
    public void deleteEmployee() {
        
    }     

    public boolean validateFields(String fname, String lname) {
        Text text = new Text(); // Dummy text.
        TextField fnameField = textfield_EditFirstName;
        TextField lnameField = textfield_EditLastName;
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
    
    public void clearTextFieldError() {
        textfields.forEach((field) -> {
            setErrorTextField(field, true);
        });
    }
    
    public void clearEmployeeTextTable() {
        texts.forEach((text) -> {
            text.setText("");
        });
    }
    
 /* #########################################################################
  * #   EVENT LISTENERS                                                     #
    ######################################################################### */   
    
    public void onClickAddEmployee() {
        /* # Set mode to Adding Employee */
        /* # Edit = False, Add = True */
        editOrAdding = true;
        clearEmployeeTextTable();
        showEditFields();
    }
    
    public void onClickEditEmployee() {
        /* # Set mode to Adding Employee */
        /* # Edit = False, Add = True */
        editOrAdding = false;
        choicebox_SelectEmployee.getSelectionModel().getSelectedIndex();
        setEmployeeDetails(thisBusiness.getListOfEmployees().get(0));
        
        showEditFields();
    }
    
    public void onClickSave() {
        if(editOrAdding) {
            addEmployee();
        } else {
            int index = choicebox_SelectEmployee.getSelectionModel().getSelectedIndex();
            Business business = (Business) session.currentUser;
            editEmployee(business.getListOfEmployees().get(index).getEID());
        }
    }
    
 /* #########################################################################
  * #   Design Methods                                                      #
    ######################################################################### */     
    public void setErrorTextField(TextField tf, boolean toggle) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(toggle) { // Remove
            styleClass.removeAll(Collections.singleton("error")); 
        } else { // Add
            styleClass.add("error");
        }      
    }
        
    public boolean fieldIsEmpty(TextField field) {
        return field.getText().isEmpty();
    }
    
    public Employee getThisEmployee(String eid) {
        Business business = (Business) session.currentUser;
        List<Employee> employees = business.getListOfEmployees();
        
        for(Employee e : employees) {
            if(e.getEID().equals(eid)) {
                return e;
            }
        }
        /* # Employee not found */
        return null;
    }
    
}
