package com.monopoly.view.playerDescisions;

@FunctionalInterface
public interface PlayerBuyHouseDecision 
{
    void onAnswer(int eventID, boolean answer);
}
