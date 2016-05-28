package com.monopoly.logic.model.cell;

import javafx.scene.paint.Color;

public interface DrawableProperty
{
    String getPropertyName();

    String getOwnerName();

    String getGroupName();

    Color getGroupColor();

    int getHousesOwned();

    String getPropertySummary();
}
