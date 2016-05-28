package com.monopoly.logic.model.card;

public class MoneyPenaltyCard extends AlertCard
{
    private boolean isToOtherPlayers;
    private int moneyPenalty;

    public MoneyPenaltyCard(String text, int moneyPenalty, boolean isToOtherPlayers)
    {
        super(text);
        this.isToOtherPlayers = isToOtherPlayers;
        this.moneyPenalty = moneyPenalty;
    }
}
