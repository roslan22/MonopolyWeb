package com.monopoly.controller;

import javafx.scene.paint.Color;

public class ColorParser {

    public Color parse(String color) 
    {
        switch (color) 
        {
            case "CHOCOLATE":
                return Color.CHOCOLATE;
            case "AQUAMARINE":
                return Color.AQUAMARINE;
            case "PLUM":
                return Color.PLUM;
            case "SANDYBROWN":
                return Color.SANDYBROWN;
            case "LIGHTCORAL":
                return Color.LIGHTCORAL;
            case "LIGHTYELLOW":
                return Color.LIGHTYELLOW;
            case "DARKSEAGREEN":
                return Color.DARKSEAGREEN;
            case "LIGHTBLUE":
                return Color.LIGHTBLUE;
            case "TRANSPARENT":
                return Color.TRANSPARENT;
            default:
                System.out.println("Couldnt fint color " + color);
                return Color.TRANSPARENT;
        }
    }
}
