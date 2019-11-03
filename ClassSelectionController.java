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
public class ClassSelectionController extends Switchable implements Initializable {
    
    @FXML
    private Button playButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label player1NameLabel;
    
    @FXML
    private Label player2NameLabel;
    
    @FXML
    private Label player1ClassLabel;
    
    @FXML
    private Label player2ClassLabel;
    
    @FXML
    private Button player1ChangeRight;
    
    @FXML
    private Button player1ChangeLeft;
    
    @FXML
    private Button player2ChangeRight;
    
    @FXML
    private Button player2ChangeLeft;
    
    
    private ClassSelectModel player1Class;
    private ClassSelectModel player2Class;
    
    private static String player1ClassName;
    private static String player2ClassName;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        player1Class = new ClassSelectModel();
        player2Class = new ClassSelectModel();
        
        
        
    } 
    
    
    @FXML
    private void handleGoToGame(ActionEvent event) {
        Switchable.switchTo("Game");
        
    }
    
    @FXML
    private void handleGoToHome(ActionEvent event) {
        Switchable.switchTo("Home");
    }
    
    
    @FXML
    private void handleP1ChangeLeft(ActionEvent event) {
        player1ClassName = player1Class.changeClassLeft();
        player1ClassLabel.setText(player1ClassName);
    }
    
    @FXML
    private void handleP1ChangeRight(ActionEvent event) {
        player1ClassName = player1Class.changeClassRight();
        player1ClassLabel.setText(player1ClassName);
    }
    
    @FXML
    private void handleP2ChangeLeft(ActionEvent event) {
        player2ClassName = player2Class.changeClassLeft();
        player2ClassLabel.setText(player2ClassName);
    }
    
    @FXML
    private void handleP2ChangeRight(ActionEvent event) {
        player2ClassName = player2Class.changeClassRight();
        player2ClassLabel.setText(player2ClassName);
    
    }

    public String getPlayer1Class() {
        return player1ClassName;
    }
    
    public String getPlayer2Class() {
        return player2ClassName;
    }
}
