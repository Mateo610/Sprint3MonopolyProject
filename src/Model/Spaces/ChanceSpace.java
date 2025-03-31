/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the Chance space on the board.
 * When a player lands on this space, they draw a chance card.
 * Team Member(s) responsible: Deborah, Jamell
 * */

package Model.Spaces;
import Model.Board.Player;
import Model.Cards.ChanceCard;

public class ChanceSpace extends BoardSpace {

    private ChanceCard chanceDeck;

    /**
     * Constructor for ChanceSpace
     *
     * @param position
     * @param chanceDeck Team member(s) responsible: Deborah
     */
    public ChanceSpace(int position, ChanceCard chanceDeck) {
        super("Chance", position);
        this.chanceDeck = chanceDeck;
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
     * Draw a chance card when a player lands on the space
     *
     * @param player The player who landed on the space
     * Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) {
        String cardDrawn = chanceDeck.drawCard();
        System.out.println(player.getName() + " drew a chance card: " + cardDrawn);
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