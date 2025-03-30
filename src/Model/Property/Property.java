/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents a property on the Monopoly board.
 * Properties can be owned, mortgaged, and improved with houses/hotels.
 * Team Member(s) responsible: Matt
 * */

package Model.Property;

import Model.Board.Banker;
import Model.Exceptions.PlayerNotFoundException;
import Model.Spaces.BoardSpace;
import Model.Board.Player;

public class Property extends BoardSpace {

    private final int purchasePrice;
    private final int baseRent;
    private final int[] houseRents;  // Rents for 1-4 houses
    private final int hotelRent;
    private final int mortgageValue;
    private final PropertyColor color;
    private final ColorGroup colorGroup;
    private Player owner;
    private boolean isMortgaged;
    private int numHouses;
    private boolean hasHotel;
    private int housePrice;
    private Banker banker;

    /**
     * Constructor for Property
     * @param name Property name
     * @param position Board position
     * @param purchasePrice Cost to buy the property
     * @param baseRent Rent with no houses
     * @param houseRents Array of rents for 1-4 houses
     * @param hotelRent Rent with a hotel
     * @param mortgageValue Mortgage value
     * @param color Color of the property
     * @param colorGroup Color group this property belongs to
     * Team member(s) responsible: Original implementation team
     */
    public Property(String name, int position, int purchasePrice, int baseRent,
                    int[] houseRents, int hotelRent, int mortgageValue,
                    PropertyColor color, ColorGroup colorGroup) {
        super(name, position);
        this.purchasePrice = purchasePrice;
        this.baseRent = baseRent;
        this.houseRents = houseRents;
        this.hotelRent = hotelRent;
        this.mortgageValue = mortgageValue;
        this.color = color;
        this.colorGroup = colorGroup;
        this.isMortgaged = false;
        this.numHouses = 0;
        this.hasHotel = false;
        setHousePriceByColor();
    }

    /**
     * Set the house price based on the property color
     * Team member(s) responsible: Matt
     */
    private void setHousePriceByColor() {
        switch (color)
        {
            case BROWN:
            case LIGHT_BLUE:
                this.housePrice = 50;
                break;
            case PINK:
            case ORANGE:
                this.housePrice = 100;
                break;
            case RED:
            case YELLOW:
                this.housePrice = 150;
                break;
            case GREEN:
            case DARK_BLUE:
                this.housePrice = 200;
                break;
            default:
                this.housePrice = 0;
        }
    }

    /**
     * Defines what happens when a player lands on this property
     * @param player The player who landed on the space
     * Team member(s) responsible: Matt, updated to include action
     */
    @Override
public void onLanding(Player player) throws PlayerNotFoundException {
//    if (owner == null) {
//        boolean wantsToBuy = askPlayerToBuy(player);
//
//        if (wantsToBuy && banker.getBalance(player) >= getPurchasePrice()) {
//            // Player wants to buy and can afford it
//            banker.sellProperty(this, player);
//            System.out.println(player.getName() + " bought " + getName() + " for $" + getPurchasePrice());
//        } else {
//            // Player declined or can't afford - auction the property
//            System.out.println(player.getName() + " declined to buy " + getName() + ". Property goes to auction.");
//
//            // Get all players for the auction
//            List<Player> allPlayers = player.getBoard().getGame().getPlayers();
//            banker.auctionProperty(this, allPlayers);
//        }
//    } else if (owner != player && !isMortgaged) {
//        // If property is owned by another player and not mortgaged, pay rent
//        banker.collectRent(this, player);
//    }
}

    /**
     * Ask player if they want to buy the property.
     * For testing, uses a simple heuristic. In the final version, this would use GUI input.
     * 
     * @param player The player to ask
     * @return true if the player wants to buy, false otherwise
     * Team member(s) responsible: Matt
     */
//    private boolean askPlayerToBuy(Player player) {
//        try {
//            // Computer players always buy if they can afford it
//            if (player instanceof ComputerPlayer) {
//                return banker.getBalance(player) >= getPurchasePrice();
//            }
//
//            // For human players in testing, automatically buy if they have twice the property value
//            // In the future, this would show a dialog/UI
//            int balance = banker.getBalance(player);
//            boolean canEasilyAfford = balance >= (getPurchasePrice() * 2);
//
//            System.out.println(player.getName() + " has landed on " + getName() + " (Price: $" + getPurchasePrice() +
//                            ", Balance: $" + balance + ")");
//            System.out.println(canEasilyAfford ?
//                            "Automatically buying property." :
//                            "Not enough funds to easily afford. Declining purchase.");
//
//            return canEasilyAfford;
//
//        } catch (PlayerNotFoundException e) {
//            System.out.println("Error: " + e.getMessage());
//            return false;
//        }
//    }
    /**
     * Offers the property for purchase to the given player
     * @param player The player who has the option to buy
     * Team member(s) responsible: Matt
     */
    public void offerPurchase(Property property, Player player) throws PlayerNotFoundException {
        try {
            banker.sellProperty(property, player);
        } catch (Exception e) {
            if (banker.getBalance(player) > purchasePrice) {
                this.owner = player;
                banker.withdraw(player,purchasePrice);
                banker.addTitleDeed(player,property);
            }
        }
    }

