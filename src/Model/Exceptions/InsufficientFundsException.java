/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This exception is thrown when Insufficient funds are available for a transaction.
 * Team Member(s) responsible: Jamell
 */

package Model.Exceptions;

public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructor for InsufficientFundsException
     * Team member(s) responsible: Jamell
     */
    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
