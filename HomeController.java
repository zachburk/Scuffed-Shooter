/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zsbwf7finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Zach
 */
public class HomeController extends Switchable implements Initializable {
    
    @FXML
    Button playButton;
    
    @FXML
    Button aboutButton;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void handleGoToClassSelect(ActionEvent event) {
        Switchable.switchTo("ClassSelection");
    }
    
    
    @FXML
    private void handleGoToAbout(ActionEvent event) {
        Switchable.switchTo("About");
    }
    
}
