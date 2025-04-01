/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the utility spaces on the board
 * The class maintains the owner of the utility and interacts with the Banker to handle financial transactions.
 * Team Member(s) responsible: Deborah
 * */

package Model.Spaces;

import Model.Board.Banker;
import Model.Exceptions.PlayerNotFoundException;
import Model.Board.Player;
import Model.Board.Dice;

public class UtilitySpace extends BoardSpace {
    private static final int PURCHASE_PRICE = 150;
    private static final int RENT_FOR_ONE_OWNED = 4;
    private static final int RENT_FOR_TWO_OWNED = 10;
    private Player owner;
    private Banker banker;


    /**
     * Constructor for UtilitySpace
     *
     * @param name
     * @param position Team member(s) responsible: Deborah
     */
    public UtilitySpace(String name, int position) {
        super(name, position);
        this.owner = null;
        this.banker = Banker.getInstance();
    }

    /**
     * Get the owner of the utility
     *
     * @return The player who owns the utility
     * Team member(s) responsible: Jamell
     */
    @Override
    public int getPurchasePrice() {
        return PURCHASE_PRICE;
    }

    /**
     * Set the owner for the game
     *
     * @param owner The banker for the game
     * Team member(s) responsible: Jamell
     */
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Get the owner of the utility
     *
     * @return The player who owns the utility
     * Team member(s) responsible: Jamell
     */
    @Override
    public Player getOwner() {
        return owner;
    }


    /**
     * Pay rent to the owner if the space is owned, otherwise buy the space
     *
     * @param player The player who landed on the space
     *               Team member(s) responsible: Deborah
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        if (owner == null)
        {banker.sellProperty(this, player);
            banker.withdraw(player,PURCHASE_PRICE);
            owner = player;
        } else if
        (owner != player) {
            int rent = calculateRent(player);
            System.out.println(player.getName() + " landed on " + getName() + " and paid " + owner.getName() + " $" + rent + " in rent");
            banker.withdraw(player, rent);
            banker.deposit(owner,rent);
        }
    }

    /**
     * Calculate the rent to be paid by the player
     *
     * @param player
     * @return Team member(s) responsible: Deborah, jamell
     */
    @Override
    public int calculateRent(Player player) throws PlayerNotFoundException {
        int numOwnedByOwner = banker.getPlayerProperties(player).size(); //needs to be changed
        int diceRoll = player.getBoard().getDice().getSum();

        if (numOwnedByOwner == 1) {
            return RENT_FOR_ONE_OWNED * diceRoll;
        } else if (numOwnedByOwner == 2) {
            return RENT_FOR_TWO_OWNED * diceRoll;
        } else {
            return 0;
        }
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
