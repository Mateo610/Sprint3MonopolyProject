package Model.Board;

import Model.Spaces.BoardSpace;

/**
 * Represents a human player in a Monopoly game.
 * Inherits from the abstract Player class.
 * Team member(s) responsible: Matt
 */
public class HumanPlayer extends Player {

    /**
     * Constructor for HumanPlayer.
     *
     * @param name  Player's name
     * @param board The game board
     * Team member(s) responsible: Giovanny
     */
    public HumanPlayer(String name, GameBoard board) {
        super(name, board);
    }



}
