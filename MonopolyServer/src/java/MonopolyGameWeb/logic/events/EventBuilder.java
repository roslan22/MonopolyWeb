package MonopolyGameWeb.logic.events;

import MonopolyGameWeb.logic.engine.MonopolyEngine;
import ws.monopoly.EventType;
import ws.monopoly.Event;

public class EventBuilder
{
    private int       eventID;
    private EventType eventType;
    private int       timeoutCount;
    private String    playerName = "";
    private String    eventMessage = "";
    private int       boardSquareID;
    private int       firstDiceResult;
    private int       secondDiceResult;
    private boolean   playerMove = true;
    private int       nextBoardSquareID;
    private boolean   paymentToOrFromTreasury;
    private boolean   paymentFromUser;
    private String    paymentToPlayerName = "";
    private int       paymentAmount;

    public EventBuilder(int eventID, EventType eventType)
    {
        this.eventID = eventID;
        this.eventType = eventType;
    }

    public EventBuilder setTimeoutCount(int timeoutCount)
    {
        this.timeoutCount = timeoutCount;
        return this;
    }

    public EventBuilder setPlayerName(String playerName)
    {
        this.playerName = playerName;
        return this;
    }

    public EventBuilder setEventMessage(String eventMessage)
    {
        this.eventMessage = eventMessage;
        return this;
    }

    public EventBuilder setBoardSquareID(int boardSquareID)
    {
        this.boardSquareID = boardSquareID;
        return this;
    }

    public EventBuilder setFirstDiceResult(int firstDiceResult)
    {
        this.firstDiceResult = firstDiceResult;
        return this;
    }

    public EventBuilder setSecondDiceResult(int secondDiceResult)
    {
        this.secondDiceResult = secondDiceResult;
        return this;
    }

    public EventBuilder setPlayerMove(boolean playerMove)
    {
        this.playerMove = playerMove;
        return this;
    }

    public EventBuilder setNextBoardSquareID(int nextBoardSquareID)
    {
        this.nextBoardSquareID = nextBoardSquareID;
        return this;
    }

    public EventBuilder setPaymentToOrFromTreasury(boolean paymentToOrFromTreasury)
    {
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
        return this;
    }

    public EventBuilder setPaymentFromUser(boolean paymentFromUser)
    {
        this.paymentFromUser = paymentFromUser;
        return this;
    }

    public EventBuilder setPaymentToPlayerName(String paymentToPlayerName)
    {
        this.paymentToPlayerName = paymentToPlayerName;
        return this;
    }

    public EventBuilder setPaymentAmount(int paymentAmount)
    {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public Event createGameEvent()
    {
        Event event = new Event();
        event.setId(eventID);
        event.setEventMessage(eventMessage);
        event.setType(eventType);
        event.setPlayerMove(playerMove);
        event.setPlayerName(playerName);
        event.setFirstDiceResult(firstDiceResult);
        event.setSecondDiceResult(secondDiceResult);
        event.setPaymemtFromUser(paymentFromUser);
        event.setPaymentAmount(paymentAmount);
        event.setPaymentToPlayerName(paymentToPlayerName);
        event.setTimeout((int)MonopolyEngine.TIMER_DELAY);
        event.setBoardSquareID(boardSquareID);
        event.setNextBoardSquareID(nextBoardSquareID);
        return event;
    }
}