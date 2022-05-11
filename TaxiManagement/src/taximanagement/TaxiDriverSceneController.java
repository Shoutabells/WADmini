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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class TaxiDriverSceneController implements Initializable {

    @FXML
    private AnchorPane taxiDriverScene;
    @FXML
    private TextField taxiModel;
    @FXML
    private Button addTaxiBtn;
    private TextField driveName;
    @FXML
    private TextField taxiNum;
    @FXML
    private TextField taxiMake;
    @FXML
    private TableView<?> taxiTableView;
    private TextField driveAdd;
    private TextField dContact;
    private TableView<?> driverTableView;

    /**
     * Initializes the controller class.
     */
     DisplayDatabase displayTaxi = new DisplayDatabase();
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      displayTaxi.buildData(taxiTableView, "Select * from taxitable;");
        
        
    }    

    @FXML
    private void addTaxi(ActionEvent event) {
        
         try {
            String number = taxiNum.getText();
            String make = taxiMake.getText();
             String model = taxiModel.getText();
             
            if(number==null || number.isEmpty()){
                taxiNum.requestFocus();
                
                return;
            }
            
            if(make==null || make.isEmpty()){
                taxiMake.requestFocus();
                return;
            }
            if(model==null || model.isEmpty()){
                taxiModel.requestFocus();
                return;
            }
            
            Connection c;
            c = DBConnection.connect();
            
            String query = "insert into taxiTable (Number,Make,Model) values ('"+number+"','"+make+"','"+model+"');";
            c.createStatement().execute(query);
            c.close();
            
            taxiNum.clear();
            taxiMake.clear();
            taxiModel.clear();
            
            displayTaxi.buildData(taxiTableView, "Select * from taxiTable Order By(Id) desc;");
            taxiNum.requestFocus();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TaxiDriverSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        
        
        
        
    }

    @FXML
    private void deleteTaxi(ActionEvent event) {
         int index = taxiTableView.getSelectionModel().getSelectedIndex();
         ObservableList data = displayTaxi.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "taxitable");
         
         displayTaxi.buildData(taxiTableView, "Select * from taxitable;");
    }

    
    
    
}
