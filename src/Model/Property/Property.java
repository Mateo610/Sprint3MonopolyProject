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
        this.banker = Banker.getInstance();
        setHousePriceByColor();
        colorGroup.addProperty(this);
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
        if( owner != null && owner != player && !isMortgaged ) {
            banker.collectRent(this, player);
        } else if (owner == null) {
            offerPurchase(this, player);
        } else if (owner == player) {
            System.out.println(player.getName() + " owns " + getName());
        }
    }

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
            if (owner != null && colorGroup.hasMonopoly(owner)) {
                rent *= 2;
            }
        }
        return rent;
    }

    /**
     * Attempts to buy a house for the property
     * @param banker
     * @return true if the house was successfully bought
     * Team member(s) responsible: Deborah
     */
    public boolean buyHouse(Banker banker) {
        if (!canBuyHouse(banker)) {
            return false;
        }
        try {
            banker.withdraw(owner, housePrice);
            banker.decrementAvailableHouses(1);
            numHouses++;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a house can be bought for the property
     * @param banker
     * @return true if a house can be bought
     * Team member(s) responsible: Deborah
     */
    public boolean canBuyHouse(Banker banker) {
        if (owner == null) {
            return false;
        }
        if (isMortgaged) {
            return false;
        }
        if (!colorGroup.hasMonopoly(owner)) {
            return false;
        }
        if (hasHotel) {
            return false;
        }
        if (numHouses >= 4) {
            return false;
        }
        if (!colorGroup.canAddHouse(this)) {
            return false;
        }
        if (banker.getAvailableHouses() <= 0) {
            return false;
        }
        try {
            return banker.getBalance(owner) >= housePrice;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attempts to buy a hotel for the property
     * @param banker
     * @return true if the hotel was successfully bought
     * Team member(s) responsible: Deborah
     */
    public boolean buyHotel(Banker banker) {
        if (!canBuyHotel(banker)) {
            return false;
        }
        try {
            banker.withdraw(owner, housePrice);
            banker.decrementAvailableHotels();
            banker.incrementAvailableHouses(4);
            hasHotel = true;
            numHouses = 0;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a hotel can be bought for the property
     * @param banker
     * @return true if a hotel can be bought
     * Team member(s) responsible: Deborah
     */
    public boolean canBuyHotel(Banker banker) {
        if (owner == null) {
            return false;
        }
        if (isMortgaged) {
            return false;
        }
        if (!colorGroup.hasMonopoly(owner)) {
            return false;
        }
        if (numHouses != 4) {
            return false;
        }
        if (hasHotel) {
            return false;
        }
        if (!colorGroup.canAddHotel(this)) {
            return false;
        }
        if (banker.getAvailableHotels() <= 0) {
            return false;
        }
        try {
            return banker.getBalance(owner) >= housePrice;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attempts to sell a house for the property
     * @param banker
     * @return true if the house was successfully sold
     * Team member(s) responsible: Deborah
     */
    public boolean sellHouse(Banker banker) {
        if (!canSellHouse(banker)) {
            return false;
        }
        try {
            banker.deposit(owner, housePrice / 2);
            banker.incrementAvailableHouses(1);
            numHouses--;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a house can be sold for the property
     * @param banker
     * @return true if a house can be sold
     * Team member(s) responsible: Deborah
     */
    public boolean canSellHouse(Banker banker) {
        if (owner == null) {
            return false;
        }
        if (numHouses <= 0) {
            return false;
        }
        if (hasHotel) {
            return false;
        }
        return colorGroup.canSellHouse(this);
    }

    /**
     * Attempts to sell a hotel for the property
     * @param banker
     * @return true if the hotel was successfully sold
     * Team member(s) responsible: Deborah
     */
    public boolean sellHotel(Banker banker) {
        if (!canSellHotel(banker)) {
            return false;
        }
        try {
            banker.deposit(owner, housePrice / 2);
            banker.incrementAvailableHotels(1);
            banker.decrementAvailableHouses(4);
            hasHotel = false;
            numHouses = 4;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a hotel can be sold for the property
     * @param banker
     * @return true if a hotel can be sold
     * Team member(s) responsible: Deborah
     */
    public boolean canSellHotel(Banker banker) {
        if (owner == null) {
            return false;
        }
        if (!hasHotel) {
            return false;
        }
        if (banker.getAvailableHouses() < 4) {
            return false;
        }
        return true;
    }


    /**
     * Mortgages the property
     * @return true if property was successfully mortgaged
     * Team member(s) responsible:  Deborah
     */
    public boolean mortgage() throws PlayerNotFoundException {
        if (!canMortgage()) {
            return false;
        }
        try {
            banker.buyBackProperty(this, owner);
            return true;
        } catch (Exception e) {
            isMortgaged = true;
            banker.deposit(owner, mortgageValue);
        return true;
        }
    }

    /**
     * Checks if the property can be mortgaged
     * @return true if the property can be mortgaged
     * Team member(s) responsible: Deborah
     */
    public boolean canMortgage() {
        if (isMortgaged) {
            return false;
        }
        if (numHouses > 0 || hasHotel) {
            return false;
        }
        if (owner == null) {
            return false;
        }
        return true;
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
        if (numHouses > 0 && !hasHotel) {
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
        return String.format(
                "Property {\n" +
                        "  Name: '%s'\n" +
                        "  Position: %d\n" +
                        "  Purchase Price: $%d\n" +
                        "  Base Rent: $%d\n" +
                        "  House Rents: %s\n" +
                        "  Hotel Rent: $%d\n" +
                        "  Mortgage Value: $%d\n" +
                        "  Color: %s\n" +
                        "  Color Group: %s\n" +
                        "  Owner: %s\n" +
                        "  Mortgaged: %b\n" +
                        "  Number of Houses: %d\n" +
                        "  Has Hotel: %b\n" +
                        "  House Price: $%d\n" +
                        "}",
                getName(), getPosition(), purchasePrice, baseRent,
                houseRents, hotelRent, mortgageValue, color,
                colorGroup, owner, isMortgaged, numHouses,
                hasHotel, housePrice
        );
    }

}