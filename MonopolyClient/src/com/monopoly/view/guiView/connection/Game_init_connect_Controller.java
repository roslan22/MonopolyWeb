/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.view.guiView.connection;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ruslan
 */
public class Game_init_connect_Controller implements Initializable {

    /**
     * Initializes the controller class.
     */ 
    @FXML
    private TextField newGameName, gameNameToJoin, joinGameUserName;  
    
    @FXML 
    private Button createNewGameButton, joinGameButton; 
    
    @FXML 
    private Label errorTextLabel;
    
    private NewGameListener newGameListener = null;
    private JoinGameListener joinGameListener = null;

    @FXML
    private void onCreateNewGameClicked()
    {
        newGameListener.onCreateNewGameButtonPressed();
    }
    
    @FXML
    private void onJoinGameClicked()
    {
        joinGameListener.onJoinGameButtonPressed();
    }
    
    public String getGameNameToJoin() 
    {
        return gameNameToJoin.getText();
    }

    public String getUserNameToJoin() 
    {
        return joinGameUserName.getText();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setNewGameListener(NewGameListener newGameListener)
    {
        this.newGameListener = newGameListener;
    }
    
    public void setJoinGameListener(JoinGameListener joinGameListener)
    {
        this.joinGameListener = joinGameListener;
    }

    public void showErrorMessage(String message) 
    {
        errorTextLabel.setVisible(true);
        errorTextLabel.setText(message);
    }
    
    public String getNewGameName()
    {
        return newGameName.getText();
    }
        
    public interface NewGameListener
    {
        void onCreateNewGameButtonPressed();
    }
    
    public interface JoinGameListener
    {
        void onJoinGameButtonPressed();
    }
    
}
