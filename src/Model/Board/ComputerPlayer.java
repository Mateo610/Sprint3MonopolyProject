/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class is responsible for rolling the dice
 * and keeping track of the dice values
 * Team Member(s) responsible: Jamell
 * */
package Model.Board;

import Model.Exceptions.PlayerNotFoundException;
import Model.Property.Property;
import Model.Spaces.BoardSpace;


import java.util.Random;

/**
 * Represents a computer-controlled player in a Monopoly game.
 * Inherits from the abstract Player class.
 * Team member(s) responsible: Matt
 */
public class ComputerPlayer extends Player {
    private Banker banker;
    private Random rand;

    /**
     * Constructor for ComputerPlayer.
     *
     * @param name  Player's name
     * @param board The game board
     * Team member(s) responsible: Giovanny
     */
    public ComputerPlayer(String name, GameBoard board) {
        super("Cpu", board);
        banker = Banker.getInstance();
        rand = new Random();

    }

    /**
     * Buy a property from the bank.
     * @return Player's position
     * Team member(s) responsible: Jamell
     */
    @Override
    public void sellProperty(BoardSpace space, Player player) throws PlayerNotFoundException {
        int chance = rand.nextInt(3) + 1;
        if (chance == 1) {
            banker.sellProperty(space, player);
        } else
        {
            System.out.println("Banker didn't sell the property this time.");
        }
    }

    /**
     * Sell a house on the specified space.
     * Team member(s) responsible: Jamell
     */
    @Override
    public void sellHouse(Property property, Player player) throws PlayerNotFoundException {
        int chance = rand.nextInt(3) + 1;
        if (chance == 1) {
            banker.sellHouse(property, player);
        } else
        {
            System.out.println("Banker didn't sell the house this time.");
        }
    }


    /**
     * Sell a hotel on the specified space.
     * Team member(s) responsible: Jamell
     */
    @Override
    public void sellHotel(Property property, Player player) throws PlayerNotFoundException {
        int chance = rand.nextInt(3) + 1;
        if (chance == 1) {
            banker.sellHotel(property, player);
        } else
        {
            System.out.println("Banker didn't sell the hotel this time.");
        }
    }


    /***
     * Move the player on the board.
     * This method is abstract and should be implemented by subclasses.
     * Team member(s) responsible: Jamell
     */
    @Override
    public void move(Player player, int spaces) throws PlayerNotFoundException {
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        int newPosition = (getPosition() + spaces) % 40;
        setPosition(newPosition);
        System.out.println(getName() + " moved " + newPosition + " spaces");
        getBoard().getBoardElements()[newPosition].onLanding(player);
    }


}
