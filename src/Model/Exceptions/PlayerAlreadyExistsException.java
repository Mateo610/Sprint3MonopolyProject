/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a player already exists in the game.
 * Team Member(s) responsible: Deborah
 */

package Model.Exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {
    /**
     * Constructor for PlayerAlreadyExistsException
     * Team member(s) responsible: Deborah
     */
    public PlayerAlreadyExistsException() {
        super("Player already exists");
    }
}
