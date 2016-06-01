package com.monopoly.view.guiView.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GetNamesSceneController implements Initializable
{
    @FXML
    private Label messageLabel, playerNumberLabel;

    @FXML
    private Button nextButton;

    @FXML
    private TextField playerNameTextField;

    private int humanPlayersNumber;
    private int currentPlayerIndex = 1;
    private List<String> names = new ArrayList<>();
    private GetNamesEndedListener getNamesEndedListener;

    public void setGetNamesEndedListener(GetNamesEndedListener getNamesEndedListener)
    {
        this.getNamesEndedListener = getNamesEndedListener;
    }

    public void setHumanPlayersNumber(int humanPlayersNumber)
    {
        this.humanPlayersNumber = humanPlayersNumber;
        setCurrentPlayerLabelText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        setCurrentPlayerLabelText();
        playerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> onNameChanged(newValue));
    }

    private void setCurrentPlayerLabelText()
    {
        playerNumberLabel.setText("Enter your name:");

        //if (names.size() == humanPlayersNumber - 1 || humanPlayersNumber == 1)
        nextButton.setText("Start");
    }

    @FXML
    private void onNextButtonPressed(MouseEvent e)
    {
        nameSigned();
    }

    @FXML
    private void onEnterPressed(ActionEvent e)
    {
        nameSigned();
    }


    private void onNameChanged(String name)
    {
        if (names.contains(name))
            nameExists();
        else
            nameNotExists();
    }

    private void nameSigned()
    {
       if(playerNameTextField.getText().isEmpty())
            showErrorMessage("Please type a name");

        addName();
    }

    private void nameExists()
    {
        playerNameTextField.getStyleClass().remove("text-field");
        playerNameTextField.getStyleClass().remove("text-field");
        playerNameTextField.getStyleClass().add("text-field-invalid");
    }

    private void nameNotExists()
    {
        playerNameTextField.getStyleClass().remove("text-field-invalid");
        if (!playerNameTextField.getStyleClass().contains("text-field"))
            playerNameTextField.getStyleClass().add("text-field");
    }

    private void showErrorMessage(String errorMessage)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(errorMessage);
        Timer t = new Timer();
        final TimerTask task = new TimerTask() { public void run() { messageLabel.setVisible(false); t.cancel(); }};
        t.schedule(task, 1500);
    }

    private void addName()
    {
        if(!playerNameTextField.getText().isEmpty())
        {
        names.add(playerNameTextField.getText());
        currentPlayerIndex++;
        playerNameTextField.clear();
        setCurrentPlayerLabelText();
        if (getNamesEndedListener != null)
            getNamesEndedListener.onGetNameEnded();
        }
    }

    public List<String> getNames()
    {
        return names;
    }

    public interface GetNamesEndedListener
    {
        void onGetNameEnded();
    }
}
