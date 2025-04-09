/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class is responsible for managing the player's money and transactions
 * Team Member(s) responsible: Jamell
 */

package Model.Board;
import Model.Exceptions.InsufficientFundsException;
import Model.Exceptions.InvalidTransactionException;
import Model.Exceptions.PlayerAlreadyExistsException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.ColorGroup;
import Model.Property.Property;
import Model.Property.PropertyColor;
import Model.Spaces.BoardSpace;
import Model.Spaces.Railroad;
import Model.Spaces.UtilitySpace;

import java.util.*;

public class Banker {

    private int availableHouses;
    private int availableHotels;
    private static final int MAX_HOUSES = 32;
    private static final int MAX_HOTELS = 12;
    private static final int GO_MONEY = 200;
    private HashMap<Player, ArrayList<BoardSpace>> titleDeeds;
    private HashMap<Player, Integer> playerBalances;
    private ArrayList<BoardSpace> availableProperties;
    private static Banker instance;

    private Banker() {
        this.playerBalances = new HashMap<>();
        this.availableProperties = new ArrayList<>();
        this.availableHouses = MAX_HOUSES;
        this.availableHotels = MAX_HOTELS;
        this.titleDeeds = new HashMap<>();

    }

    /**
     * Get the Banker instance
     * @return Banker instance
     * Team member(s) responsible: Jamell
     */
    public static Banker getInstance() {
        if (instance == null) {
            instance = new Banker();
        }
        return instance;
    }

    /**
     * Get the list of available properties
     *
     * @return List of available properties
     * Team member(s) responsible: Jamell
     */
    public ArrayList<BoardSpace> getAvailableProperties() {
        return availableProperties;
    }

    /**
     * Add a property to the banker's list of available properties
     * Team member(s) responsible: Jamell
     */
    public void addAvailableProperty(BoardSpace property) {
        availableProperties.add(property);
    }

    /**
     * Adds a player to the banker's list of players
     * if player is already in the list, throws PlayerNotFoundException
     *
     * @param player Player to add
     * Team member(s) responsible: Jamell
     */
    public void addPlayer(Player player) throws PlayerAlreadyExistsException {
        if (playerBalances.containsKey(player)) {
            throw new PlayerAlreadyExistsException();
        }
        playerBalances.put(player, 1500);
    }

