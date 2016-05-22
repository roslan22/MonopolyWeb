package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;
import com.monopoly.view.guiView.controllers.DrawableProperty;

import javafx.scene.paint.Color;


public abstract class Cell implements DrawableProperty
{
    abstract public void perform(Player player);

    public boolean isPlayerParking(Player player)
    {
        return false;
    }

    public boolean isInJail(Player player)
    {
        return false;
    }

    public CellType getType()
    {
        return CellType.DEFAULT;
    }

    public String getCellName()
    {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getPropertyName()
    {
        return getCellName();
    }

    @Override
    public String getOwnerName()
    {
        return "";
    }

    @Override
    public String getGroupName()
    {
        return "";
    }

    @Override
    public Color getGroupColor()
    {
        return Color.TRANSPARENT;
    }

    @Override
    public int getHousesOwned()
    {
        return 0;
    }

    @Override
    public String getPropertySummary()
    {
        return getCellName();
    }
}
