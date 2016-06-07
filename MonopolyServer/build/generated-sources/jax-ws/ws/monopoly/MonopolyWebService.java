
package ws.monopoly;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MonopolyWebService", targetNamespace = "http://monopoly.ws/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MonopolyWebService {


    /**
     * 
     * @param eventId
     * @param playerId
     * @return
     *     returns java.util.List<ws.monopoly.Event>
     * @throws InvalidParameters_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEvents", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetEvents")
    @ResponseWrapper(localName = "getEventsResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetEventsResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getEventsRequest", output = "http://monopoly.ws/MonopolyWebService/getEventsResponse", fault = {
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/getEvents/Fault/InvalidParameters")
    })
    public List<Event> getEvents(
        @WebParam(name = "eventId", targetNamespace = "")
        int eventId,
        @WebParam(name = "playerId", targetNamespace = "")
        int playerId)
        throws InvalidParameters_Exception
    ;

    /**
     * 
     * @param gameName
     * @return
     *     returns java.util.List<ws.monopoly.PlayerDetails>
     * @throws GameDoesNotExists_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPlayersDetails", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetPlayersDetails")
    @ResponseWrapper(localName = "getPlayersDetailsResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetPlayersDetailsResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getPlayersDetailsRequest", output = "http://monopoly.ws/MonopolyWebService/getPlayersDetailsResponse", fault = {
        @FaultAction(className = GameDoesNotExists_Exception.class, value = "http://monopoly.ws/MonopolyWebService/getPlayersDetails/Fault/GameDoesNotExists")
    })
    public List<PlayerDetails> getPlayersDetails(
        @WebParam(name = "gameName", targetNamespace = "")
        String gameName)
        throws GameDoesNotExists_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getWaitingGames", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetWaitingGames")
    @ResponseWrapper(localName = "getWaitingGamesResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetWaitingGamesResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getWaitingGamesRequest", output = "http://monopoly.ws/MonopolyWebService/getWaitingGamesResponse")
    public List<String> getWaitingGames();

    /**
     * 
     * @param humanPlayers
     * @param name
     * @param computerizedPlayers
     * @throws InvalidParameters_Exception
     * @throws DuplicateGameName_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "createGame", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.CreateGame")
    @ResponseWrapper(localName = "createGameResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.CreateGameResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/createGameRequest", output = "http://monopoly.ws/MonopolyWebService/createGameResponse", fault = {
        @FaultAction(className = DuplicateGameName_Exception.class, value = "http://monopoly.ws/MonopolyWebService/createGame/Fault/DuplicateGameName"),
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/createGame/Fault/InvalidParameters")
    })
    public void createGame(
        @WebParam(name = "computerizedPlayers", targetNamespace = "")
        int computerizedPlayers,
        @WebParam(name = "humanPlayers", targetNamespace = "")
        int humanPlayers,
        @WebParam(name = "name", targetNamespace = "")
        String name)
        throws DuplicateGameName_Exception, InvalidParameters_Exception
    ;

    /**
     * 
     * @param playerId
     * @throws InvalidParameters_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "resign", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.Resign")
    @ResponseWrapper(localName = "resignResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.ResignResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/resignRequest", output = "http://monopoly.ws/MonopolyWebService/resignResponse", fault = {
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/resign/Fault/InvalidParameters")
    })
    public void resign(
        @WebParam(name = "playerId", targetNamespace = "")
        int playerId)
        throws InvalidParameters_Exception
    ;

    /**
     * 
     * @param gameName
     * @param playerName
     * @return
     *     returns int
     * @throws InvalidParameters_Exception
     * @throws GameDoesNotExists_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "joinGame", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.JoinGame")
    @ResponseWrapper(localName = "joinGameResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.JoinGameResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/joinGameRequest", output = "http://monopoly.ws/MonopolyWebService/joinGameResponse", fault = {
        @FaultAction(className = GameDoesNotExists_Exception.class, value = "http://monopoly.ws/MonopolyWebService/joinGame/Fault/GameDoesNotExists"),
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/joinGame/Fault/InvalidParameters")
    })
    public int joinGame(
        @WebParam(name = "gameName", targetNamespace = "")
        String gameName,
        @WebParam(name = "playerName", targetNamespace = "")
        String playerName)
        throws GameDoesNotExists_Exception, InvalidParameters_Exception
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBoardXML", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetBoardXML")
    @ResponseWrapper(localName = "getBoardXMLResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetBoardXMLResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getBoardXMLRequest", output = "http://monopoly.ws/MonopolyWebService/getBoardXMLResponse")
    public String getBoardXML();

    /**
     * 
     * @param playerId
     * @return
     *     returns ws.monopoly.PlayerDetails
     * @throws InvalidParameters_Exception
     * @throws GameDoesNotExists_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPlayerDetails", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetPlayerDetails")
    @ResponseWrapper(localName = "getPlayerDetailsResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetPlayerDetailsResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getPlayerDetailsRequest", output = "http://monopoly.ws/MonopolyWebService/getPlayerDetailsResponse", fault = {
        @FaultAction(className = GameDoesNotExists_Exception.class, value = "http://monopoly.ws/MonopolyWebService/getPlayerDetails/Fault/GameDoesNotExists"),
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/getPlayerDetails/Fault/InvalidParameters")
    })
    public PlayerDetails getPlayerDetails(
        @WebParam(name = "playerId", targetNamespace = "")
        int playerId)
        throws GameDoesNotExists_Exception, InvalidParameters_Exception
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBoardSchema", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetBoardSchema")
    @ResponseWrapper(localName = "getBoardSchemaResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetBoardSchemaResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getBoardSchemaRequest", output = "http://monopoly.ws/MonopolyWebService/getBoardSchemaResponse")
    public String getBoardSchema();

    /**
     * 
     * @param gameName
     * @return
     *     returns ws.monopoly.GameDetails
     * @throws GameDoesNotExists_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getGameDetails", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetGameDetails")
    @ResponseWrapper(localName = "getGameDetailsResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.GetGameDetailsResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/getGameDetailsRequest", output = "http://monopoly.ws/MonopolyWebService/getGameDetailsResponse", fault = {
        @FaultAction(className = GameDoesNotExists_Exception.class, value = "http://monopoly.ws/MonopolyWebService/getGameDetails/Fault/GameDoesNotExists")
    })
    public GameDetails getGameDetails(
        @WebParam(name = "gameName", targetNamespace = "")
        String gameName)
        throws GameDoesNotExists_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @throws InvalidParameters_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "buy", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.Buy")
    @ResponseWrapper(localName = "buyResponse", targetNamespace = "http://monopoly.ws/", className = "ws.monopoly.BuyResponse")
    @Action(input = "http://monopoly.ws/MonopolyWebService/buyRequest", output = "http://monopoly.ws/MonopolyWebService/buyResponse", fault = {
        @FaultAction(className = InvalidParameters_Exception.class, value = "http://monopoly.ws/MonopolyWebService/buy/Fault/InvalidParameters")
    })
    public void buy(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        boolean arg2)
        throws InvalidParameters_Exception
    ;

}
