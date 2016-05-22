package com.monopoly.view.guiView.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class GameInitSceneController implements Initializable
{
    private static final int INITIAL_HUMAN_PLAYERS = 1;
    private static final int INITIAL_COMPUTER_PLAYERS = 1;
    private static final int MAXIMUM_PLAYERS = 6;
    private static final int MINIMUM_PLAYERS = 2;
    private static final String LABEL_CLASS = "label";
    private static final String LABEL_INVALID_CLASS = "label-invalid";

    @FXML
    private Label humanPlayersLabel, computerPlayersLabel, selectedXMLLabel, messageLabel;

    @FXML
    private Button addHumanPlayerButton, addComputerPlayerButton, removeComputerPlayerButton,
            removeHumanPlayerButton, nextButton, clearXMLChoiceButton;

    private File         externalXMLFILE;
    private XMLValidator xmlValidator;
    private NextListener nextListener;

    public void setXmlValidator(XMLValidator xmlValidator)
    {
        this.xmlValidator = xmlValidator;
    }

    public void setNextListener(NextListener nextListener)
    {
        this.nextListener = nextListener;
    }

    public int getHumanPlayers()
    {
        return Integer.parseInt(humanPlayersLabel.getText());
    }

    public int getComputerPlayers()
    {
        return Integer.parseInt(computerPlayersLabel.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        humanPlayersLabel.setText(String.valueOf(0));
        computerPlayersLabel.setText(String.valueOf(0));
        addInitialPlayers();
    }

    private void addInitialPlayers()
    {
        IntStream.range(0, INITIAL_HUMAN_PLAYERS).forEach(i -> addHumanPlayer());
        IntStream.range(0, INITIAL_COMPUTER_PLAYERS).forEach(i -> addComputerPlayer());
    }

    @FXML
    private void onAddHumanPlayer(MouseEvent e)
    {
        addHumanPlayer();
    }

    private void addHumanPlayer()
    {
        addPlayerToLabel(humanPlayersLabel);
    }

    @FXML
    private void onRemoveHumanPlayer(MouseEvent e)
    {
        removePlayerFromLabel(humanPlayersLabel);
    }

    @FXML
    private void onAddComputerPlayer(MouseEvent e)
    {
        addComputerPlayer();
    }

    private void addComputerPlayer()
    {
        addPlayerToLabel(computerPlayersLabel);
    }

    @FXML
    private void onRemoveComputerPlayer(MouseEvent e)
    {
        removePlayerFromLabel(computerPlayersLabel);
    }

    @FXML
    private void onExternalXMLButtonPressed(MouseEvent e)
    {
        chooseExternalXMLFile();
    }

    @FXML
    private void onClearXMLButtonPressed(MouseEvent e)
    {
        externalXMLFILE = null;
        selectedXMLLabel.setText("");
        replaceXMLLabelClass(LABEL_CLASS, LABEL_INVALID_CLASS);
        nextButton.setDisable(false);
    }

    @FXML
    private void onNextButtonPressed(MouseEvent e)
    {
        nextListener.onNextButtonPressed();
    }

    private void chooseExternalXMLFile()
    {
        askForFile();
        if (externalXMLFILE != null)
        {
            selectedXMLLabel.setText(externalXMLFILE.getName());
            validateExternalXMLFile(externalXMLFILE);
        }
    }

    private void askForFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("External XML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        externalXMLFILE = fileChooser.showOpenDialog(addComputerPlayerButton.getContextMenu());
    }

    private void validateExternalXMLFile(File selectedXML)
    {
        if (xmlValidator != null && !xmlValidator.isXMLValid(selectedXML))
            signInvalidXMLFile();
        else
            signValidXMLFile();
    }

    private void signInvalidXMLFile()
    {
        nextButton.setDisable(true);
        replaceXMLLabelClass(LABEL_CLASS, LABEL_INVALID_CLASS);
        showErrorMessage();
    }

    private void showErrorMessage()
    {
        messageLabel.setVisible(true);
        messageLabel.setText("Invalid XML");
        Timer t = new Timer();
        final TimerTask task = new TimerTask() { public void run() { messageLabel.setVisible(false); t.cancel(); }};
        t.schedule(task, 2500);
    }

    private void replaceXMLLabelClass(String prevClass, String nextClass)
    {
        selectedXMLLabel.getStyleClass().remove(prevClass);
        selectedXMLLabel.getStyleClass().add(nextClass);
    }

    private void signValidXMLFile()
    {
        nextButton.setDisable(false);
        replaceXMLLabelClass(LABEL_INVALID_CLASS, LABEL_CLASS);
    }

    public File getXMLFile()
    {
        return externalXMLFILE;
    }

    private void addPlayerToLabel(Label l)
    {
        l.setText(String.valueOf(Integer.parseInt(l.getText()) + 1));
        updateButtonsDisabling();
    }

    private void removePlayerFromLabel(Label l)
    {
        if (Integer.parseInt(l.getText()) != 0)
            l.setText(String.valueOf(Integer.parseInt(l.getText()) - 1));
        updateButtonsDisabling();
    }

    private void updateButtonsDisabling()
    {
        if (getTotalPlayers() >= MAXIMUM_PLAYERS)
            disableAddButtons();
        else
            enableAddButtons();

        if (getTotalPlayers() > MINIMUM_PLAYERS)
            enableRemoveButtons();
        else
            disableRemoveButtons();

        if (getHumanPlayers() <= 1)
            removeHumanPlayerButton.setDisable(true);
        else
            removeHumanPlayerButton.setDisable(false);
    }

    private int getTotalPlayers()
    {
        return getHumanPlayers() + getComputerPlayers();
    }

    private void disableAddButtons()
    {
        addHumanPlayerButton.setDisable(true);
        addComputerPlayerButton.setDisable(true);
    }

    private void disableRemoveButtons()
    {
        removeComputerPlayerButton.setDisable(true);
        removeHumanPlayerButton.setDisable(true);
    }

    private void enableAddButtons()
    {
        addHumanPlayerButton.setDisable(false);
        addComputerPlayerButton.setDisable(false);
    }

    private void enableRemoveButtons()
    {
        removeComputerPlayerButton.setDisable(false);
        removeHumanPlayerButton.setDisable(false);
    }

    public interface XMLValidator
    {
        boolean isXMLValid(File xml);
    }

    public interface NextListener
    {
        void onNextButtonPressed();
    }
}
