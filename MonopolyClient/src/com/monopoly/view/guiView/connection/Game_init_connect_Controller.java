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
    private TextField new_game_name, join_game_name, join_user_name;  
    
    @FXML 
    private Button createNewGameButton, joinGameButton; 
    
        private NewGameListener newGameListener;

    @FXML
    private void onCreateNewGameClicked()
    {
        newGameListener.onCreateNewGameButtonPressed();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setNewGameListener(NewGameListener newGameListener)
    {
        this.newGameListener = newGameListener;
    }
    
    public interface NewGameListener
    {
        void onCreateNewGameButtonPressed();
    }
    
}
