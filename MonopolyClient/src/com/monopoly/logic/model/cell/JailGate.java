package com.monopoly.logic.model.cell;


public class JailGate extends Cell
{
    private Jail jailCell;

    public void setJailCell(Jail jailCell)
    {
        this.jailCell = jailCell;
    }

    @Override
    public CellType getType()
    {
        return CellType.JAIL_GATE;
    }
}
