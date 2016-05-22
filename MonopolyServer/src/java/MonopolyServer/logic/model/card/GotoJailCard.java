package MonopolyServer.logic.model.card;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.player.Player;

public class GotoJailCard extends AlertCard
{
    public GotoJailCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        super.perform(player, board);
        board.moveToJail(player);
    }
}
