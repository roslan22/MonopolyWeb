package com.monopoly.logic.model.cell;

import javafx.scene.paint.Color;

public interface DrawableProperty
{
    String getPropertyName();

    String getOwnerName();

    String getGroupName();

    String getGroupColor();

    int getHousesOwned();

    String getPropertySummary();
}
