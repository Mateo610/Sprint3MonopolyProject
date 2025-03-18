package Model.Board;

import java.util.Random;

public class Dice {

    private Random random;
    private int die1;
    private int die2;
    private int doubleRollCounter;

    public Dice() {
        this.random = new Random();
        doubleRollCounter = 0;
    }

    /**
     * Rolls the dice
     * Team member(s) responsible: Jamell
     */
    public void roll() {
        die1 = rollDie();
        die2 = rollDie();
    }

    /**
     * Rolls a single die from 1 to 6
     *
     * @return the value of the die
     * Team member(s) responsible: Jamell
     */
    public int rollDie() {
        return random.nextInt(6) + 1;
    }

    /**
     * Checks if the player has rolled doubles 3 times in a row
     * Team member(s) responsible: Jamell
     */
    public boolean goToJail() {
        if (doubleRollCounter == 3) {
            resetDoubleRollCounter();
            return true;
        }
        return false;
    }

    /**
     * Returns the sum of the dice
     *
     * @return the sum of the dice
     * Team member(s) responsible: Jamell
     */
    public int getSum() {
        return die1 + die2;
    }

    /**
     * Checks if the player has rolled doubles
     *
     * @return true if the player has rolled doubles
     * Team member(s) responsible: Jamell
     */
    public boolean isDouble() {
        incrementDoubleRollCounter();
        return die1 == die2;
    }

    /**
     * Increments the double roll counter
     * Team member(s) responsible: Jamell
     */
    public void incrementDoubleRollCounter() {
        doubleRollCounter++;
    }

    public void resetDoubleRollCounter() {
        doubleRollCounter = 0;
    }

    /**
     * returns the value of the dice
     * Team member(s) responsible: Jamell
     */
    public int getDie1() {
        return die1;
    }

    /**
     * returns the value of the dice
     * Team member(s) responsible: Jamell
     */
    public int getDie2() {
        return die2;
    }
}
