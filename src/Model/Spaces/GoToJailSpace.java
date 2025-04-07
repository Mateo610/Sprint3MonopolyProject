/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the GoTo Jail space on the board
 * This space sends the player to jail when they land on it
 * Team Member(s) responsible: Deborah
 * */

package Model.Spaces;

import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;

public class GoToJailSpace extends BoardSpace {
    private static final int JAIL_POSITION = 10;

    /**
     * Constructor for GoToJailSpace
     *
     * @param position Team member(s) responsible: Deborah
     */
    public GoToJailSpace(int position) {
        super("Go To Jail", position);
    }

    /**
     * Get the purchase price of the space
     *
     * @return The purchase price of the space
     * Team member(s) responsible: Jamell
     */
    @Override
    public int getPurchasePrice() {
        return 0; //no purchase price
    }
    /**
     * set owner for this space
     * Team member(s) responsible: Jamell
     * */
    @Override
    public void setOwner(Player owner) {
        // No owner for this space
    }

    /**
     * Calculate the rent for the space
     *
     * @return The rent for the space
     * Team member(s) responsible: Jamell
     */
    @Override
    public int calculateRent(Player player) {
        return 0; //no rent
    }

    /**
     * Get the owner of the space
     *
     * @return The owner of the space
     * Team member(s) responsible: Jamell
     */
    @Override
    public Player getOwner() {
        return null; //no owner
    }
    /**
     * Send the player to jail when they land on the Go To Jail space
     *
     * @param player The player who landed on the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        player.setInJail(true);
        player.setPosition(JAIL_POSITION); // Directly set position to jail (10)
    }

    /**
     * Do nothing when a player passes over the space
     *
     * @param player The player who passed over the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onPassing(Player player) {
        // Do nothing
    }
}