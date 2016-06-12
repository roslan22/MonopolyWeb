package com.monopoly.controller;

import com.monopoly.view.View;
import com.monopoly.ws.DuplicateGameName_Exception;
import com.monopoly.ws.Event;
import com.monopoly.ws.EventType;
import com.monopoly.ws.GameDoesNotExists_Exception;
import com.monopoly.ws.InvalidParameters_Exception;
import com.monopoly.ws.MonopolyWebService;
import java.net.SocketException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;

public class Controller {

    private View view;
    private int lastEvent = 0;
    private boolean isAnotherGame = false;
    private boolean isWillingToJoinToGame = false;
    private String playerToJoinName = null;
    private String gameToJoinName = null;
    private String newGameName = null;
    private Timer timer = new Timer();
    
    MonopolyWebService gameWebService;

    public boolean isIsAnotherGame() {
        return isAnotherGame;
    }

    public void setIsAnotherGame(boolean isAnotherGame) {
        this.isAnotherGame = isAnotherGame;
    }

    public void setJoinGame(String gameName, String playerName) {
        this.isWillingToJoinToGame = true;
        this.gameToJoinName = gameName;
        this.playerToJoinName = playerName;

    }
    public static String DEFAULT_GAME_NAME = "Monopoly";
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

    public void play() throws InvalidParameters_Exception, Exception {
        initGame();
        readEvents();
    }

    public void continueGameAfterPromt() {
        readEvents();
    }

    private void readEvents() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getEventsTimerAction();
            }
        }, 0, 3000);
    }
    
    public void getEventsTimerAction() {
        try 
        {
            System.out.println("Querying Server For Events");
            List<Event> e = gameWebService.getEvents(lastEvent, DUMMY_PLAYER_ID);
            if (!e.isEmpty()) {
                timer.cancel();
                executeEvents(e);
            }
        }
        catch (WebServiceException ex) 
        {
            ex.printStackTrace();
            getEventsTimerAction();
        }
        catch (InvalidParameters_Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void executeEvents(List<Event> e) 
    {
        lastEvent = e.get(e.size() - 1).getId();
        view.showEvents(e);
        if(!containsGameOverEvent(e)) 
        {
            readEvents();
        }
    }

    public static boolean containsGameOverEvent(List<Event> events) {
        return events.stream().map(Event::getType).anyMatch(e -> e.equals(EventType.GAME_OVER));
    }

    private void initGame() throws DuplicateGameName_Exception, InvalidParameters_Exception, Exception {
        initBoard();
        addPlayers();
    }

    private void addPlayers() throws DuplicateGameName_Exception, InvalidParameters_Exception {
        if (isWillingToJoinToGame) {
            joinToPlayers();
        } else {
            createPlayersForNewGame();
        }
    }

    private void initBoard() throws Exception {
        try {
            XmlMonopolyInitReader monopolyInitReader = new XmlMonopolyInitReader(gameWebService.getBoardXML());
            monopolyInitReader.read();
            view.setDrawables(monopolyInitReader.getDrawables());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Board didn't initialize");
        }
    }

    private void createPlayersForNewGame() throws DuplicateGameName_Exception, InvalidParameters_Exception {
        int humanPlayersNumber = view.getHumanPlayersNumber(MAXIMUM_GAME_PLAYERS);
        int computerPlayersNumber = view.getComputerPlayersNumber(MAXIMUM_GAME_PLAYERS - humanPlayersNumber);

        gameWebService.createGame(computerPlayersNumber, humanPlayersNumber, newGameName);

        joinSelfToGame(view.getCurrentPlayerName());
    }

    private void joinSelfToGame(String name) {
            try {
                System.out.println(gameWebService.joinGame(newGameName, name));
            } catch (GameDoesNotExists_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidParameters_Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void buy(int playerID, int eventID, boolean answer) {
        try {
            gameWebService.buy(playerID, eventID, answer);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resign(int playerID) {
        try {
            gameWebService.resign(playerID);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void joinToPlayers() {

        try {
            gameWebService.joinGame(this.gameToJoinName, this.playerToJoinName);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GameDoesNotExists_Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Game doesn't exist", ex);
        }
    }

    public void setGameName(String newGameName) {
        this.newGameName = newGameName;
    }
}
