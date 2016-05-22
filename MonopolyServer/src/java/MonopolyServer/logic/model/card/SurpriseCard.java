package MonopolyServer.logic.model.card;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.player.Player;

public abstract class SurpriseCard extends Card
{

    public SurpriseCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        board.addSurpriseCardEvent(player, this.getCardText());
    }
}
