package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;
import java.util.HashSet;
import java.util.Set;

public class Parking extends Cell
{
    Set<Player> parkingPlayers = new HashSet<>();

    public void exitFromParking(Player player)
    {
        if (parkingPlayers.contains(player))
        {
            parkingPlayers.remove(player);
        }
    }

    @Override
    public CellType getType()
    {
        return CellType.PARKING;
    }
}
