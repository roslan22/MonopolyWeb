package MonopolyServer.logic.model.card;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.player.Player;

public class GotoGameStartCard extends SurpriseCard
{
    public GotoGameStartCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board) 
    {
        super.perform(player, board);
        board.moveToRoadStart(player);
    }
}
