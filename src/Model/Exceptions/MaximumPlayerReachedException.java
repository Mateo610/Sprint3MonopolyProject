/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a maximum number of players is reached in a game.
 * Team Member(s) responsible: Deborah
 */

package Model.Exceptions;

public class MaximumPlayerReachedException extends RuntimeException {
    /**
     * Constructor for MaximumPlayerReachedException
     * Team member(s) responsible: Deborah
     */
    public MaximumPlayerReachedException() {
        super("Maximum number of players reached");
    }
}