    /**
     * removes a player from the banker's list of players
     * if player is not in the list, throws PlayerNotFoundException
     *
     * @param player Player to remove
     *               Team member(s) responsible: Jamell
     */
    public void removePlayer(Player player) throws PlayerNotFoundException {
        if (!playerBalances.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        playerBalances.remove(player);
        titleDeeds.remove(player);
    }

    /**
     * Gets a player's balance from the banker
     * if player is not in the list, throws PlayerNotFoundException
     *
     * @param player Player to get balance of
     * @return int Player's balance
     * Team member(s) responsible: Jamell
     */
    public int getBalance(Player player) throws PlayerNotFoundException {
        if (!playerBalances.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        return playerBalances.get(player);
    }

    /**
     * Deposits money into a player's account
     * if player is not in the list, throws PlayerNotFoundException
     *
     * @param player Player to deposit money into
     * @param amount Amount to deposit
     * Team member(s) responsible: Jamell
     */
    public void deposit(Player player, int amount) throws PlayerNotFoundException {
        if (!playerBalances.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        if (amount < 0) {
            throw new InvalidTransactionException();
        }
        playerBalances.put(player, playerBalances.get(player) + amount);
    }

    /**
     * Withdraws money from a player's account
     * if player is not in the list, throws PlayerNotFoundException
     *
     * @param player Player to withdraw money from
     * @param amount Amount to withdraw
     *               Team member(s) responsible: Jamell
     */
    public void withdraw(Player player, int amount) throws PlayerNotFoundException, InvalidTransactionException, InsufficientFundsException {
        if (!playerBalances.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        if (amount < 0) {
            throw new InvalidTransactionException();
        }
        if (playerBalances.get(player) < amount) {
            throw new InsufficientFundsException();
        }
        playerBalances.put(player, playerBalances.get(player) - amount);
    }

    /**
     * Add title deeds of a players propertu
     * Team member(s) responsible: Jamell
     */
    public void addTitleDeed(Player player, BoardSpace property) {
        titleDeeds.putIfAbsent(player, new ArrayList<>());
        titleDeeds.get(player).add(property);
        property.setOwner(player);
        availableProperties.remove(property);
    }

    /**
     * Remove title deeds of a players property
     * Team member(s) responsible: Jamell
     **/
    public void removeTitleDeed(Player player, BoardSpace property) throws PlayerNotFoundException {
        if (!titleDeeds.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        ArrayList<BoardSpace> properties = titleDeeds.get(player);
        properties.remove(property);
        property.setOwner(null);
        availableProperties.add(property);
        if (properties.isEmpty()) {
            titleDeeds.remove(player);
        }
    }

    /**
     * Get title deeds of a players property
     * Team member(s) responsible: Jamell
     */
    public HashMap<Player, ArrayList<BoardSpace>> getTitleDeedsAll() {
        return titleDeeds;
    }

    /**
     * Get title deeds of a player
     * Team member(s) responsible: Jamell
     */
    public ArrayList<BoardSpace> getPlayerProperties(Player player) throws PlayerNotFoundException {
        if (!titleDeeds.containsKey(player)) {
            throw new PlayerNotFoundException();
        }
        return new ArrayList<>(titleDeeds.get(player));
    }

    /**
     * Transfer money from one player to another
     *
     * @param from   The player to transfer from
     * @param to     The player to transfer to
     * @param amount The amount to transfer
     * @throws PlayerNotFoundException     if player is not found
     * @throws InvalidTransactionException if transaction is invalid
     * @throws InsufficientFundsException  if player has insufficient funds
     *                                     Team member(s) responsible: Matt, Jamell
     */
    public void transferMoney(Player from, Player to, int amount) throws PlayerNotFoundException {
        if (amount < 0) {
            throw new InvalidTransactionException();
        }
        if (getBalance(from) < amount) {
            throw new InsufficientFundsException();
        }
        withdraw(from, amount);
        deposit(to, amount);
    }


    /**
     * Pay GO money to a player
     *
     * @param player The player
     * @throws PlayerNotFoundException if player is not found
     *                                 Team member(s) responsible: Matt
     */
    public void payGoMoney(Player player) throws PlayerNotFoundException {
        deposit(player, GO_MONEY);
    }


    /**
     * Sell a property to a player
     * @param property The property to sell
     * @param player   The player buying the property
     * @throws PlayerNotFoundException    if player is not found
     * @throws InsufficientFundsException if player has insufficient funds
     * Team member(s) responsible: Matt
     */
    public void sellProperty(BoardSpace property, Player player) throws PlayerNotFoundException {
        if (!availableProperties.contains(property)) {
            throw new InvalidTransactionException();
        }
        int price = property.getPurchasePrice();
        withdraw(player, price);
        availableProperties.remove(property);
        property.setOwner(player);
        addTitleDeed(player, property);
    }

    /**
     * Buy a property back from a player (for mortgaging)
     *
     * @param property The property to buy
     * @param player   The player selling the property
     * @throws PlayerNotFoundException if player is not found
     *  Team member(s) responsible: Matt
     */
    public void buyBackProperty(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player) {
            throw new InvalidTransactionException();
        }
        int mortgageValue = property.getMortgageValue();
        deposit(player, mortgageValue);
        property.setMortgaged(true);
    }

    /**
     * Sell a house to a player for a specific property
     *
     * @param property The property to add a house to
     * @param player   The player buying the house
     * @throws PlayerNotFoundException    if player is not found
     * @throws InsufficientFundsException if player has insufficient funds
     * Team member(s) responsible: Matt
     */
    public void sellHouse(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player) {
            throw new InvalidTransactionException();
        }
        if (!property.buyHouse(this)) {
            throw new InvalidTransactionException();
        }
    }

    /**
     * Sell a hotel to a player for a specific property
     *
     * @param property The property to add a hotel to
     * @param player   The player buying the hotel
     * @throws PlayerNotFoundException    if player is not found
     * @throws InsufficientFundsException if player has insufficient funds
     *                                    Team member(s) responsible: Matt
     */
    public void sellHotel(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player) {
            throw new InvalidTransactionException();
        }
        if (property.getNumHouses() != 4) {
            throw new InvalidTransactionException();
        }
        if (!property.canBuyHotel(this)) {
            throw new InvalidTransactionException();
        }
    }

    /**
     * Buy back a house from a player
     * @param property The property to remove a house from
     * @param player   The player selling the house
     * @throws PlayerNotFoundException if player is not found
     *                                 Team member(s) responsible: Matt
     */
    public void buyBackHouse(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player || property.getNumHouses() <= 0) {
            throw new InvalidTransactionException();
        }
        int housePrice = property.getHousePrice();
        deposit(player, housePrice);
        property.removeHouse();
        availableHouses++;
    }

    /**
     * Buy back a hotel from a player
     *
     * @param property The property to remove a hotel from
     * @param player   The player selling the hotel
     * @throws PlayerNotFoundException if player is not found
     * Team member(s) responsible: Matt
     */
    public void buyBackHotel(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player || !property.hasHotel()) {
            throw new InvalidTransactionException();
        }
        if (availableHouses < 4) {
            throw new InvalidTransactionException();
        }
        int hotelPrice = property.getHousePrice() / 2;
        deposit(player, hotelPrice);
        property.removeHotel();
        availableHotels++;
        availableHouses -= 4;
    }

