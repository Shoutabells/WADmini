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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class ExpenseSceneController implements Initializable {

    @FXML
    private AnchorPane runServScene;
    @FXML
    private Button addServBtn;
    @FXML
    private TextField servAmount;
    @FXML
    private TextField servDescrip;
    @FXML
    private Button srchServBtn;
    @FXML
    private TableView<?> runServTable;
    @FXML
    private DatePicker eDate;
    @FXML
    private DatePicker sDate;
    @FXML
    private TextField tNum;
    @FXML
    private TextField dName;
DisplayDatabase tData= new DisplayDatabase();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         tData.buildData(runServTable, "Select * from TransactionTable Order By(Id) desc;");
        // TODO
    }    

    @FXML
    private void btnAddServ(ActionEvent event) {
         try {
            LocalDate date = eDate.getValue();
            String sDescrp = servDescrip.getText();
            String sAmount = servAmount.getText();
              String taxiN = tNum.getText();
                String driverN = dName.getText();
            if(date==null ){
                
                return;
            }
            
            if(sDescrp==null || sDescrp.isEmpty()){
                servDescrip.requestFocus();
                return;
            }
             if(sAmount==null || sAmount.isEmpty()){
                servAmount.requestFocus();
                return;
            }
             if(taxiN==null || taxiN.isEmpty()){
                tNum.requestFocus();
                return;
            }
             if(driverN==null || driverN.isEmpty()){
                dName.requestFocus();
                return;
            }
            
            Connection c;
            c = DBConnection.connect();
         
            String query = "insert into TransactionTable (date,Type,Description,Amount) values ('"+date+"','Debit','"+sDescrp+"','"+sAmount+"');";
            PreparedStatement ps = c.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int tID = Integer.parseInt(rs.getString(1));
           
           
            
            query = "insert into ExpenseTable (date,Description,TaxiNum,Driver,Amount,TId) values ('"+date+"','"+sDescrp+"','"+taxiN+"','"+driverN+"','"+sAmount+"','"+tID+"');";
            c.createStatement().execute(query);
            c.close();
            
            servDescrip.clear();
            servAmount.clear();
            tNum.clear();
            dName.requestFocus();
            
            tData.buildData(runServTable, "Select * from TransactionTable Order By(Id) desc;"); 
        
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSrchServ(ActionEvent event) {
        
        
    }

    @FXML
    private void deleteTransaction(ActionEvent event) {
         int index = runServTable.getSelectionModel().getSelectedIndex();
         ObservableList<ObservableList> data = tData.getData();
         ObservableList<String> items =  data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "TransactionTable");
         tData.buildData(runServTable, "Select * from TransactionTable;");
    }
 
    
}
