package MonopolyServer.logic.model.cell;

import MonopolyServer.logic.model.card.Card;
import MonopolyServer.logic.model.card.CardPack;
import MonopolyServer.logic.model.card.SurpriseCard;

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
