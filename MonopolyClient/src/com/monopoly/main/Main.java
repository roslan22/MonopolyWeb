package com.monopoly.main;

import com.monopoly.controller.Controller;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.view.consoleView.ConsoleView;


public class Main
{
    public static void main(String[] args)
    {
        Boolean isNewGameRequired = true;
        while(isNewGameRequired)
        {
           playMonopoly();
           isNewGameRequired = ConsoleView.isNewGameRequired();
        }
    }

    private static void playMonopoly()
    {
        try
        {
            startController();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            playMonopoly();
        }
    }

    private static void startController()
    {
        Controller controller = new Controller(new ConsoleView(), new MonopolyEngine());
        controller.play();
    }
}
