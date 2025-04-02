/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the Community Chest space on the board
 * This class is a subclass of the BoardSpace class
 * This class has a CommunityChestCard object that represents the deck of community chest cards
 * Team Member(s) responsible: Deborah, Jamell
 * */


package Model.Spaces;

import Model.Board.Player;
import Model.Cards.CommunityChestCard;
import Model.Exceptions.PlayerNotFoundException;

public class CommunityChestSpace extends BoardSpace {
    private CommunityChestCard communityChestDeck;

    /**
     * Constructor for CommunityChestSpace
     *
     * @param position
     * @param communityChestDeck Team member(s) responsible: Deborah
     */
    public CommunityChestSpace(int position, CommunityChestCard communityChestDeck) {
        super("Community Chest", position);
        this.communityChestDeck = communityChestDeck;
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
     * Draw a community chest card when a player lands on the space
     *
     * @param player The player who landed on the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        String cardDrawn = communityChestDeck.drawCard();
        communityChestDeck.useCard(cardDrawn, player);
        System.out.println(player.getName() + " drew a community chest card: " + cardDrawn);
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