package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;
import java.util.HashSet;
import java.util.Set;

public class Jail extends Cell
{
    Set<Player> playersInJail = new HashSet<>();

    public void getPlayerOutOfJail(Player player)
    {
        if (playersInJail.contains(player))
        {
            playersInJail.remove(player);
        }
    }

    @Override
    public CellType getType()
    {
        return CellType.JAIL;
    }
}
