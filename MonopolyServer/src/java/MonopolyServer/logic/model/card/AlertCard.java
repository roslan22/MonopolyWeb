package MonopolyServer.logic.model.card;

import MonopolyServer.logic.model.board.Board;
import MonopolyServer.logic.model.player.Player;

public abstract class AlertCard extends Card
{
    public AlertCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        board.addAlertCardEvent(player, this.getCardText());
    }
}
