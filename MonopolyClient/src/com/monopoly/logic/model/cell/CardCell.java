package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.Card;

public abstract class CardCell extends Cell
{   
    public abstract Card getCard(); 

    @Override
    public String getPropertyName()
    {
        return "";
    }

    @Override
    public String getGroupName()
    {
        return getCellName();
    }
}
