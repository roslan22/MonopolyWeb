package com.monopoly.logic.engine;

import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.engine.monopolyInitReader.MonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventList;
import com.monopoly.logic.model.DiceRoll;
import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.Parking;
import com.monopoly.logic.model.cell.Property;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.HumanPlayer;
import com.monopoly.logic.model.player.Player;
import com.monopoly.view.guiView.controllers.DrawableProperty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonopolyEngine implements Engine
{
    public static final int FIRST_PLAYER_INDEX      = 0;
    public static final int END_OF_ROUND_MONEY_EARN = 200;
    public static final int MINIMUM_GAME_PLAYERS    = 2;
    public static final int MAXIMUM_TURNS           = 300;

    private List<Player> players     = new ArrayList<>();
    private List<Player> lostPlayers = new ArrayList<>();
    private int    numberOfPlayers;
    private Board  board;
    private Player currentPlayer;
    private boolean isBoardIsInitialized = false;

    private EventList events = new EventList();
    private OnBuyDecisionTaken onBuyDecisionTaken;

    @Override
    public Event[] getEvents(int playerID, int eventID)
    {
        if (eventID < 0 || (events.size() > 0 && eventID >= events.size()))
        {
            throw new InvalidParameterException("eventID should be between 0 and " + events.size());
        }
        int fromIndex = (eventID == 0) ? 0 : eventID + 1;
        return events.getEventsClone().subList(fromIndex, events.size()).toArray(new Event[events.size() - fromIndex]);
    }

    @Override
    public void createGame(String gameName, int computerPlayers, int humanPlayers)
    {
        numberOfPlayers = computerPlayers + humanPlayers;
        createComputerPlayers(computerPlayers);
        startGame();
    }

    @Override
    public int joinGame(String gameName, String playerName)
    {
        if (players.stream().anyMatch(player -> player.getName().equals(playerName)))
        {
            throw new PlayerNameAlreadyExists();
        }

        HumanPlayer newPlayer = new HumanPlayer(playerName, players.size(), this);
        players.add(newPlayer);
        startGame();
        return newPlayer.getPlayerID();
    }

    @Override
    public void buy(int playerID, int eventID, boolean buy)
    {
        onBuyDecisionTaken.buy(buy);
        onBuyDecisionTaken = null;
        playGame();
    }

    @Override
    public void resign(int playerID)
    {
        onBuyDecisionTaken = null;
        events.addPlayerResignEvent(currentPlayer);
        playerLost(currentPlayer);
        playGame();
    }

    private void createComputerPlayers(int computerPlayersCount)
    {
        IntStream.range(0, computerPlayersCount).forEach(i -> players.add(new ComputerPlayer(players.size(), this, board)));
    }

    public void initializeBoard(MonopolyInitReader monopolyInitReader) throws CouldNotReadMonopolyInitReader
    {
        board = new Board(this,
                          monopolyInitReader.getCells(),
                          monopolyInitReader.getSurpriseCards(),
                          monopolyInitReader.getKeyCells());
        isBoardIsInitialized = true;
    }

    public boolean isStillPlaying()
    {
        return (players.size() - lostPlayers.size()) >= MINIMUM_GAME_PLAYERS;
    }

    public DiceRoll rollDices()
    {
        Random r = new Random();
        DiceRoll dr = new DiceRoll(r.nextInt(6) + 1, r.nextInt(6) + 1);
        //DiceRoll dr = new DiceRoll(4, 4);
        events.addDiceRollEvent(currentPlayer, dr);
        return dr;
    }

    public void startGame()
    {
        if (players.size() < numberOfPlayers || !isBoardIsInitialized)
        {
            return;
        }
        Collections.shuffle(players);
        putPlayersAtFirstCell();
        events.addGameStartEvent();
        playGame();
    }

    public void playGame()
    {
        while (isStillPlaying() && !isWaitingForPlayer())
        {
            nextPlayer();
            movePlayer();
            checkLostPlayers();
        }
        finishGame();
    }

    private void checkLostPlayers()
    {
        players.stream().filter(p -> !lostPlayers.contains(p) && p.getMoneyAmount() <= 0).forEach(this::playerLost);
    }

    private boolean isWaitingForPlayer()
    {
        return onBuyDecisionTaken != null;
    }

    private void finishGame()
    {
        if (!isStillPlaying())
        {
            Player winnerPlayer = getPlayingPlayers().stream().max((p1, p2) -> p1.getMoneyAmount() - p2.getMoneyAmount()).get();
            events.addGameWinnerEvent(winnerPlayer);
            events.addGameOver();
        }
    }

    public void movePlayer()
    {
        if (currentPlayer.isParking())
        {
            addMovePlayerEvent(currentPlayer,
                               board.getParkingCellIndex(),
                               board.getParkingCellIndex(),
                               Parking.class.getSimpleName());
            currentPlayer.exitFromParking();
            return;
        }

        DiceRoll cr = rollDices();
        if (currentPlayer.isInJail())
        {
            if (cr.isDouble())
            {
                currentPlayer.getOutOfJail();
            }
            else
            {
                addMovePlayerEvent(currentPlayer,
                                   board.getJailCellIndex(),
                                   board.getJailCellIndex(),
                                   Jail.class.getSimpleName());
                return;
            }
        }

        board.movePlayer(currentPlayer, cr.getResult());
    }

    public void playerFinishedARound(Player player)
    {
        events.addPlayerPassedStartEvent(player, END_OF_ROUND_MONEY_EARN);
        player.receiveMoneyFromBank(END_OF_ROUND_MONEY_EARN);
    }

    private void nextPlayer()
    {
        if (currentPlayer == null)
        {
            currentPlayer = players.get(FIRST_PLAYER_INDEX);
        }
        else
        {
            moveTurnToNextPlayer();
        }
    }

    private void moveTurnToNextPlayer()
    {
        do
        {
            final int nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size();
            currentPlayer = players.get(nextPlayerIndex);
        }
        while (lostPlayers.contains(currentPlayer));
    }

    public void putPlayersAtFirstCell()
    {
        players.forEach(player -> player.setCurrentCellDoNotPerform(board.getFirstCell()));
    }

    public void payToEveryoneElse(Player payingPlayer, int amount)
    {
        getPlayingPlayers().stream().filter(p -> !p.equals(payingPlayer))
                .forEach(p -> payingPlayer.payToOtherPlayer(p, amount));
        if (payingPlayer.getMoneyAmount() <= 0)
        {
            playerLost(payingPlayer);
        }
    }

    private List<Player> getPlayingPlayers()
    {
        List<Player> playingPlayers = new ArrayList<>(players);
        playingPlayers.removeAll(lostPlayers);
        return playingPlayers;
    }

    public void transferOtherPlayersMoneyTo(Player receivePlayer, int amount)
    {
        getPlayingPlayers().stream().filter(p -> !p.equals(receivePlayer))
                .forEach(p -> p.payToOtherPlayer(receivePlayer, amount));
        getPlayingPlayers().stream().filter(p -> p.getMoneyAmount() <= 0).collect(Collectors.toList()).stream()
                .forEach(this::playerLost);
    }

    private void playerLost(Player player)
    {
        lostPlayers.add(player);
        events.addPlayerLostEvent(player);
        board.playerLost(player);
    }

    public void addMovePlayerEvent(Player player, int from, int to, String destinationName)
    {
        events.addMovePlayerEvent(player, from, to, destinationName);
    }

    public void addGoToJailEvent(Player player)
    {
        events.addGoToJailEvent(player);
    }

    public void addSurpriseCardEvent(Player player, String cardText)
    {
        events.addSurpriseCardEvent(player, cardText);
    }

    public void addAlertCardEvent(Player player, String cardText)
    {
        events.addAlertCardEvent(player, cardText);
    }

    public void addOutOfJailEvent(Player player)
    {
        events.addOutOfJailEvent(player);
    }

    public void addPlayerUsedOutOfJailCard(Player player)
    {
        events.addPlayerUsedOutOfJailCard(player);
    }

    public void addLandedOnStartSquareEvent(Player player)
    {
        events.addLandedOnStartSquareEvent(player);
    }

    public void askToBuyProperty(Property property, Player player, OnBuyDecisionTaken onBuyDecisionTaken)
    {
        this.onBuyDecisionTaken = onBuyDecisionTaken;
        events.addPromptPlayerToBuyAssetEvent(player, property.getGroupName(), property.getName(), property.getPrice(),
                                              board.getCellIndex(property));
    }

    public void askToBuyHouse(City city, Player player, OnBuyDecisionTaken onBuyDecisionTaken)
    {
        this.onBuyDecisionTaken = onBuyDecisionTaken;
        events.addPromptPlayerToBuyHouseEvent(player, city.getName(), city.getHousePrice(), board.getCellIndex(city));
    }

    public void addHouseBoughtEvent(Player player, City city)
    {
        events.addHouseBoughtEvent(player, city.getName(), board.getCellIndex(city));
    }

    public void addAssetBoughtEvent(Player player, Property property)
    {
        events.addAssertBoughtEvent(player, property.getName(), board.getCellIndex(property));
    }

    public void addPayToBankEvent(Player player, int amount)
    {
        events.addPayToBankEvent(player, amount);
    }

    public void addPayToOtherPlayerEvent(Player payingPlayer, Player payedPlayer, int amount)
    {
        events.addPayToOtherPlayerEvent(payingPlayer, payedPlayer, amount);
    }

    public void addPaymentFromBankEvent(Player player, int amount)
    {
        events.addPaymentFromBankEvent(player, amount);
    }

    public List<Player> getAllPlayers()
    {
        return players;
    }

    public interface OnBuyDecisionTaken
    {
        void buy(boolean buyDecision);
    }

    @Override
    public List<? extends DrawableProperty> getBoardCells()
    {
        return board.getCells();
    }
}
