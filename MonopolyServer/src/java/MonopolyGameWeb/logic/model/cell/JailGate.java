package MonopolyGameWeb.logic.model.cell;

import MonopolyGameWeb.logic.model.player.Player;

public class JailGate extends Cell
{
    private Jail jailCell;

    public void setJailCell(Jail jailCell)
    {
        this.jailCell = jailCell;
    }

    @Override
    public void perform(Player player)
    {
        jailCell.putInJail(player);
    }

    @Override
    public CellType getType()
    {
        return CellType.JAIL_GATE;
    }
}
