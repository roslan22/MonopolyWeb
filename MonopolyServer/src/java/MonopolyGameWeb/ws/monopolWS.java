/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyGameWeb.ws;

import MonopolyGameWeb.logic.engine.Engine;
import MonopolyGameWeb.logic.engine.MonopolyEngine;
import java.util.ArrayList;
import java.util.Arrays;
import javax.jws.WebService;
import ws.monopoly.Event;
import ws.monopoly.GameDoesNotExists;
import ws.monopoly.GameDoesNotExists_Exception;
import ws.monopoly.GameStatus;
import ws.monopoly.InvalidParameters;
import ws.monopoly.InvalidParameters_Exception;

/**
 *
 * @author Ruslan
 */
@WebService(serviceName = "MonopolyWebServiceService", portName = "MonopolyWebServicePort", endpointInterface = "ws.monopoly.MonopolyWebService", targetNamespace = "http://monopoly.ws/", wsdlLocation = "WEB-INF/wsdl/monopolWS/MonopolyWebServiceService.wsdl")
public class monopolWS {

    private Engine engine;
    public static String DEFAULT_XML_PATH = "configs/monopoly_config.xml";
    public static String DEFAULT_XSD_PATH = "configs/monopoly_config.xsd";

    private Engine getEngine()
    {
        if (engine == null)
            engine = new MonopolyEngine();
        return engine;
    }
    
    public java.util.List<Event> getEvents(int eventId, int playerId) throws ws.monopoly.InvalidParameters_Exception {
        return getEngine().getEvents(playerId, eventId);
    }

    public java.lang.String getBoardSchema() {
        return XmlMonopolyInitReader.getXmlContent(DEFAULT_XSD_PATH);
    }

    public java.lang.String getBoardXML() {
        return XmlMonopolyInitReader.getXmlContent(DEFAULT_XML_PATH);
    }

    public void createGame(int computerizedPlayers, int humanPlayers, java.lang.String name) 
            throws ws.monopoly.DuplicateGameName_Exception, ws.monopoly.InvalidParameters_Exception {
        if (engine != null)
            return;
        validatePlayersNumber(computerizedPlayers, humanPlayers);
        startGame(name, computerizedPlayers, humanPlayers);
    }

    public void startGame(String name, int computerizedPlayers, int humanPlayers) {
        getEngine();
        tryToLoadBoardFromXML();
        getEngine().createGame(name, computerizedPlayers, humanPlayers);
    }
    
    private void validatePlayersNumber(int computerizedPlayers, int humanPlayers) throws InvalidParameters_Exception
    {
        if (computerizedPlayers + humanPlayers > 6 || humanPlayers < 2 || computerizedPlayers < 0)
            throw new InvalidParameters_Exception("Invalid players number", new InvalidParameters());
    }

    public ws.monopoly.GameDetails getGameDetails(java.lang.String gameName) throws GameDoesNotExists_Exception {
        if (getEngine().getGameName().equals(gameName))
            return getEngine().getGameDetails();
        throw new GameDoesNotExists_Exception("No such a game name " + gameName, new GameDoesNotExists());
    }

    public java.util.List<java.lang.String> getWaitingGames() {
        return getEngine().isGameWaiting() ? new ArrayList<>() : Arrays.asList(getEngine().getGameName());
    }

    public int joinGame(java.lang.String gameName, java.lang.String playerName) 
            throws ws.monopoly.InvalidParameters_Exception, ws.monopoly.GameDoesNotExists_Exception {
        if (getEngine().getGameName().equals(gameName) && getEngine().getGameStatus() == GameStatus.WAITING)
            return getEngine().joinGame(gameName, playerName);
        if (getEngine().getGameStatus() != GameStatus.WAITING)
            throw new InvalidParameters_Exception("Game has already stated", new InvalidParameters());
        throw new GameDoesNotExists_Exception("Game does not exists", new GameDoesNotExists());
    }

    public ws.monopoly.PlayerDetails getPlayerDetails(int playerId) 
            throws ws.monopoly.GameDoesNotExists_Exception, ws.monopoly.InvalidParameters_Exception {
        return getEngine().getPlayerDetails(playerId);
    }

    public void buy(int arg0, int arg1, boolean arg2) {
        getEngine().buy(arg1, arg1, arg2);
    }

    public void resign(int playerId) throws ws.monopoly.InvalidParameters_Exception {
        getEngine().resign(playerId);
    }

    public java.util.List<ws.monopoly.PlayerDetails> getPlayersDetails(java.lang.String gameName) throws ws.monopoly.GameDoesNotExists_Exception {
        if (engine.getGameName().equals(gameName))
            return engine.getPlayersDetails();
        throw new GameDoesNotExists_Exception("Game does not exists", new GameDoesNotExists());
    }

    private void tryToLoadBoardFromXML() {
        String xmlPath = DEFAULT_XML_PATH;
        try {
            XmlMonopolyInitReader monopolyInitReader = XmlMonopolyInitReader.getInstance(xmlPath);
            monopolyInitReader.read();
            getEngine().initializeBoard(monopolyInitReader);
            //view.setDrawables(monopolyInitReader.getDrawables());
        } catch (Exception exc) {
            System.out.println("trying to load again" + exc.getMessage());
            tryToLoadBoardFromXML();
        }
    }

}
