package MonopolyServer.logic.model.cell;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.card.Card;
import MonopolyServer.logic.model.player.Player;

public abstract class CardCell extends Cell
{   
    private Board board;
    public abstract Card getCard();
    
    @Override
    public void perform(Player player)
    {
        getCard().perform(player, board);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String getPropertyName()
    {
        return "";
    }

    @Override
    public String getGroupName()
    {
        return getCellName();
    }
}
