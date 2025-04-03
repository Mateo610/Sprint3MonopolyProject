package Model.Board;

import Model.Exceptions.PlayerNotFoundException;
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
