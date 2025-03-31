/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a player is not found in the game.
 * Team Member(s) responsible: Jamell
 */
package Model.Exceptions;

public class PlayerNotFoundException extends Exception {

    /**
     * Constructor for PlayerNotFoundException
     * Team member(s) responsible: Jamell
     */
    public PlayerNotFoundException() {
        super("Player not found");
    }

}
