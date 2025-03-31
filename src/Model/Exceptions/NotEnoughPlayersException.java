/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a game is attempted to be started with less than the required number of players.
 * Team Member(s) responsible: Deborah
 */
package Model.Exceptions;

public class NotEnoughPlayersException extends RuntimeException {
    /**
     * Constructor for NotEnoughPlayersException
     * Team member(s) responsible: Deborah
     */
    public NotEnoughPlayersException() {
        super("Not enough players to start the game");
    }
}
