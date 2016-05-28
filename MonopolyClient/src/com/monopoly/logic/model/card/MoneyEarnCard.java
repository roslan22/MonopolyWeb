package com.monopoly.logic.model.card;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int     amount;

    public MoneyEarnCard(String text, int amount, boolean isFromOtherPlayers)
    {
        super(text);
        this.isFromOtherPlayers = isFromOtherPlayers;
        this.amount = amount;
    }
}
