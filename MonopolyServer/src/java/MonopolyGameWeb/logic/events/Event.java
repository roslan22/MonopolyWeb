package MonopolyGameWeb.logic.events;

import ws.monopoly.EventType;

public class Event extends ws.monopoly.Event
{

    int       eventID                 = 0;
    int       timeoutCount            = 0;
    EventType eventType               = null;
    boolean   paymentToOrFromTreasury = false;
    boolean   paymentFromUser         = false;

    public Event(int eventID, EventType eventType)
    {
        this.eventID = eventID;
        this.eventType = eventType;

    }

    public Event(int eventID, int timeoutCount, EventType eventType, String playerName, String eventMessage,
                 int boardSquareID, int firstDiceResult, int secondDiceResult, boolean playerMove, int nextBoardSquareID,
                 boolean paymentToOrFromTreasury, boolean paymentFromUser, String paymentToPlayerName, int paymentAmount)
    {
        this.eventID = eventID;
        this.timeoutCount = timeoutCount;
        this.eventType = eventType;
        this.playerName = playerName;
        this.eventMessage = eventMessage;
        this.boardSquareID = boardSquareID;
        this.firstDiceResult = firstDiceResult;
        this.secondDiceResult = secondDiceResult;
        this.playerMove = playerMove;
        this.nextBoardSquareID = nextBoardSquareID;
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
        this.paymentFromUser = paymentFromUser;
        this.paymentToPlayerName = paymentToPlayerName;
        this.paymentAmount = paymentAmount;
    }

    public int getEventID()
    {
        return eventID;
    }

    public int getTimeoutCount()
    {
        return timeoutCount;
    }

    public void setTimeoutCount(int timeoutCount)
    {
        this.timeoutCount = timeoutCount;
    }

    public EventType getEventType()
    {
        return eventType;
    }


    public boolean isPaymentToOrFromTreasury()
    {
        return paymentToOrFromTreasury;
    }

    public void setPaymentToOrFromTreasury(boolean paymentToOrFromTreasury)
    {
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
    }

    public boolean isPaymentFromUser()
    {
        return paymentFromUser;
    }

    public void setPaymentFromUser(boolean paymentFromUser)
    {
        this.paymentFromUser = paymentFromUser;
    }
}
