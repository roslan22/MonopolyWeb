package com.monopoly.view.guiView;

import com.monopoly.view.View;
import com.monopoly.view.guiView.guiEntities.GuiCell;
import com.monopoly.ws.Event;

import java.io.File;
import java.util.List;

public class GuiView extends View
{
    private MonopolBoard monopolBoard = new MonopolBoard();

    public GuiView(MonopolBoard monopolBoard)
    {
        this.monopolBoard = monopolBoard;
    }

    @Override
    public void init()
    {
        monopolBoard.showGameInit();
    }

    @Override
    public String loadExternalXmlPath()
    {
        File externalXML = monopolBoard.getExternalXML();
        return externalXML != null ? externalXML.getAbsolutePath() : null;
    }

    @Override
    public int getHumanPlayersNumber(int maximumAllowed)
    {
        return monopolBoard.getHumanPlayers();
    }

    @Override
    public int getComputerPlayersNumber(int maximumAllowed)
    {
        return monopolBoard.getComputerPlayers();
    }

    @Override
    public List<String> getDistinctHumanPlayerNames(int humanPlayersNumber)
    {
        return monopolBoard.getHumanPlayersNames();
    }

    @Override
    protected void showPlayerMove(Event event)
    {
        monopolBoard.movePlayer(event.getNextBoardSquareID(), event.getPlayerName());
        System.out.println(event.getPlayerName() + " moves to " + event.getNextBoardSquareID());
    }

    @Override
    protected void promptPlayerToBuy(Event event)
    {
      monopolBoard.promptPlayerToBuy(event.getEventMessage(), playerBuyAssetDecision,
                                     event.getId());
    }

    @Override
    protected void showGameStartedMsg()
    {
        monopolBoard.initPlayerDecisions(this.playerBuyAssetDecision,
                                         this.playerBuyHouseDecision,
                                         this.playerResign);
    }

    @Override
    protected void showGameOverMsg()
    {
        monopolBoard.showGameOverMsg("Game Over");
    }

    @Override
    protected void showDiceRollResult(Event event)
    {
        monopolBoard.showDiceRollResult(event.getPlayerName(), event.getEventMessage(), event.getFirstDiceResult(), event.getSecondDiceResult());
    }

    @Override
    protected void showGameWinner(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showAssetBoughtMsg(Event event)
    {
        monopolBoard.buy(event.getPlayerName(), event.getBoardSquareID());
    }

    @Override
    protected void showHouseBoughtMsg(Event event)
    {
        monopolBoard.buy(event.getPlayerName(), event.getBoardSquareID());
    }

    @Override
    protected void showLandedOnStartSquareMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPassedStartSquareMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPaymentMsg(Event event)
    {
        if (isPaymentToBank(event))
            monopolBoard.updateMoney(event.getPlayerName(), "", event.getPaymentAmount());
        if (isPaymentFromBank(event))
            monopolBoard.updateMoney("", event.getPlayerName(), event.getPaymentAmount());
        //if (!event.isPaymentToOrFromTreasury())
            //monopolBoard.updateMoney(event.getPlayerName(), event.getPaymentToPlayerName(), event.getPaymentAmount());
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    private boolean isPaymentFromBank(Event event)
    {
        //return event.isPaymentToOrFromTreasury() && !event.isPaymentFromUser();
        return true;
    }

    private boolean isPaymentToBank(Event event)
    {
        //return event.isPaymentToOrFromTreasury() && event.isPaymentFromUser();
        return true;
    }

    @Override
    protected void showPlayerLostMsg(Event event)
    {
        monopolBoard.playerLost(event.getEventMessage(), event.getPlayerName());
    }

    @Override
    protected void showPlayerResignMsg(Event event)
    {
         monopolBoard.showPlayerResignMsg(event.getEventMessage(), event.getPlayerName());
    }

    @Override
    protected void showUsedOutOfJailCardMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showSurpriseCardMsg(Event event)
    {
        monopolBoard.showSurpriseCard(event.getEventMessage());
    }

    @Override
    protected void showWarrantCardMsg(Event event)
    {
        monopolBoard.showWarningCard(event.getEventMessage());
    }

    @Override
    protected void showOutOfJailCard(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showGoToJailMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    public static Boolean isNewGameRequired()
    {
        return false;
    }

    @Override
    public void setDrawables(List<? extends GuiCell> drawableProperties)
    {
        monopolBoard.setDrawables(drawableProperties);
    }
}
