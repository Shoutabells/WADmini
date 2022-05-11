/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taximanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Sameer
 */
public class MainSceneController implements Initializable {

    @FXML
    private BorderPane rootLayout;
    @FXML
    private ToggleButton registerBtn;
    @FXML
    private ToggleGroup group1;
    @FXML
    private ToggleButton orderBtn;
    @FXML
    private ToggleButton expeseBtn;
    @FXML
    private ToggleGroup group11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         changeScene("OrderScene.fxml");
    }    
    
        
    public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){
        }
     
    }
   
    @FXML
    private void setRegisterScene(ActionEvent event) {
            changeScene("TaxiDriverScene.fxml");
    }

    @FXML
    private void setOrderScene(ActionEvent event) {
       changeScene("OrderScene.fxml");
    }

    @FXML
    private void setEnpenseScene(ActionEvent event) {
        changeScene("ExpenseScene.fxml");
    }

    @FXML
    private void setDriverScene(ActionEvent event) {
        changeScene("DriverScene.fxml");
    }
    
}
