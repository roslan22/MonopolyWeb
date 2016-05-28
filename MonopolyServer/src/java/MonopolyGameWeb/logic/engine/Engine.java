package MonopolyGameWeb.logic.engine;

import MonopolyGameWeb.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import MonopolyGameWeb.logic.engine.monopolyInitReader.MonopolyInitReader;
import MonopolyGameWeb.logic.engine.DrawableProperty;
import ws.monopoly.Event;
import java.util.List;
import ws.monopoly.GameDetails;
import ws.monopoly.GameStatus;
import ws.monopoly.InvalidParameters_Exception;
import ws.monopoly.PlayerDetails;

public interface Engine
{
    void createGame(String gameName, int computerPlayers, int humanPlayers);
    int joinGame (String gameName, String playerName) throws InvalidParameters_Exception;

    List<Event> getEvents(int playerID, int eventID);
    void buy(int playerID, int eventID, boolean buy);
    void resign(int playerID) throws InvalidParameters_Exception;

    void initializeBoard(MonopolyInitReader monopolyInitReader) throws CouldNotReadMonopolyInitReader;

    List<? extends DrawableProperty> getBoardCells();
    public String getGameName();

    public boolean isGameWaiting();

    public GameDetails getGameDetails();

    public GameStatus getGameStatus();

    public PlayerDetails getPlayerDetails(int playerID) throws InvalidParameters_Exception;

    public List<PlayerDetails> getPlayersDetails();
}
