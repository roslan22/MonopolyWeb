/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyGameWeb.ws;

import javax.jws.WebService;

/**
 *
 * @author Ruslan
 */
@WebService(serviceName = "MonopolyWebServiceService", portName = "MonopolyWebServicePort", endpointInterface = "ws.monopoly.MonopolyWebService", targetNamespace = "http://monopoly.ws/", wsdlLocation = "WEB-INF/wsdl/monopolWS/MonopolyWebServiceService.wsdl")
public class monopolWS {

    public java.util.List<ws.monopoly.Event> getEvents(int eventId, int playerId) throws ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
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
        throw new UnsupportedOperationException("Not implemented yet.");
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
        return 888;
    }

    public ws.monopoly.PlayerDetails getPlayerDetails(int playerId) throws ws.monopoly.GameDoesNotExists_Exception, ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void buy(int arg0, int arg1, boolean arg2) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void resign(int playerId) throws ws.monopoly.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.util.List<ws.monopoly.PlayerDetails> getPlayersDetails(java.lang.String gameName) throws ws.monopoly.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
