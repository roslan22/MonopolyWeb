package MonopolyServer.logic.model.cell;

import MonopolyServer.logic.model.card.AlertCard;
import MonopolyServer.logic.model.card.Card;
import MonopolyServer.logic.model.card.CardPack;

public class AlertCell extends CardCell
{
    private CardPack<AlertCard> alertCardPack;

    public AlertCell(CardPack<AlertCard> alertCardPack)
    {
        this.alertCardPack = alertCardPack;
    }

    @Override
    public Card getCard() 
    {
        return alertCardPack.getNext();
    }

    @Override
    public CellType getType()
    {
        return CellType.ALERT_CELL;
    }

    @Override
    public String getCellName()
    {
        return "Warning";
    }
}
