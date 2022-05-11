/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taximanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class DriverSceneController implements Initializable {

    @FXML
    private TextField driveName;
    @FXML
    private TextField driveAdd;
    @FXML
    private TextField dContact;
    @FXML
    private Button addDriverBtn;
    @FXML
    private TableView<?> driverTableView;
DisplayDatabase displayDriver =new DisplayDatabase();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayDriver.buildData(driverTableView, "Select * from driverTable Order By(Id) desc;");
    }    

    @FXML
    private void addDriver(ActionEvent event) {
       
         try {
            String name = driveName.getText();
            String contact = dContact.getText();
            String address = driveAdd.getText();
            
            if(name==null || name.isEmpty()){
                driveName.requestFocus();
                return;
            }
            
            if(contact==null || contact.isEmpty()){
                dContact.requestFocus();
                return;
            }
             if(address==null || address.isEmpty()){
                driveAdd.requestFocus();
                return;
            }
            
            Connection c;
            c = DBConnection.connect();
               if(update == true){
                   


              String query = "Update drivertable set Name='"+name+"',Contact='"+contact+"',Address='"+address+"' Where Id='"+id+"';";
            c.createStatement().execute(query);
            c.close();
            
            }
               else{
            String query = "insert into driverTable (Name,contact,address) values ('"+name+"','"+contact+"','"+address+"');";
            c.createStatement().execute(query);
            c.close();}
            
            driveName.clear();
            dContact.clear();
            driveAdd.clear();
            driveName.requestFocus();
            addDriverBtn.setText("Add");
            update = false;
            displayDriver.buildData(driverTableView, "Select * from driverTable Order By(Id) desc;"); 
        
        } catch (SQLException ex) {
            Logger.getLogger(TaxiDriverSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        
        
        
        
    }

    @FXML
    private void deleteDriver(ActionEvent event) {
        int index = driverTableView.getSelectionModel().getSelectedIndex();
         ObservableList data = displayDriver.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "DriverTable");
         
         displayDriver.buildData(driverTableView, "Select * from DriverTable;");
    }

    boolean update = false;
    int id;
  
    @FXML
    private void UpdateDriver(ActionEvent event) {
        
         int index = driverTableView.getSelectionModel().getFocusedIndex();
      ObservableList<ObservableList> data = displayDriver.getData();
      ObservableList<String> itemData = data.get(index);
      
    
      

      id = Integer.parseInt(itemData.get(0));
            
       driveName.setText(itemData.get(1));
        dContact.setText(itemData.get(2));
        driveAdd.setText(itemData.get(3));
       
      displayDriver.buildData(driverTableView, "Select * from DriverTable;");
        update=true;
        addDriverBtn.setText("Update");
    }

    
    }
    

