package com.monopoly.view.guiView.guiEntities;

import com.monopoly.view.guiView.controllers.DrawableProperty;

import javafx.scene.paint.Color;

public class GuiCell implements DrawableProperty
{
    private String propertyName;
    private String ownerName;
    private String groupName;
    private Color groupColor;
    private int housesOwned = 0;
    private String propertySummary;

    public GuiCell(String propertyName, String ownerName, String groupName, Color groupColor, int housesOwned,
                   String propertySummary)
    {
        this.propertyName = propertyName;
        this.ownerName = ownerName;
        this.groupName = groupName;
        this.groupColor = groupColor;
        this.housesOwned = housesOwned;
        this.propertySummary = propertySummary;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public void setGroupColor(Color groupColor)
    {
        this.groupColor = groupColor;
    }

    public void setHousesOwned(int housesOwned)
    {
        this.housesOwned = housesOwned;
    }

    public void setPropertySummary(String propertySummary)
    {
        this.propertySummary = propertySummary;
    }

    @Override
    public String getPropertyName()
    {
        return propertyName;
    }

    @Override
    public String getOwnerName()
    {
        return ownerName;
    }

    @Override
    public String getGroupName()
    {
        return groupName;
    }

    @Override
    public Color getGroupColor()
    {
        return groupColor;
    }

    @Override
    public int getHousesOwned()
    {
        return housesOwned;
    }

    @Override
    public String getPropertySummary()
    {
        return propertySummary;
    }
}
