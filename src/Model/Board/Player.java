/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents a player's on the Monopoly board
 * Team Member(s) responsible: Matt, Jamell
 * */

package Model.Board;

import Model.Exceptions.PlayerNotFoundException;
import Model.Property.Property;
import Model.Spaces.BoardSpace;


public abstract class Player {

    private String name;
    private GameBoard board;
    private boolean inJail;
    private int jailTurns;
    private Token token;
    private int getOutOfJailFreeCards = 0;
    private int position;

    /**
     * Constructor for Player.
     *
     * @param name  Player's name
     * @param board The game board
     * Team member(s) responsible: Matt
     */
    public Player(String name, GameBoard board) {
        this.name = name;
        this.board = board;
        this.inJail = false;
        this.jailTurns = 0;
        this.position = 0;
    }

    /**
     * Buy a property from the bank.
     *
     * @param space  The space to buy
     * @param player The player buying the property
     * Team member(s) responsible: Jamell
     */
    public abstract void sellProperty(BoardSpace space, Player player) throws PlayerNotFoundException;

    /**
     * Buy a house on the specified space.
     * @param property  The space to sell
     * @param player The player selling the house
     * Team member(s) responsible: Jamell
     */
    public abstract void sellHouse(Property property, Player player) throws PlayerNotFoundException;


    /**
     * Buy a hotel on the specified space.
     * @param property   The space to sell
     * @param player The player selling the hotel
     * Team member(s) responsible: Jamell
     */
    public abstract void sellHotel(Property property, Player player) throws PlayerNotFoundException;

    /***
     * Move the player on the board.
     * This method is abstract and should be implemented by subclasses.
     * Team member(s) responsible: Jamell
     */
    public abstract void move(Player player, int spaces) throws PlayerNotFoundException;

    /**
     * Get the player's position on the board.
     *
     * @return Player's position
     * Team member(s) responsible: Jamell
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the player's position on the board.
     *
     * @param position New position
     * Team member(s) responsible: Jamell
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * set player's token.
     * Team member(s) responsible: Jamell
     */
    public void setTokenToPlayer(Token token) {
        this.token = token;
        token.setOwner(this);
    }

    /**
     * get player's token.
     *
     * @return Player's token
     * Team member(s) responsible: Jamell
     */

    public Token getToken() {
        return token;
    }

    /**
     * Get player's name.
     *
     * @return Player name
     * Team member(s) responsible: Matt
     */
    public String getName() {
        return name;
    }


    /**
     * Get the game board.
     *
     * @return The game board
     * Team member(s) responsible: Matt
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Check if player is in jail.
     *
     * @return true if player is in jail
     * Team member(s) responsible: Matt
     */
    public boolean isInJail() {
        return inJail;
    }

    /**
     * Set player's jail status.
     *
     * @param inJail New jail status
     *               Team member(s) responsible: Matt
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * Get the number of turns a player has been in jail.
     *
     * @return Number of turns in jail
     * Team member(s) responsible: Matt
     */
    public int getTurnsInJail() {
        return jailTurns;
    }

    /**
     * Increment the number of turns the player has spent in jail.
     * Team member(s) responsible: Matt
     */
    public void incrementTurnsInJail() {
        jailTurns++;
    }

    /**
     * Reset the number of turns in jail to 0.
     * Team member(s) responsible: Matt
     */
    public void addGetOutOfJailFreeCard() {
        getOutOfJailFreeCards++;
    }

    /**
     * Reset the number of turns in jail to 0.
     * Team member(s) responsible: Deborah
     */
    public void resetTurnsInJail() {
        jailTurns = 0;
    }

    /**
     * Returns true if the player has a get out of jail free card.
     *
     * @return Number of get out of jail free cards
     * Team member(s) responsible: Deborah
     */
    public boolean hasGetOutOfJailFreeCard() {
        return getOutOfJailFreeCards > 0;
    }

    /**
     * Returns true if used get out of jail free card.
     *
     * @return Number of get out of jail free cards
     * Team member(s) responsible: Deborah
     */
    public boolean useGetOutOfJailFreeCard() {
        if (getOutOfJailFreeCards > 0) {
            getOutOfJailFreeCards--;
            return true;
        }
        return false;
    }

    /**
     * Get the number of get out of jail free cards.
     *
     * @return Number of get out of jail free cards
     * Team member(s) responsible: Deborah
     */
    public int getGetOutOfJailFreeCard() {
        return getOutOfJailFreeCards;
    }
}
