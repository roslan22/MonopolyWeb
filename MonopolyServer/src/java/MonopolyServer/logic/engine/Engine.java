package MonopolyServer.logic.engine;

import MonopolyServer.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import MonopolyServer.logic.engine.monopolyInitReader.MonopolyInitReader;
import MonopolyServer.logic.events.Event;
import com.monopoly.view.guiView.controllers.DrawableProperty;

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
