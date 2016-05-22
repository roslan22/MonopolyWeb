package com.monopoly.logic.model.player;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Property;

public class HumanPlayer extends Player
{

    public HumanPlayer(String name, int playerId, MonopolyEngine engine)
    {
        super(name, playerId, engine);
    }

    @Override
    public void askToBuyProperty(Property property)
    {
        engine.askToBuyProperty(property,
                                this,
                                buyDecision -> onBuyDecisionTaken(property, buyDecision));
    }

    private void onBuyDecisionTaken(Property property, boolean buyDecision)
    {
        if (buyDecision)
        {
            engine.addAssetBoughtEvent(this, property);
            property.buyProperty(HumanPlayer.this);
        }
    }

    @Override
    public void askToBuyHouse(City city)
    {
        engine.askToBuyHouse(city, this, buyDecision -> {
            if (buyDecision)
            {
                engine.addHouseBoughtEvent(this, city);
                city.buyHouse(HumanPlayer.this);
            }
        });
    }
}
