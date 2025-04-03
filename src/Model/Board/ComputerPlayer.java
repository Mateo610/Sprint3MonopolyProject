/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class is responsible for rolling the dice
 * and keeping track of the dice values
 * Team Member(s) responsible: Jamell
 * */
package Model.Board;

import Model.Exceptions.PlayerNotFoundException;

/**
 * Represents a computer-controlled player in a Monopoly game.
 * Inherits from the abstract Player class.
 * Team member(s) responsible: Matt
 */
public class ComputerPlayer extends Player {

    /**
     * Constructor for ComputerPlayer.
     *
     * @param name  Player's name
     * @param board The game board
     * Team member(s) responsible: Giovanny
     */
    public ComputerPlayer(String name, GameBoard board) {
        super("Cpu", board);
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

    //monopoly buy logic
    //buy property if not owned
    //if owned, check if it is mortgaged
    //if not mortgaged, check if the player can afford to pay rent
    //jail logic

}
