package com.monopoly.view.consoleView;

import com.monopoly.logic.events.Event;
import com.monopoly.utils.Utils;
import com.monopoly.view.View;
import com.monopoly.view.guiView.guiEntities.GuiCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class ConsoleView extends View
{
    private static final int MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN = 3;

    private Scanner scanner = new Scanner(System.in);

    public static Boolean isNewGameRequired()
    {
        System.out.println("Do you want to play again? Press 1-YES, 2-NO");
        return PlayerChoice.getValueForChoice(getUserChoice()) == PlayerChoice.YES;
    }

    private static int getUserChoice()
    {
        Scanner scanner = new Scanner(System.in);
        Integer inputNum = Utils.tryParseInt(scanner.next());

        while (inputNum == null || !PlayerChoice.isChoiceExists(inputNum))
        {
            System.out.println("Bad input format, please try again:");
            inputNum = Utils.tryParseInt(scanner.next());
        }
        return inputNum;
    }

    @Override
    public String loadExternalXmlPath()
    {
        System.out.println("Do you want to load game XML file externally ? Press 1-YES, 2-NO");
        Integer inputNum = getUserChoice();

        String userPath = null;
        if (PlayerChoice.getValueForChoice(inputNum) == PlayerChoice.YES)
        {
            userPath = tryGetXMLPathFromUser();
        }

        return userPath;
    }

    @Override
    public int getHumanPlayersNumber(int maximumAllowed)
    {
        System.out.print("Please enter a number of Human players: ");
        return getNumberFromUser(maximumAllowed);
    }

    @Override
    public int getComputerPlayersNumber(int maximumAllowed)
    {
        if (maximumAllowed > 0)
        {
            System.out.print("Please enter a number of Computer players: ");
            return getNumberFromUser(maximumAllowed);
        }
        return 0;
    }

    @Override
    public List<String> getDistinctHumanPlayerNames(int humanPlayersNumber)
    {
        System.out.println("Please enter the names of the human players: ");
        List<String> names = new ArrayList<>();
        IntStream.range(0, humanPlayersNumber).forEach(i -> names.add(getNextName(i + 1, names)));
        return names;
    }

    private String getNextName(int playerNumber, List<String> prevNames)
    {
        String name = getPlayerName(playerNumber);
        while (prevNames.contains(name))
        {
            System.out.println("name already exist");
            name = getPlayerName(playerNumber);
        }
        return name;
    }

    private String getPlayerName(int playerNumber)
    {
        String name;
        System.out.println("Please enter a name for " + playerNumber + " player:");
        scanner = new Scanner(System.in);
        name = scanner.nextLine();
        return name;
    }

    private int getNumberFromUser(int maximumAllowed)
    {
        Integer inputNum = Utils.tryParseInt(scanner.next());

        while (inputNum == null || inputNum > maximumAllowed)
        {
            System.out.println("Bad input format, please try again:");
            inputNum = Utils.tryParseInt(scanner.next());
        }

        return inputNum;
    }

    @Override
    protected void showPlayerMove(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void promptPlayerToBuy(Event event)
    {
        System.out.println(event.getEventMessage() + "\npress 1-Yes 2-No 3-Resign:");
        handlePlayerChoiceForPrompt(event, isUserWillingToBuy());
    }

    private void handlePlayerChoiceForPrompt(Event event, PlayerChoice playersChoice)
    {
        switch (playersChoice)
        {
            case YES:
                playerBuyHouseDecision.onAnswer(event.getEventID(), true);
                break;
            case NO:
                playerBuyAssetDecision.onAnswer(event.getEventID(), false);
                break;
            case RESIGN:
                playerResign.resign();
                break;
        }
    }

    private PlayerChoice isUserWillingToBuy()
    {
        return PlayerChoice.getValueForChoice(getLegalDecision());
    }

    private int getLegalDecision()
    {
        int decision = getNumberFromUser(MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN);
        while (!PlayerChoice.isChoiceExists(decision))
        {
            System.out.println("Wrong input, try again:");
            decision = getNumberFromUser(MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN);
        }
        return decision;
    }

    @Override
    protected void showGameStartedMsg()
    {
        System.out.println("Game Started");
    }

    @Override
    protected void showGameOverMsg()
    {
        System.out.println("Game Over!");
    }

    @Override
    protected void showDiceRollResult(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showGameWinner(Event event)
    {
        System.out.println(event.getEventMessage());
    }


    @Override
    protected void showAssetBoughtMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showHouseBoughtMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showLandedOnStartSquareMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showPassedStartSquareMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showPaymentMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showPlayerLostMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showPlayerResignMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " resigned");
    }

    @Override
    protected void showUsedOutOfJailCardMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " used - Out of jail card");
    }

    @Override
    protected void showSurpriseCardMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showWarrantCardMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showOutOfJailCard(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    @Override
    protected void showGoToJailMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " goes to jail");
    }

    private String tryGetXMLPathFromUser()
    {
        scanner = new Scanner(System.in);
        System.out.println("Please enter full XML path and then enter");
        String path = scanner.nextLine();

        while (path.isEmpty())
        {
            System.out.println("Bad XML path, please enter again:");
            path = scanner.nextLine();
        }

        return path;
    }

    @Override
    public void setDrawables(List<? extends GuiCell> drawableProperties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}