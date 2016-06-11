package MonopolyGameWeb.logic.model.cell;

import MonopolyGameWeb.logic.model.player.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PropertyGroup
{
    private static LinkedList<String> PROPERTY_COLORS = new LinkedList<>(Arrays.asList("CHOCOLATE",
                                                                                       "AQUAMARINE",
                                                                                       "PLUM",
                                                                                       "SANDYBROWN",
                                                                                       "LIGHTCORAL",
                                                                                       "LIGHTYELLOW",
                                                                                       "DARKSEAGREEN",
                                                                                       "LIGHTBLUE"));
    private String                   name;
    private List<? extends Property> properties;
    private String                   color;

    public PropertyGroup(String name, List<? extends Property> countryCities)
    {
        this.name = name;
        this.properties = countryCities;
        color = generateColor();
    }

    private static String generateColor()
    {
        if (PROPERTY_COLORS.isEmpty())
        {
            return "TRANSPARENT";
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

    public String getColor()
    {
        return color;
    }
}
