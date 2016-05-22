package com.monopoly.controller;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.view.View;
import com.monopoly.ws.DuplicateGameName_Exception;
import com.monopoly.ws.GameDoesNotExists_Exception;
import com.monopoly.ws.InvalidParameters_Exception;
import com.monopoly.ws.MonopolyWebService;
import com.monopoly.ws.MonopolyWebServiceService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller
{
    private View   view;
    private Engine engine;
    private int lastEvent = 0;
    private boolean isAnotherGame = false;
    MonopolyWebServiceService service = new MonopolyWebServiceService();
    MonopolyWebService gameWebService;

    public boolean isIsAnotherGame() {
        return isAnotherGame;
    }

    public void setIsAnotherGame(boolean isAnotherGame) {
        this.isAnotherGame = isAnotherGame;
    }
    public static       String GAME_NAME            = "Monopoly";
    public static final int    MAXIMUM_GAME_PLAYERS = 6;
    public static       int    DUMMY_PLAYER_ID      = 1;
    public static       String DEFAULT_XML_PATH  = "configs/monopoly_config.xml";

    public Controller(View view, MonopolyEngine engine)
    {
        this.view = view;
        this.engine = engine;
        initView(view);
    }

    private void initView(View view)
    {
        view.setPlayerBuyHouseDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerBuyAssetDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerResign(() -> resign(DUMMY_PLAYER_ID));
    }

    public void play()
    {
        initGame();
        runEventsLoop();
    }
    
    public void continueGameAfterPromt()
    {
        runEventsLoop();
    }
    
    private void runEventsLoop() 
    {
        List<com.monopoly.ws.Event> events = null;
        try {
            events = gameWebService.getEvents(DUMMY_PLAYER_ID, lastEvent);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (events!= null && !events.isEmpty())
        {
            lastEvent =  events.get(events.size() - 1).getId();
            //NEXT TWO LINES FOR EX. 3
            // events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player));
            // lastReceivedEventIds.replace(player, events[events.length-1].getEventID());
            
            view.showEvents(events);
            try {
                events = gameWebService.getEvents(DUMMY_PLAYER_ID, lastEvent);
            } catch (InvalidParameters_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    private void initGame()
    {
        initWebServices();
        initBoard();
        createPlayers();
    }

    private void  initBoard()
    {
        String xmlPath;
        xmlPath = view.loadExternalXmlPath();
        
        if(xmlPath == null || xmlPath.isEmpty())
        {
            xmlPath = DEFAULT_XML_PATH;
        }      
        
        tryToLoadBoardFromXML(xmlPath);
    }

    private void tryToLoadBoardFromXML(String xmlPath) {
        try
        {
            XmlMonopolyInitReader monopolyInitReader = XmlMonopolyInitReader.getInstance(xmlPath);
            monopolyInitReader.read();
            //TODO change initializeBoard Component
            engine.initializeBoard(monopolyInitReader);
            view.setDrawables(monopolyInitReader.getDrawables());
        } catch (CouldNotReadMonopolyInitReader couldNotReadMonopolyInitReader)
        {
            System.out.println("trying to load again" + couldNotReadMonopolyInitReader.getMessage());
            initBoard();
        }
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber(MAXIMUM_GAME_PLAYERS);
        int computerPlayersNumber = view.getComputerPlayersNumber(MAXIMUM_GAME_PLAYERS - humanPlayersNumber);

        try {
            gameWebService.createGame(computerPlayersNumber, humanPlayersNumber, GAME_NAME);
        } catch (DuplicateGameName_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        //engine.createGame(GAME_NAME, computerPlayersNumber, humanPlayersNumber);
        addHumanPlayersNames(view.getDistinctHumanPlayerNames(humanPlayersNumber));
    }

    private void addHumanPlayersNames(List<String> humanPlayersNames)
    {
        humanPlayersNames.forEach(p -> {
            try {
                gameWebService.joinGame(GAME_NAME, p);
            } catch (GameDoesNotExists_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidParameters_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void buy(int playerID, int eventID, boolean answer)
    {
        gameWebService.buy(playerID, eventID, answer);
        continueGameAfterPromt();
    }

    private void resign(int playerID)
    {
        try {
            gameWebService.resign(playerID);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        continueGameAfterPromt();
    }

    private void initWebServices() {
        gameWebService = service.getMonopolyWebServicePort();
    }

}
