package MonopolyGameWeb.logic.model.cell;

import MonopolyGameWeb.logic.model.card.Card;
import MonopolyGameWeb.logic.model.card.CardPack;
import MonopolyGameWeb.logic.model.card.SurpriseCard;

public class SurpriseCell extends CardCell
{
    private CardPack<SurpriseCard> surpriseCardPack;

    public SurpriseCell(CardPack<SurpriseCard> surpriseCardPack)
    {
        this.surpriseCardPack = surpriseCardPack;
    }

    @Override
    public Card getCard() 
    {
        return surpriseCardPack.getNext();
    }

    @Override
    public CellType getType()
    {
        return CellType.SURPRISE_CELL;
    }

    @Override
    public String getCellName()
    {
        return "Surprise";
    }
}