    /**
     * Calculates the current rent based on houses/hotels and color group ownership
     * @return The rent amount
     * Team member(s) responsible: Matt
     */
    @Override
    public int calculateRent(Player player) throws PlayerNotFoundException {
        if (isMortgaged) {
            return 0;
        }
        int rent;
        if (hasHotel) {
            rent = hotelRent;
        } else if (numHouses > 0) {
            rent = houseRents[numHouses - 1];
        } else {
            rent = baseRent;
            if (colorGroup.hasMonopoly(owner)) {
                rent *= 2;
            }
        }
        return rent;
    }

    /**
     * Adds a house to the property
     * @return true if house was successfully added
     * Team member(s) responsible: Matt
     */
    public boolean addHouse() {
        if (numHouses < 4 && !hasHotel && canAddHouse()) {
            numHouses++;
            return true;
        }
        return false;
    }

    /**
     * Upgrades the property to a hotel
     * @return true if hotel was successfully added
     * Team member(s) responsible: Original implementation team
     */
    public boolean addHotel() {
        if (numHouses == 4 && !hasHotel && canAddHotel()) {
            numHouses = 0;
            hasHotel = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if a house can be added based on game rules
     * Team member(s) responsible: Matt
     */
    private boolean canAddHouse() {
        if (!colorGroup.hasMonopoly(owner)) {
            return false;
        }
        return colorGroup.canAddHouse(this);
    }

    /**
     * Checks if a hotel can be added based on game rules
     * Team member(s) responsible: Matt
     */
    private boolean canAddHotel() {
        return colorGroup.canAddHotel(this);
    }

    /**
     * Mortgages the property
     * @return true if property was successfully mortgaged
     * Team member(s) responsible:  Matt
     */
    public boolean mortgage() throws PlayerNotFoundException {
        if (!isMortgaged && numHouses == 0 && !hasHotel) {
            try {
                banker.buyBackProperty(this, owner);
                return true;
            } catch (Exception e) {
                isMortgaged = true;
                banker.deposit(owner,mortgageValue);
                return true;
            }
        }
        return false;
    }

    /**
     * Unmortgages the property
     * @return true if property was successfully unmortgaged
     * Team member(s) responsible: Matt
     */
    public boolean unmortgage() throws PlayerNotFoundException {
        if (isMortgaged) {
            int cost = (int)(mortgageValue * 1.1);
            try {
                if (banker.getBalance(owner) >= cost) {
                    banker.withdraw(owner, cost);
                    isMortgaged = false;
                    return true;
                }
            } catch (Exception e) {
                if (banker.getBalance(owner) >= cost) {
                    banker.withdraw(owner,cost);
                    isMortgaged = false;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove a house from the property
     * @return true if house was successfully removed
     * Team member(s) responsible: Matt
     */
    public boolean removeHouse() {
        if (numHouses > 0) {
            numHouses--;
            return true;
        }
        return false;
    }

    /**
     * Remove a hotel from the property
     * Returns the property to having 4 houses
     * @return true if hotel was successfully removed
     * Team member(s) responsible: Matt
     */
    public boolean removeHotel() {
        if (hasHotel) {
            hasHotel = false;
            numHouses = 4;
            return true;
        }
        return false;
    }

    /**
     * Get the property owner
     * @return The owner of the property
     * Team member(s) responsible: Matt
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Set the property owner
     * @param owner The new owner
     * Team member(s) responsible: Matt
     */
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Check if property is mortgaged
     * @return true if the property is mortgaged
     * Team member(s) responsible: Matt
     */
    public boolean isMortgaged() {
        return isMortgaged;
    }

    /**
     * Set the mortgage status of the property
     * @param mortgaged New mortgage status
     * Team member(s) responsible: Matt
     */
    public void setMortgaged(boolean mortgaged) {
        this.isMortgaged = mortgaged;
    }

    /**
     * Get the number of houses on the property
     * @return Number of houses
     * Team member(s) responsible: Matt
     */
    public int getNumHouses() {
        return numHouses;
    }

    /**
     * Check if property has a hotel
     * @return true if the property has a hotel
     * Team member(s) responsible: Matt
     */
    public boolean hasHotel() {
        return hasHotel;
    }

    /**
     * Get the purchase price of the property
     * @return Purchase price
     * Team member(s) responsible: Matt
     */
    public int getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Get the color group this property belongs to
     * @return Color group
     * Team member(s) responsible: Matt
     */
    public ColorGroup getColorGroup() {
        return colorGroup;
    }

    /**
     * Get the property color
     * @return Property color
     * Team member(s) responsible: Matt
     */
    public PropertyColor getColor() {
        return color;
    }

    /**
     * Get the mortgage value of the property
     * @return Mortgage value
     * Team member(s) responsible: Matt
     */
    public int getMortgageValue() {
        return mortgageValue;
    }

    /**
     * Get the price of a house for this property
     * @return House price
     * Team member(s) responsible: Matt
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * Defines what happens when a player passes over this property
     * @param player The player who passed over the space
     * Team member(s) responsible: Matt
     */
    @Override
    public void onPassing(Player player) {
        // do nothing
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + getName() + '\'' +
                ", position=" + getPosition() +
                ", purchasePrice=" + purchasePrice +
                ", baseRent=" + baseRent +
                ", houseRents=" + houseRents +
                ", hotelRent=" + hotelRent +
                ", mortgageValue=" + mortgageValue +
                ", color=" + color +
                ", colorGroup=" + colorGroup +
                ", owner=" + owner +
                ", isMortgaged=" + isMortgaged +
                ", numHouses=" + numHouses +
                ", hasHotel=" + hasHotel +
                ", housePrice=" + housePrice +
                '}';
    }

}