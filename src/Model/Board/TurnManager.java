/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class manages the turn order and progression in a Monopoly game.
 * Team Member(s) responsible: Giovanny
 * */
package Model.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the turn order and progression in a Monopoly game.
 */
public class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex;

    /**
     * Initializes the turn manager with a list of players.
     * The turn order is randomized.
     *
     * @param players List of players in the game.
     *                Team Member(s) responsible: Giovanny
     */
    public TurnManager(List<Player> players) {
        if (players.size() < 2 || players.size() > 4) {
            throw new IllegalArgumentException("Monopoly requires 2 to 4 players.");
        }
        this.players = new ArrayList<>(players);
        Collections.shuffle(this.players); // Randomize turn order
        this.currentPlayerIndex = 0;
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return The current player.
     * Team Member(s) responsible: Giovanny
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Moves to the next player's turn.
     * Team Member(s) responsible: Giovanny
     */
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Gets the list of players in their randomized turn order.
     *
     * @return List of players.
     * Team Member(s) responsible: Giovanny
     */
    public List<Player> getTurnOrder() {
        return new ArrayList<>(players);
    }
}
