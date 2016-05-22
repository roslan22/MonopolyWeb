package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;

public class PropertyGroup
{
    private static LinkedList<Color> PROPERTY_COLORS = new LinkedList<>(Arrays.asList(Color.CHOCOLATE,
                                                                                       Color.AQUAMARINE,
                                                                                       Color.PLUM,
                                                                                       Color.SANDYBROWN,
                                                                                       Color.LIGHTCORAL,
                                                                                       Color.LIGHTYELLOW,
                                                                                       Color.DARKSEAGREEN,
                                                                                       Color.LIGHTBLUE));
    private String                   name;
    private List<? extends Property> properties;
    private Color                   color;

    public PropertyGroup(String name, List<? extends Property> countryCities)
    {
        this.name = name;
        this.properties = countryCities;
        color = generateColor();
    }

    private static Color generateColor()
    {
        if (PROPERTY_COLORS.isEmpty())
        {
            return Color.TRANSPARENT;
        }
        return PROPERTY_COLORS.pop();
    }

    public String getName()
    {
        return name;
    }

    public List<? extends Property> getProperties()
    {
        return properties;
    }

    public boolean hasMonopoly(Player player)
    {
        for (Property p : properties)
        {
            if (p.isPropertyAvailable() || !p.getOwner().equals(player))
            {
                return false;
            }
        }
        return true;
    }

    public Color getColor()
    {
        return color;
    }
}
