package com.monopoly.controller;

import com.monopoly.view.View;
import com.monopoly.ws.DuplicateGameName_Exception;
import com.monopoly.ws.GameDoesNotExists_Exception;
import com.monopoly.ws.InvalidParameters_Exception;
import com.monopoly.ws.MonopolyWebService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private View view;
    private int lastEvent = 0;
    private boolean isAnotherGame = false;
    private boolean isWillingToJoinToGame = false;
    private String playerToJoinName = null;
    private String gameToJoinName = null;
    
    MonopolyWebService gameWebService;

    public boolean isIsAnotherGame() {
        return isAnotherGame;
    }

    public void setIsAnotherGame(boolean isAnotherGame) {
        this.isAnotherGame = isAnotherGame;
    }
    
    public void setJoinGame(String gameName, String playerName) 
    {
        this.isWillingToJoinToGame = true;
        this.gameToJoinName = gameName;
        this.playerToJoinName = playerName;
        
    }
    public static String GAME_NAME = "Monopoly";
    public static final int MAXIMUM_GAME_PLAYERS = 6;
    public static int DUMMY_PLAYER_ID = 1;
    public static String DEFAULT_XML_PATH = "configs/monopoly_config.xml";

    public Controller(View view, MonopolyWebService gameWebService) {
        this.view = view;
        this.gameWebService = gameWebService;
        initView(view);
    }

    private void initView(View view) {
        view.setPlayerBuyHouseDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerBuyAssetDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerResign(() -> resign(DUMMY_PLAYER_ID));
    }

    public void play() 
    {
        initGame();
        runEventsLoop();
    }

    public void continueGameAfterPromt() {
        runEventsLoop();
    }

    private void runEventsLoop() {
        List<com.monopoly.ws.Event> events = null;
        try {
            events = gameWebService.getEvents(lastEvent, DUMMY_PLAYER_ID);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (events != null && !events.isEmpty()) {
            lastEvent = events.get(events.size() - 1).getId();
            //NEXT TWO LINES FOR EX. 3
            //events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player));
            //lastReceivedEventIds.replace(player, events[events.length-1].getEventID());

            view.showEvents(events);
            try {
                events = gameWebService.getEvents(lastEvent, DUMMY_PLAYER_ID);
            } catch (InvalidParameters_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initGame() 
    {
        try 
        {
            initBoard();
            addPlayers();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void addPlayers() 
    {
        if(isWillingToJoinToGame)
        {
            joinToPlayers();
        }
        else
        {
            createPlayers();
        }
    }

    private void initBoard() throws Exception
    {
        try 
        {
            XmlMonopolyInitReader monopolyInitReader = new XmlMonopolyInitReader(gameWebService.getBoardXML());
            monopolyInitReader.read();
            view.setDrawables(monopolyInitReader.getDrawables());
        } 
        catch (Exception ex) 
        {
            throw new Exception("Board didn't initialize");
        }
    }

    private void createPlayers() {
        int humanPlayersNumber = view.getHumanPlayersNumber(MAXIMUM_GAME_PLAYERS);
        int computerPlayersNumber = view.getComputerPlayersNumber(MAXIMUM_GAME_PLAYERS - humanPlayersNumber);

        try {
            gameWebService.createGame(computerPlayersNumber, humanPlayersNumber, GAME_NAME);
        } catch (DuplicateGameName_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        addHumanPlayersNames(view.getDistinctHumanPlayerNames(humanPlayersNumber));
    }

    private void addHumanPlayersNames(List<String> humanPlayersNames) {
        for (String name : humanPlayersNames) {
            try {
                gameWebService.joinGame(GAME_NAME, name);
            } catch (GameDoesNotExists_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidParameters_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*humanPlayersNames.forEach((p) -> {
            
        }); */
    }

    private void buy(int playerID, int eventID, boolean answer) {
        gameWebService.buy(playerID, eventID, answer);
        continueGameAfterPromt();
    }

    private void resign(int playerID) {
        try {
            gameWebService.resign(playerID);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        continueGameAfterPromt();
    }

    private void joinToPlayers() 
    {

        try {
            gameWebService.joinGame(this.gameToJoinName, this.playerToJoinName);
        } 
         catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GameDoesNotExists_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Game doesn't exist", ex);
        }
        /*
        while(gameWebService.getWaitingGames().contains(this.gameToJoinName))
        {
           //Thread.sleep(1000);
        } */
    }
}
