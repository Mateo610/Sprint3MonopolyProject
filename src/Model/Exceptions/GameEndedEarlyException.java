/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when the game ends before it should.
 * Team Member(s) responsible: Deborah
 */

package Model.Exceptions;

public class GameEndedEarlyException extends RuntimeException {
    /**
     * Constructor for GameEndedEarlyException
     * Team member(s) responsible: Deborah
     */
    public GameEndedEarlyException() {
        super("Game ended early!");
    }
}
