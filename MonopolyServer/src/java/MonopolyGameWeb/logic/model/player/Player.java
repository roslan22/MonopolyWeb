package MonopolyGameWeb.logic.model.player;

import MonopolyGameWeb.logic.engine.MonopolyEngine;
import MonopolyGameWeb.logic.model.card.OutOfJailCard;
import MonopolyGameWeb.logic.model.cell.Cell;
import MonopolyGameWeb.logic.model.cell.City;
import MonopolyGameWeb.logic.model.cell.Jail;
import MonopolyGameWeb.logic.model.cell.Parking;
import MonopolyGameWeb.logic.model.cell.Property;
import ws.monopoly.PlayerDetails;
import ws.monopoly.PlayerStatus;
import ws.monopoly.PlayerType;

public abstract class Player
{
    public static final int START_MONEY_AMOUNT = 1500;
    protected MonopolyEngine engine;
    private   String         name;
    private   int            playerID;

    private int money = START_MONEY_AMOUNT;
    private Cell          currentCell;
    private OutOfJailCard outOfJailCard;
    private PlayerStatus playerStatus = PlayerStatus.JOINED;

    public Player(String name, int playerID, MonopolyEngine engine)
    {
        this.name = name;
        this.playerID = playerID;
        this.engine = engine;
    }
    
    public void setPlayerStatus(PlayerStatus playerStatus)
    {
        this.playerStatus = playerStatus;
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

    public Cell getCurrentCell()
    {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell)
    {
        this.currentCell = currentCell;
        currentCell.perform(this);
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public void setCurrentCellDoNotPerform(Cell currentCell)
    {
        this.currentCell = currentCell;
    }

    public void receiveMoneyFromBank(int amount)
    {
        money += amount;
        engine.addPaymentFromBankEvent(this, amount);
    }

    public abstract void askToBuyProperty(Property property);

    public void payToOtherPlayer(Player player, int amount)
    {
        if (player.equals(this) || money <= 0)
        {
            return;
        }

        int actualPayedAmount = money > amount ? amount : money;
        player.receiveMoneyFromOtherPlayer(actualPayedAmount);
        money -= actualPayedAmount;
        engine.addPayToOtherPlayerEvent(this, player, actualPayedAmount);
    }

    private void receiveMoneyFromOtherPlayer(int amount)
    {
        money += amount;
    }

    public abstract void askToBuyHouse(City city);

    public void payToBank(int amount)
    {
        int actualAmountPayed = money > amount ? amount : money;
        money -= actualAmountPayed;
        engine.addPayToBankEvent(this, actualAmountPayed);
    }

    public boolean isParking()
    {
        return getCurrentCell().isPlayerParking(this);
    }

    public boolean isInJail()
    {
        return getCurrentCell().isInJail(this);
    }

    public void exitFromParking()
    {
        if (getCurrentCell() instanceof Parking)
        {
            ((Parking) getCurrentCell()).exitFromParking(this);
        }
    }

    public void getOutOfJail()
    {
        if (getCurrentCell() instanceof Jail)
        {
            ((Jail) getCurrentCell()).getPlayerOutOfJail(this);
        }
    }

    public void receiveOutOfJailCard(OutOfJailCard outOfJailCard)
    {
        this.outOfJailCard = outOfJailCard;
    }

    public boolean hasOutOfJailCard()
    {
        return this.outOfJailCard != null;
    }

    public void returnOutOfJailCardToPack()
    {
        this.outOfJailCard.returnToPack();
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

    public PlayerDetails getDetails() {
        PlayerDetails pd = new PlayerDetails();
        pd.setMoney(money);
        pd.setName(name);
        pd.setStatus(getPlayerStatus());
        pd.setType(getPlayerType());
        return pd;
    }

    private PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
    
    public abstract PlayerType getPlayerType();
}
