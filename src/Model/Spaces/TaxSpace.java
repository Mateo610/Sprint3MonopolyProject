/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the Tax spaces on the board. Players can choose to pay a flat fee or 10% of their total assets.
 * This class is a subclass of the BoardSpace class.
 * Team Member(s) responsible: Deborah
 * */

package Model.Spaces;

import Model.Board.Banker;
import Model.Exceptions.PlayerNotFoundException;
import Model.Board.Player;

// Needs to be modified to allow user choice between paying a flat fee or 10% of their total assets
public class TaxSpace extends BoardSpace {

    private static final int INCOME_TAX_AMOUNT = 200;
    private static final int LUXURY_TAX_AMOUNT = 75;
    private static final double TAX_RATE = 0.1;
    protected Banker banker;

    public TaxSpace(String name, int position, Banker banker) {
        super(name, position);
        this.banker = banker;
    }

    /**
     * Get the purchase price of the space
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
     * Player can choose whether they want to pay a flat fee or 10% of their total assets
     *
     * @param player The player who landed on the space
     *Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        if (getName().equals("Income Tax")) {
            int percentageTax = (int) (banker.getBalance(player) * TAX_RATE);
            int taxToPay = getUserChoice(player, INCOME_TAX_AMOUNT, percentageTax);
            System.out.println(player.getName() + " chose to pay $" + taxToPay + " in Income Tax.");
        } else if (getName().equals("Luxury Tax")) {
            banker.withdraw(player, LUXURY_TAX_AMOUNT);
            System.out.println(player.getName() + " landed on Luxury Tax and paid $" + LUXURY_TAX_AMOUNT);
        }
    }

    // Temporary method that should be moved into a game manager class
    private int getUserChoice(Player player, int flatTax, int percentageTax) {
        // Needs the UI logic
        return percentageTax;
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
