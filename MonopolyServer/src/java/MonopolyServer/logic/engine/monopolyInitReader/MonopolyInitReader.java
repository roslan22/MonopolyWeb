
package MonopolyServer.logic.engine.monopolyInitReader;


import MonopolyServer.logic.model.board.KeyCells;
import MonopolyServer.logic.model.card.AlertCard;
import MonopolyServer.logic.model.card.CardPack;
import MonopolyServer.logic.model.card.SurpriseCard;
import MonopolyServer.logic.model.cell.Cell;
import com.monopoly.view.guiView.controllers.DrawableProperty;

import java.util.List;

public interface MonopolyInitReader
{
    List<Cell> getCells();

    CardPack<AlertCard> getAlertCards();

    CardPack<SurpriseCard> getSurpriseCards();

    KeyCells getKeyCells();

    void read() throws CouldNotReadMonopolyInitReader;

    List<? extends DrawableProperty> getDrawables();
}
