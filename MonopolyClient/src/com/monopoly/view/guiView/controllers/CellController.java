package com.monopoly.view.guiView.controllers;

import com.monopoly.view.guiView.guiEntities.GuiCell;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

public class CellController implements Initializable
{
    @FXML
    FlowPane playersPane;

    public void addPlayer(Node player)
    {
        if(!playersPane.getChildren().contains(player))
        {
            playersPane.getChildren().add(player);
        }
    }

    public void removePlayer(Node player)
    {
        playersPane.getChildren().remove(player);
    }

    public void paint()
    {
    }

    public void setDrawableProperty(GuiCell guiCell)
    {}

    protected void setTooltip(String text)
    {
        Tooltip t = new Tooltip(text);
        Tooltip.install(playersPane, t);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void playerLost(String playerName)
    {

    }

    public void buy(String playerName)
    {
    }
}
