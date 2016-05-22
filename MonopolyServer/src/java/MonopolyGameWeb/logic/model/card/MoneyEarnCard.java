package MonopolyGameWeb.logic.model.card;

import MonopolyGameWeb.logic.model.board.Board;
import MonopolyGameWeb.logic.model.player.Player;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int     amount;

    public MoneyEarnCard(String text, int amount, boolean isFromOtherPlayers)
    {
        super(text);
        this.isFromOtherPlayers = isFromOtherPlayers;
        this.amount = amount;
    }

    @Override
    public void perform(Player player, Board board)
    {
        super.perform(player, board);
        if (isFromOtherPlayers)
        {
            board.transferOtherPlayersMoneyTo(player, amount);
        }
        else
        {
            player.receiveMoneyFromBank(amount);
        }
    }
    
}
