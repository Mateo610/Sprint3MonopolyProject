/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a game is already in progress
 * Team Member(s) responsible: Deborah
 */

package Model.Exceptions;

public class GameInProgressException extends RuntimeException {
    /**
     * Constructor for GameInProgressException
     * Team member(s) responsible: Deborah
     */
    public GameInProgressException() {
        super("Game is already in progress");
    }
}