    /**
     * Collect rent from a player and pay it to the property owner
     *
     * @param property The property
     * @param player   The player landing on the property
     * @throws PlayerNotFoundException    if player is not found
     * @throws InsufficientFundsException if player has insufficient funds
     *                                    Team member(s) responsible: Matt
     */
    public void collectRent(BoardSpace property, Player player) throws PlayerNotFoundException {
        Player owner = property.getOwner();
        int rent = property.calculateRent(player);
        transferMoney(player, owner, rent);
    }

    /**
     * Get the number of available houses
     * @return Number of houses
     * Team member(s) responsible: Matt
     */
    public int getAvailableHouses() {
        return availableHouses;
    }

    /**
     * Get the number of available hotels
     * @return Number of hotels
     * Team member(s) responsible: Matt
     */
    public int getAvailableHotels() {
        return availableHotels;
    }

    /**
     * Decrement the number of available hotels
     * Team member(s) responsible: Deborah
     */
    public void decrementAvailableHotels() {
        if (availableHotels > 0) {
            availableHotels--;
        }
    }

    /**
     * Increment the number of available houses
     * @param i
     * Team member(s) responsible: Deborah
     */
    public void incrementAvailableHouses(int i) {
        availableHouses += i;
    }

    /**
     * Decrement the number of available houses
     * @param i
     */
    public void decrementAvailableHouses(int i) {
        if (availableHouses > 0) {
            availableHouses -= i;
        }
    }

    /**
     * Increment the number of available hotels
     * @param i
     * Team member(s) responsible: Deborah
     */
    public void incrementAvailableHotels(int i) {
        availableHotels += i;
    }

    /**
     * Get the list of available properties
     *
     * @return List of available properties
     * Team member(s) responsible: Jamell
     */
    public void initializeProperties() {
        // Initialize color groups
        ColorGroup brownGroup = new ColorGroup(PropertyColor.BROWN, 2);
        ColorGroup lightBlueGroup = new ColorGroup(PropertyColor.LIGHT_BLUE, 3);
        ColorGroup pinkGroup = new ColorGroup(PropertyColor.PINK, 3);
        ColorGroup orangeGroup = new ColorGroup(PropertyColor.ORANGE, 3);
        ColorGroup redGroup = new ColorGroup(PropertyColor.RED, 3);
        ColorGroup yellowGroup = new ColorGroup(PropertyColor.YELLOW, 3);
        ColorGroup greenGroup = new ColorGroup(PropertyColor.GREEN, 3);
        ColorGroup blueGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);

