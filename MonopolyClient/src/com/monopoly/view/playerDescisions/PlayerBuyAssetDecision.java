package com.monopoly.view.playerDescisions;

@FunctionalInterface
public interface PlayerBuyAssetDecision 
{
    void onAnswer(int eventID, boolean answer);
}
