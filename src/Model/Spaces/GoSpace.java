package Model.Spaces;

import Model.Board.Banker;
import Model.Exceptions.PlayerNotFoundException;
import Model.Board.Player;

public class GoSpace extends BoardSpace {

    private static final int GO_MONEY = 200;
    Banker banker;

    /**
     * Constructor for GoSpace
     * Team member(s) responsible: Deborah
     */
    public GoSpace() {
        super("Go", 0);
    }

    /**
     * Give the player $200 when they land on the Go space
     *
     * @param player The player who landed on the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        banker.deposit(player, GO_MONEY);
    }

    /**
     * Give the player $200 when they pass over the Go space
     *
     * @param player The player who passed over the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onPassing(Player player) throws PlayerNotFoundException {
        banker.deposit(player, GO_MONEY);
    }
}