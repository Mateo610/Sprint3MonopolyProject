/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when a transaction is invalid.
 * Team Member(s) responsible: Jamell
 */

package Model.Exceptions;

public class InvalidTransactionException extends RuntimeException {

    /**
     * Constructor for InvalidTransactionException
     * Team member(s) responsible: Jamell
     */
    public InvalidTransactionException() {
        super("Invalid transaction");
    }
}
