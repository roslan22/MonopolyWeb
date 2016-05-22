package com.monopoly.view.guiView.guiEntities;

import javafx.scene.paint.Color;

public class GuiCellBuilder
{
    private String propertyName;
    private String ownerName;
    private String groupName;
    private Color  groupColor;
    private int    housesOwned;
    private String propertySummary;

    public GuiCellBuilder setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
        return this;
    }

    public GuiCellBuilder setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public GuiCellBuilder setGroupName(String groupName)
    {
        this.groupName = groupName;
        return this;
    }

    public GuiCellBuilder setGroupColor(Color groupColor)
    {
        this.groupColor = groupColor;
        return this;
    }

    public GuiCellBuilder setHousesOwned(int housesOwned)
    {
        this.housesOwned = housesOwned;
        return this;
    }

    public GuiCellBuilder setPropertySummary(String propertySummary)
    {
        this.propertySummary = propertySummary;
        return this;
    }

    public GuiCell createGuiCell()
    {
        return new GuiCell(propertyName, ownerName, groupName, groupColor, housesOwned, propertySummary);
    }
}