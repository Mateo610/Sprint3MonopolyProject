/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a game is not in progress
 * Team Member(s) responsible: Deborah
 */

package Model.Exceptions;

public class GameNotInProgressException extends RuntimeException {
    /**
     * Constructor for GameNotInProgressException
     * Team member(s) responsible: Deborah
     */
    public GameNotInProgressException() {
        super("Game is not in progress");
    }
}
