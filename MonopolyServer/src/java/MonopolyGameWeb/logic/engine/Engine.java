package MonopolyGameWeb.logic.engine;

import MonopolyGameWeb.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import MonopolyGameWeb.logic.engine.monopolyInitReader.MonopolyInitReader;
import MonopolyGameWeb.logic.events.Event;
import MonopolyGameWeb.logic.engine.DrawableProperty;

import java.util.List;

public interface Engine
{
    void createGame(String gameName, int computerPlayers, int humanPlayers);
    int joinGame (String gameName, String playerName) throws PlayerNameAlreadyExists;

    Event[] getEvents(int playerID, int eventID);
    void buy(int playerID, int eventID, boolean buy);
    void resign(int playerID);

    void initializeBoard(MonopolyInitReader monopolyInitReader) throws CouldNotReadMonopolyInitReader;

    List<? extends DrawableProperty> getBoardCells();
}
