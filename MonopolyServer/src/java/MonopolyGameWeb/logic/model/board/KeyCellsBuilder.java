package MonopolyGameWeb.logic.model.board;

import MonopolyGameWeb.logic.model.cell.AlertCell;
import MonopolyGameWeb.logic.model.cell.Jail;
import MonopolyGameWeb.logic.model.cell.JailGate;
import MonopolyGameWeb.logic.model.cell.Parking;
import MonopolyGameWeb.logic.model.cell.PropertyGroup;
import MonopolyGameWeb.logic.model.cell.SurpriseCell;

import java.util.List;

public class KeyCellsBuilder
{
    private List<AlertCell>     alertCells;
    private List<SurpriseCell>  surpriseCells;
    private List<PropertyGroup> propertyGroups;
    private Jail                jailCell;
    private JailGate            jailGate;
    private Parking             parkingCell;

    public KeyCellsBuilder setAlertCells(List<AlertCell> alertCells)
    {
        this.alertCells = alertCells;
        return this;
    }

    public KeyCellsBuilder setSurpriseCells(List<SurpriseCell> surpriseCells)
    {
        this.surpriseCells = surpriseCells;
        return this;
    }

    public KeyCellsBuilder setJailCell(Jail jailCell)
    {
        this.jailCell = jailCell;
        return this;
    }

    public KeyCellsBuilder setJailGate(JailGate jailGate)
    {
        this.jailGate = jailGate;
        return this;
    }

    public KeyCellsBuilder setParkingCell(Parking parkingCell)
    {
        this.parkingCell = parkingCell;
        return this;
    }

    public KeyCellsBuilder setPropertyGroups(List<PropertyGroup> propertyGroups)
    {
        this.propertyGroups = propertyGroups;
        return this;
    }

    public KeyCells createKeyCells()
    {
        return new KeyCells(alertCells, surpriseCells, jailCell, jailGate, parkingCell, propertyGroups);
    }
}