/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the Just Visiting and Free Parking space on the board
 * This class is a subclass of the BoardSpace class
 * Team Member(s) responsible: Deborah
 * */

package Model.Spaces;

import Model.Board.Banker;
import Model.Board.Dice;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;

public class JailAndJustVisitingAndFreeParking extends BoardSpace {
    private static final int JAIL_FINE = 50;
    private static final int MAX_JAIL_TURNS = 3;
    private Banker banker;
    /**
     * Constructor for JustVisitingAndFreeParking
     *
     * @param name
     * @param position Team member(s) responsible: Deborah
     */
    public JailAndJustVisitingAndFreeParking(String name, int position) {
        super(name, position);
        this.banker = Banker.getInstance();
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
     * Do nothing when a player lands on the space
     *
     * @param player The player who landed on the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) {
        if (player.isInJail()) {
            try {
                handleJailStay(player, player.getBoard().getDice());
            } catch (PlayerNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println(player.getName() + " is just visiting jail");

        }
    }

    /**
     * Handle the player's stay in jail
     * @param player
     * @param dice
     * @return
     * @throws PlayerNotFoundException
     * Team member(s) responsible: Deborah
     */
    private boolean handleJailStay(Player player, Dice dice) throws PlayerNotFoundException {
        if (useGetOutOfJailFreeCard(player)) {
            return true;
        }
        dice.roll();
        System.out.println(player.getName() + "rolled a " + dice.getSum());
        if (dice.isDouble()) {
            releaseFromJail(player);
            System.out.println(player.getName() + " rolled doubles and is now out of jail!");
            return true;
        }
        player.incrementTurnsInJail();

        if (player.getTurnsInJail() >= MAX_JAIL_TURNS) {
            if (banker.getBalance(player) < JAIL_FINE) {
                System.out.println(player.getName() + " does not have enough money to pay the fine and is now bankrupt!");
                return false;
            } else {
                return payToGetOutOfJail(player);
            }
        }
        System.out.println(player.getName() + " has been in jail for " + player.getTurnsInJail() + " turns");
        return false;
    }

    /**
     * Pay the fine to get out of jail
     * @param player
     * @return
     * @throws PlayerNotFoundException
     */
    public boolean payToGetOutOfJail(Player player) throws PlayerNotFoundException {
        if (!player.isInJail()) {
            return false;
        }
        if (banker.getBalance(player) < JAIL_FINE) {
            System.out.println(player.getName() + " does not have enough money to pay the fine!");
            return false;
        }
        banker.withdraw(player, JAIL_FINE);
        releaseFromJail(player);
        System.out.println(player.getName() + " paid $" + JAIL_FINE + " to get out of jail!");
        return true;
    }

    /**
     * Use the Get Out of Jail Free card
     * @param player
     * @return
     * Team member(s) responsible: Deborah
     */
    public boolean useGetOutOfJailFreeCard(Player player) {
        if (!player.isInJail()) {
            return false;
        }

        if (!player.hasGetOutOfJailFreeCard()) {
            System.out.println(player.getName() + " does not have a Get Out of Jail Free card!");
            return false;
        }

        player.useGetOutOfJailFreeCard();
        releaseFromJail(player);
        System.out.println(player.getName() + " used a Get Out of Jail Free card!");
        return true;
    }

    /**
     * Release the player from jail
     * @param player
     * Team member(s) responsible: Deborah
     */
    private void releaseFromJail(Player player) {
        player.setInJail(false);
        player.resetTurnsInJail();
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
