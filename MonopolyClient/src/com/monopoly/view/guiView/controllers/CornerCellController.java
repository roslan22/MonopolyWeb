package com.monopoly.view.guiView.controllers;

import com.monopoly.view.guiView.guiEntities.GuiCell;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CornerCellController extends CellController
{
    @FXML
    Label     cellNameLabel;
    @FXML
    ImageView backImg;

    @Override
    public void setDrawableProperty(GuiCell guiCell)
    {
        setBackground(guiCell);
    }

    public void setBackground(GuiCell guiCell)
    {
        if (backImg.getImage() != null)
            return;

        try
        {
            backImg.setImage(new Image(getClass().getResourceAsStream("boardImages/" + guiCell.getPropertyName() + ".png")));
        }catch (Exception ignored){}
    }
}
