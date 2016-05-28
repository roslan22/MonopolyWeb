package com.monopoly.logic.model.player;

public abstract class Player
{
    public static final int START_MONEY_AMOUNT = 1500;
    protected MonopolyEngine engine;
    private   String         name;
    private   int            playerID;

    private int money = START_MONEY_AMOUNT;

    public Player(String name, int playerID, MonopolyEngine engine)
    {
        this.name = name;
        this.playerID = playerID;
        this.engine = engine;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMoneyAmount()
    {
        return money;
    }


    public int getPlayerID()
    {
        return playerID;
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Player))
        {
            return false;
        }

        Player player = (Player) o;

        return getName().equals(player.getName());
    }
}
