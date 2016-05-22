package MonopolyServer.logic.model.card;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.player.Player;

public class OutOfJailCard extends SurpriseCard
{
    private Board board;

    public OutOfJailCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        super.perform(player, board);
        this.board = board;
        board.addOutOfJailEvent(player);
        board.removeCardFromSurprisePack(this);
        player.receiveOutOfJailCard(this);
    }

    public void returnToPack()
    {
        if (board == null)
            return;
        board.returnCardToSurprisePack(this);
        board = null;
    }
}
