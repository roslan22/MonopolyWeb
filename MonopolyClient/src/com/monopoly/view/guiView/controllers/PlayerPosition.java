package com.monopoly.view.guiView.controllers;

import javafx.scene.Node;

public class PlayerPosition {
    private int cell;
    private Node playerIcon;

    public Node getPlayerIcon() {
        return playerIcon;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }
    
    public PlayerPosition(int cell, Node playerIcon) {
        this.cell = cell;
        this.playerIcon = playerIcon;
    }

    void setPlayerIcon(Node playerIcon) {
    this.playerIcon = playerIcon;    
    }
}
