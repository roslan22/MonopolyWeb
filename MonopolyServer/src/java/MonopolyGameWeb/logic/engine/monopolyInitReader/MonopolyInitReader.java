
package MonopolyGameWeb.logic.engine.monopolyInitReader;


import MonopolyGameWeb.logic.model.board.KeyCells;
import MonopolyGameWeb.logic.model.card.AlertCard;
import MonopolyGameWeb.logic.model.card.CardPack;
import MonopolyGameWeb.logic.model.card.SurpriseCard;
import MonopolyGameWeb.logic.model.cell.Cell;
import MonopolyGameWeb.logic.engine.DrawableProperty;

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
