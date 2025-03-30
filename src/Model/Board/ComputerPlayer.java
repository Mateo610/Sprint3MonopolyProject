/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class is responsible for rolling the dice
 * and keeping track of the dice values
 * Team Member(s) responsible: Jamell
 * */
package Model.Board;

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



}