        // Initialize properties
        availableProperties.add(new Property("Mediterranean Avenue", 1, 60, 2, new int[]{10, 30, 90, 160}, 250, 30, PropertyColor.BROWN, brownGroup));
        availableProperties.add(new Property("Baltic Avenue", 3, 60, 4, new int[]{20, 60, 180, 320}, 450, 30, PropertyColor.BROWN, brownGroup));
        availableProperties.add(new Railroad("Reading Railroad", 5));
        availableProperties.add(new Property("Oriental Avenue", 6, 100, 6, new int[]{30, 90, 270, 400}, 550, 50, PropertyColor.LIGHT_BLUE, lightBlueGroup));
        availableProperties.add(new Property("Vermont Avenue", 8, 100, 6, new int[]{30, 90, 270, 400}, 550, 50, PropertyColor.LIGHT_BLUE, lightBlueGroup));
        availableProperties.add(new Property("Connecticut Avenue", 9, 120, 8, new int[]{40, 100, 300, 450}, 600, 60, PropertyColor.LIGHT_BLUE, lightBlueGroup));
        availableProperties.add(new UtilitySpace("Electric Company", 12));
        availableProperties.add(new Property("St. Charles Place", 11, 140, 10, new int[]{50, 150, 450, 625}, 750, 70, PropertyColor.PINK, pinkGroup));
        availableProperties.add(new Property("States Avenue", 13, 140, 10, new int[]{50, 150, 450, 625}, 750, 70, PropertyColor.PINK, pinkGroup));
        availableProperties.add(new Property("Virginia Avenue", 14, 160, 12, new int[]{60, 180, 500, 700}, 900, 80, PropertyColor.PINK, pinkGroup));
        availableProperties.add(new Railroad("Pennsylvania Railroad", 15));
        availableProperties.add(new Property("St. James Place", 16, 180, 14, new int[]{70, 200, 550, 750}, 950, 90, PropertyColor.ORANGE, orangeGroup));
        availableProperties.add(new Property("Tennessee Avenue", 18, 180, 14, new int[]{70, 200, 550, 750}, 950, 90, PropertyColor.ORANGE, orangeGroup));
        availableProperties.add(new Property("New York Avenue", 19, 200, 16, new int[]{80, 220, 600, 800}, 1000, 100, PropertyColor.ORANGE, orangeGroup));
        availableProperties.add(new Property("Kentucky Avenue", 21, 220, 18, new int[]{90, 250, 700, 875}, 1050, 110, PropertyColor.RED, redGroup));
        availableProperties.add(new Property("Indiana Avenue", 23, 220, 18, new int[]{90, 250, 700, 875}, 1050, 110, PropertyColor.RED, redGroup));
        availableProperties.add(new Property("Illinois Avenue", 24, 240, 20, new int[]{100, 300, 750, 925}, 1100, 120, PropertyColor.RED, redGroup));
        availableProperties.add(new Railroad("B. & O. Railroad", 25));
        availableProperties.add(new Property("Atlantic Avenue", 26, 260, 22, new int[]{110, 330, 800, 975}, 1150, 130, PropertyColor.YELLOW, yellowGroup));
        availableProperties.add(new Property("Ventnor Avenue", 27, 260, 22, new int[]{110, 330, 800, 975}, 1150, 130, PropertyColor.YELLOW, yellowGroup));
        availableProperties.add(new UtilitySpace("Water Works", 28));
        availableProperties.add(new Property("Marvin Gardens", 29, 280, 24, new int[]{120, 360, 850, 1025}, 1200, 140, PropertyColor.YELLOW, yellowGroup));
        availableProperties.add(new Railroad("Short Line", 35));
        availableProperties.add(new Property("Pacific Avenue", 31, 300, 26, new int[]{130, 390, 900, 1100}, 1275, 150, PropertyColor.GREEN, greenGroup));
        availableProperties.add(new Property("North Carolina Avenue", 32, 300, 26, new int[]{130, 390, 900, 1100}, 1275, 150, PropertyColor.GREEN, greenGroup));
        availableProperties.add(new Property("Pennsylvania Avenue", 34, 320, 28, new int[]{150, 450, 1000, 1200}, 1400, 160, PropertyColor.GREEN, greenGroup));
        availableProperties.add(new Property("Park Place", 37, 350, 35, new int[]{175, 500, 1100, 1300}, 1500, 175, PropertyColor.DARK_BLUE, blueGroup));
        availableProperties.add(new Property("Boardwalk", 39, 400, 50, new int[]{200, 600, 1400, 1700}, 2000, 200, PropertyColor.DARK_BLUE, blueGroup));
    }

    /**
     * Reset the banker
     * Team member(s) responsible: Jamell
     */
    public static void reset() {
        instance = null;
    }

}