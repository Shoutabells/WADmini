/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taximanagement;

import DB.DBConnection;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */


public class OrderSceneController implements Initializable {

    @FXML
    private AnchorPane paymentScene;
    @FXML
    private Button submitBtn;
    @FXML
    private TextField cName;
    @FXML
    private TextField oAmount;
    @FXML
    private TextField pLoc;
    @FXML
    private DatePicker oDate;
    @FXML
    private TextField dLoc;
    @FXML
    private ComboBox<String> cTNum;
    @FXML
    private TableView<?> oTableView;
    @FXML
    private Label warnMsg;

    /**
     * Initializes the controller class.
     */
     ObservableList<String> taxiList = FXCollections.observableArrayList(); 
      DisplayDatabase busTableData = new  DisplayDatabase();
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        busTableData.buildData(oTableView, "Select * from ordertable;");
        // TODO
    }    

    LocalDate date;
    String tNum;
    String custName;
    String pLocation;
    String dLocation;
    double amount;
    
    private boolean getOFields(){
        date = oDate.getValue();
        tNum = cTNum.getValue();
        custName=cName.getText();
        pLocation = pLoc.getText();
        dLocation = dLoc.getText();
        String amt = oAmount.getText();
       
        if(date==null){
        warnMsg.setText("Pls select a Date.");
        oDate.requestFocus();
        return false;
        }
        
         if(tNum==null || tNum.isEmpty()){
        warnMsg.setText("Pls select a Taxi.");
        cTNum.requestFocus();
        return false;
        }
         
         if(custName==null || custName.isEmpty()){
        warnMsg.setText("Pls select a Taxi.");
        cName.requestFocus();
        return false;
        }
            if(pLocation==null || pLocation.isEmpty()){
        warnMsg.setText("Pls enter the PickUp Location.");
        pLoc.requestFocus();
        return false;
        }
        
            if(dLocation==null || dLocation.isEmpty()){
        warnMsg.setText("Pls enter the drop Location.");
        dLoc.requestFocus();
        return false;
        }
            
        if(amt==null || amt.isEmpty()){
        warnMsg.setText("Pls enter Amount.");
        oAmount.requestFocus();
        return false;
        }else{
        amount = Double.parseDouble(amt);
        }
        if(amount<=0){
         warnMsg.setText("Amount cannot be less then or equal to zero.");
        oAmount.requestFocus();
        return false;
        }    
        warnMsg.setText("");
        return true;
    }
    
    @FXML
    private void submitOrder(ActionEvent event) {
        
        if(!getOFields()){
        return;
        }else{
         Connection c;
            try {

                
                
                c= DBConnection.connect();
            
                String sql = "insert into TransactionTable (Date,Type,Description,Amount) values ('"+date+"','Credit','Booking of customer"+custName+"','"+amount+"');";
            PreparedStatement  preparedStmt = c.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.execute();
            ResultSet rs1=preparedStmt.getGeneratedKeys();
            rs1.next();
            int TId= Integer.parseInt(rs1.getString(1));
                        
             sql = "Insert into OrderTable (Date, TaxiNum,Customer_Name,Pickup_add,Destination_Add,Amount,TId) Values ('"+date+"','"+tNum+"','"+custName+"','"+pLocation+"','"+dLocation+"','"+amount+"','"+TId+"');";
             c.createStatement().execute(sql);
             c.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(OrderSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
            resetFields();
              busTableData.buildData(oTableView, "Select * from ordertable;");
        }
        
    }

    
   
    @FXML
    private void checkAvail(ActionEvent event) {
        
       
        LocalDate date = oDate.getValue();
        if(date!=null){
           taxiList.clear();
            ResultSet rs= QueryDatabase.query("select Number from taxitable where Number not in (Select TaxiNum from ordertable where date='"+date+"');");
            if(rs!=null){
                try {
                    while(rs.next()){
                      taxiList.add(rs.getString(1));
                    }
                    if(taxiList.isEmpty()){
                     warnMsg.setText("All Taxies are reserved on this date.");
                     return;
                    }else{
                    warnMsg.setText("");
                    }
                    cTNum.setItems(taxiList);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderSceneController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
              warnMsg.setText("All Taxies are reserved on this date.");
            }
        }
        
    }

    @FXML
    private void checkDate(ActionEvent event) {
        if(oDate.getValue()==null){
        oDate.requestFocus();
        warnMsg.setText("Kindly select a date first.");
        }else{
        warnMsg.setText("");
        }
    }

    private void resetFields() {
      
       warnMsg.setText("");
       oDate.setValue(null);
       cTNum.setValue("");
       taxiList.clear();
       cTNum.setItems(taxiList);
       cName.clear();
       pLoc.clear();
       dLoc.clear();
       oAmount.clear();
       
    }
    
}
