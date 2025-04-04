/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the Railroad space on the board
 * It extends the BoardSpace class and includes functionality for purchasing the railroad
 * and paying rent based on the number of railroads owned by the player.
 * Team Member(s) responsible: Deborah
 * */

package Model.Spaces;

import Model.Board.Banker;
import Model.Exceptions.PlayerNotFoundException;
import Model.Board.Player;
import Model.Property.Property;

public class Railroad extends BoardSpace {
    private static final int PURCHASE_PRICE = 200;
    private static final int BASE_RENT = 25;
    private Player owner;
    private Banker banker;

    /**
     * Constructor for Railroad
     * @param name
     * @param position Team member(s) responsible: Deborah
     */
    public Railroad(String name, int position) {
        super(name, position);
        this.owner = null;
        this.banker = Banker.getInstance();
    }


    /**
     * Get the owner of the railroad
     *
     * @return The player who owns the railroad
     * Team member(s) responsible: Deborah
     */
    @Override
    public Player getOwner() {
        return owner;
    }


    /**
     * Set the owner for the game
     *
     * @param owner The banker for the game
     * Team member(s) responsible: Deborah
     */
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Get the purchase price of the railroad
     *
     * @return The purchase price of the railroad
     * Team member(s) responsible: Deborah
     */
    @Override
    public int getPurchasePrice() {
        return PURCHASE_PRICE;
    }

    /**
     * Pay rent to the owner if the space is owned, otherwise buy the space
     *
     * @param player The player who landed on the space
     * Team member(s) responsible: Deborah, Updated latest by Matt
     * Modified by Jamell on 04/03/2025
     */
    @Override
    public void onLanding(Player player) throws PlayerNotFoundException {
        if (owner == null) {
            banker.addAvailableProperty(this);
            banker.sellProperty(this, player);
            owner = player;
            System.out.println(player.getName() + " purchased " + getName() + " for $" + PURCHASE_PRICE);
        }
        else if (owner != player)
        {
            int rent = calculateRent(player);
            banker.withdraw(player, rent);
            banker.deposit(owner, rent);
            System.out.println(player.getName() + " landed on " + getName() + " and paid " + owner.getName() + " $" + rent + " in rent");
        }
    }

    /**
     * Do nothing when a player passes over the space
     *
     * @param player The player who passed over the space
     * Team member(s) responsible: Deborah
     */
    @Override
    public void onPassing(Player player) {
        // Do nothing
    }

    /**
     * Calculate the rent based on the number of railroads owned by the owner
     * @return rent amount
     * Team member(s) responsible: Deborah
     */
    @Override
    public int calculateRent(Player player) throws PlayerNotFoundException {
        if (owner == null)
        {return 0;}
        int numRailroads = 0;
        for (BoardSpace prop : banker.getPlayerProperties(owner)) {
            if (prop instanceof Railroad) {
                numRailroads++;
            }
        }
        if (numRailroads == 1)
        {return BASE_RENT;
        } else if (numRailroads == 2)
        {return BASE_RENT * 2;
        } else if (numRailroads == 3)
        {return BASE_RENT * 4;
        }return BASE_RENT * numRailroads;
    }

}