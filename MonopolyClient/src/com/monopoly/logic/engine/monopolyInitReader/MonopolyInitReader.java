
package com.monopoly.logic.engine.monopolyInitReader;


import com.monopoly.logic.model.board.KeyCells;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
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
