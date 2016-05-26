/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyGameWeb.ws;

import MonopolyGameWeb.logic.engine.Engine;
import MonopolyGameWeb.logic.engine.MonopolyEngine;
import javax.jws.WebService;
import ws.monopoly.Event;

/**
 *
 * @author Ruslan
 */
@WebService(serviceName = "MonopolyWebServiceService", portName = "MonopolyWebServicePort", endpointInterface = "ws.monopoly.MonopolyWebService", targetNamespace = "http://monopoly.ws/", wsdlLocation = "WEB-INF/wsdl/monopolWS/MonopolyWebServiceService.wsdl")
public class monopolWS {
    Engine engine;
    public static String DEFAULT_XML_PATH  = "configs/monopoly_config.xml";

   
    public java.util.List<Event> getEvents(int eventId, int playerId) throws ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        return engine.getEvents(playerId, eventId);
    }

    public java.lang.String getBoardSchema() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String getBoardXML() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void createGame(int computerizedPlayers, int humanPlayers, java.lang.String name) throws ws.monopoly.DuplicateGameName_Exception, ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        engine = new MonopolyEngine();
        tryToLoadBoardFromXML();
        engine.createGame(name, computerizedPlayers, humanPlayers);
    }

    public ws.monopoly.GameDetails getGameDetails(java.lang.String gameName) throws ws.monopoly.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.util.List<java.lang.String> getWaitingGames() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int joinGame(java.lang.String gameName, java.lang.String playerName) throws ws.monopoly.InvalidParameters_Exception, ws.monopoly.GameDoesNotExists_Exception {
        //TODO implement this method
        return engine.joinGame(gameName, playerName);
    }

    public ws.monopoly.PlayerDetails getPlayerDetails(int playerId) throws ws.monopoly.GameDoesNotExists_Exception, ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void buy(int arg0, int arg1, boolean arg2) {
        //TODO implement this method
       engine.buy(arg1, arg1, arg2);
    }

    public void resign(int playerId) throws ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        engine.resign(playerId);
    }

    public java.util.List<ws.monopoly.PlayerDetails> getPlayersDetails(java.lang.String gameName) throws ws.monopoly.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    

    private void tryToLoadBoardFromXML() {
        String xmlPath = DEFAULT_XML_PATH;
        try
        {
            XmlMonopolyInitReader monopolyInitReader = XmlMonopolyInitReader.getInstance(xmlPath);
            monopolyInitReader.read();
            engine.initializeBoard(monopolyInitReader);
            //view.setDrawables(monopolyInitReader.getDrawables());
        } 
        catch (Exception exc)
        {
            System.out.println("trying to load again" + exc.getMessage());
            tryToLoadBoardFromXML();
        }
    }
    
}
