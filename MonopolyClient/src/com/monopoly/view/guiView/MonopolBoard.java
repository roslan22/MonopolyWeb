package com.monopoly.view.guiView;

import com.monopoly.controller.Controller;
import com.monopoly.view.guiView.controllers.BoardSceneController;
import com.monopoly.view.guiView.controllers.GameInitSceneController;
import com.monopoly.view.guiView.controllers.GetNamesSceneController;
import com.monopoly.view.guiView.guiEntities.GuiCell;
import com.monopoly.view.playerDescisions.PlayerBuyAssetDecision;
import com.monopoly.view.playerDescisions.PlayerBuyHouseDecision;
import com.monopoly.view.playerDescisions.PlayerResign;
import com.monopoly.ws.MonopolyWebService;
import com.monopoly.ws.MonopolyWebServiceService;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MonopolBoard extends Application
{

    private static final String BOARD_SCENE_FXML_PATH     = "BoardScene.fxml";
    private static final String GAME_INIT_SCENE_FXML_PATH = "game_init_scene.fxml";
    private static final String GET_NAMES_SCENE_FXML_PATH = "game_init_get_human_names.fxml";

    private Stage primaryStage;

    private File externalXML;
    private int  humanPlayers;
    private int  computerPlayers;
    private List<String>         humanPlayersNames    = new ArrayList<>();
    private BoardSceneController boardSceneController = null;
    private List<String> playerNames;
    private Boolean isNewGameRequired = true;
    private Scene currentBoardScene;
    MonopolyWebServiceService service;
    MonopolyWebService gameWebService;


    Procedure startNewGameProcedure      = this::startAnotherGame;
    Procedure notToStartNewGameProcedure = this::notToStartAnotherGame;

    @Override
    public void start(Stage primaryStage)
    {
        startGame(primaryStage);
    }

    private void startGame(Stage primaryStage)
    {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Monopoly");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("controllers/boardImages/Surprise.png")));
        showGameInit();
    }

    public File getExternalXML()
    {
        return externalXML;
    }

    public int getHumanPlayers()
    {
        return humanPlayers;
    }

    public int getComputerPlayers()
    {
        return computerPlayers;
    }

    public void showGameInit()
    {
        FXMLLoader gameInitXMLLoader = getFXMLLoader(GAME_INIT_SCENE_FXML_PATH);
        primaryStage.setScene(new Scene(getRoot(gameInitXMLLoader)));

        GameInitSceneController gameInitController = gameInitXMLLoader.getController();
        gameInitController.setNextListener(() -> endGameInit(gameInitController));
        primaryStage.show();
    }

    private void endGameInit(GameInitSceneController gameInitController)
    {
        externalXML = gameInitController.getXMLFile();
        //XmlMonopolyInitReader.getInstance(externalXML != null ? externalXML.getPath() : null).readInBackground();
        humanPlayers = gameInitController.getHumanPlayers();
        computerPlayers = gameInitController.getComputerPlayers();
        askForHumanPlayersNames(gameInitController.getHumanPlayers());
    }

    private void askForHumanPlayersNames(int humanPlayers)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(GET_NAMES_SCENE_FXML_PATH);
        Parent root = getRoot(getNamesFXMLLoader);
        GetNamesSceneController getNamesFXMLLoaderController = getNamesFXMLLoader.getController();
        getNamesFXMLLoaderController.setHumanPlayersNumber(humanPlayers);
        humanPlayersNames = getNamesFXMLLoaderController.getNames();
        showNextScene(humanPlayers, root, getNamesFXMLLoaderController);
    }

    private void showNextScene(int humanPlayers, Parent root, GetNamesSceneController getNamesFXMLLoaderController)
    {
        if (humanPlayers != 0)
        {
            primaryStage.setScene(new Scene(root));
            getNamesFXMLLoaderController
                    .setGetNamesEndedListener(() -> endGetNames(getNamesFXMLLoaderController.getNames()));
        }
        else
        {
            endGetNames(getNamesFXMLLoaderController.getNames());
        }
    }

    private void endGetNames(List<String> names)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(BOARD_SCENE_FXML_PATH);
        Parent root = getRoot(getNamesFXMLLoader);
        boardSceneController = getNamesFXMLLoader.getController();
        playerNames = names;

        boardSceneController.setPlayers(names, computerPlayers);
        currentBoardScene = new Scene(root);
        primaryStage.setScene(currentBoardScene);
        primaryStage.centerOnScreen();

        startGame();
    }


    private FXMLLoader getFXMLLoader(String fxmlPath)
    {
        return new FXMLLoader(getClass().getResource(fxmlPath));
    }

    private Parent getRoot(FXMLLoader fxmlLoader)
    {
        try
        {
            return (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalPathStateException(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void startGame()
    {
        while (isNewGameRequired)
        {
            playMonopoly();
            isNewGameRequired = GuiView.isNewGameRequired();
        }
    }

    private void playMonopoly()
    {
        try
        {
            startController();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            playMonopoly();
        }
    }

    private void startController()
    {
        initWebServices();
        GuiView guiView = new GuiView(this);
        Controller controller = new Controller(guiView, gameWebService);
        controller.play();
    }

    public List<String> getHumanPlayersNames()
    {
        return humanPlayersNames;
    }

    public void showMessageToPlayer(String eventMessage)
    {
        if (boardSceneController != null)
        {
            boardSceneController.showMessage(eventMessage);
        }
    }

    public void movePlayer(int cell, String playerName)
    {
        if (boardSceneController != null)
        {
            boardSceneController.movePlayer(cell, playerName);
        }
    }

    void promptPlayerToBuy(String eventMessage, PlayerBuyAssetDecision playersDecision, int eventId)
    {
        boardSceneController.promptPlayer(eventMessage, playersDecision, eventId);
    }

    void setDrawables(List<? extends GuiCell> drawableProperties)
    {
        boardSceneController.initCellsNames(drawableProperties);
    }

    public void updateMoney(String fromPlayerName, String toPlayerName, int paymentAmount)
    {
        boardSceneController.updateMoney(fromPlayerName, toPlayerName, paymentAmount);
    }

    void playerLost(String eventMessage, String playerName)
    {
        boardSceneController.playerLost(eventMessage, playerName);
    }

    void showGameOverMsg(String game_Over)
    {
        boardSceneController.showGameOverMsg(game_Over);
    }

    void showDiceRollResult(String playerName, String eventMessage, int firstResult, int secondResult)
    {
        boardSceneController.showDiceRollResult(playerName, eventMessage, firstResult, secondResult);
    }

    void showSurpriseCard(String eventMessage)
    {
        boardSceneController.showSurpriseCard(eventMessage);
    }

    void initPlayerDecisions(PlayerBuyAssetDecision playerBuyAssetDecision, PlayerBuyHouseDecision playerBuyHouseDecision,
                             PlayerResign playerResign)
    {
        boardSceneController.initPromtDecisions(playerBuyAssetDecision, playerBuyHouseDecision, playerResign);
        boardSceneController.initAnotherGameDecisions(startNewGameProcedure, notToStartNewGameProcedure);
    }

    public void buy(String playerName, int boardSquareID)
    {
        boardSceneController.buy(playerName, boardSquareID);
    }

    public void showWarningCard(String eventMessage)
    {
        boardSceneController.showWarningCard(eventMessage);
    }

    public void showPlayerResignMsg(String eventMessage, String playerName)
    {
        boardSceneController.showPlayerResignMsg(eventMessage, playerName);
    }

    public void startAnotherGame()
    {
        cleanUp();
        startGame(primaryStage);
    }

    private void cleanUp()
    {
        externalXML = null;
        humanPlayers = 0;
        computerPlayers = 0;
        humanPlayersNames    = new ArrayList<>(); boardSceneController = null;
        playerNames = new ArrayList<>();
        isNewGameRequired = true;
        currentBoardScene = null;
    }

    public void notToStartAnotherGame()
    {
        isNewGameRequired = false;
        Platform.exit();
    }
    
    private void initWebServices() {
        service = new MonopolyWebServiceService();
        gameWebService = service.getMonopolyWebServicePort();
    }

}
