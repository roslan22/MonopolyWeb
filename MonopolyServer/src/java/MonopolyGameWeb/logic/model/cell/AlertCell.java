package MonopolyGameWeb.logic.model.cell;

import MonopolyGameWeb.logic.model.card.AlertCard;
import MonopolyGameWeb.logic.model.card.Card;
import MonopolyGameWeb.logic.model.card.CardPack;

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
