/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class manages the turn order and progression in a Monopoly game.
 * Team Member(s) responsible: Giovanny
 * */
package Model.Board;

import Model.Exceptions.PlayerNotFoundException;
import Model.Spaces.BoardSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the turn order and progression in a Monopoly game.
 */
public class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex;
    private Dice dice;
    private Banker banker;
    private GameBoard gameBoard;

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
        this.dice = Dice.getInstance();
        this.banker = Banker.getInstance();
        this.gameBoard = GameBoard.getInstance();

        // Reset dice counter at initialization
        dice.resetDoubleRollCounter();
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
        // Reset dice counter when switching players
        dice.resetDoubleRollCounter();
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

    /**
     * Processes a player's turn.
     * Rolls dice, moves player, and handles any resulting actions.
     *
     * @param player The player taking the turn
     * @return String describing the turn outcome
     * Team Member(s) responsible: Added for Sprint 3
     */
    public String processTurn(Player player) {
        StringBuilder result = new StringBuilder();

        // Roll dice
        int roll = dice.roll();
        result.append(player.getName()).append(" rolled: ")
                .append(dice.getDie1()).append(" + ").append(dice.getDie2())
                .append(" = ").append(roll).append("\n");

        // Check for doubles
        if (dice.isDouble()) {
            result.append(player.getName()).append(" rolled doubles!\n");
            if (dice.goToJail()) {
                result.append(player.getName()).append(" rolled 3 doubles in a row and goes to jail!\n");
                player.setInJail(true);
                player.setPosition(10); // Jail position
            }
        }

        // Move player if not in jail
        if (!player.isInJail()) {
            try {
                int oldPosition = player.getPosition();
                int oldBalance = banker.getBalance(player);

                player.move(player, roll);
                result.append(player.getName()).append(" moved from position ")
                        .append(oldPosition).append(" to ").append(player.getPosition()).append("\n");

                // Get balance after movement
                int newBalance = banker.getBalance(player);
                result.append(player.getName()).append("'s balance: $").append(newBalance).append("\n");

                // Add explanation for balance changes
                if (newBalance != oldBalance) {
                    String explanation = getBalanceChangeExplanation(player, oldBalance, newBalance);
                    result.append(explanation).append("\n");
                }

            } catch (PlayerNotFoundException e) {
                result.append("Error: ").append(e.getMessage()).append("\n");
            }
        } else {
            result.append(player.getName()).append(" is in jail and cannot move.\n");
        }

        return result.toString();
    }

    /**
     * Gets an explanation for a change in player balance.
     *
     * @param player The player whose balance changed
     * @param oldBalance The balance before the change
     * @param newBalance The balance after the change
     * @return A string explaining the balance change
     */
    private String getBalanceChangeExplanation(Player player, int oldBalance, int newBalance) {
        // Check what space the player landed on
        BoardSpace landedSpace = gameBoard.getBoardElements()[player.getPosition()];

        if (newBalance < oldBalance) { // Player lost money
            int amountLost = oldBalance - newBalance;

            if (landedSpace.getOwner() == player) {
                return player.getName() + " purchased " + landedSpace.getName() + " for $" + amountLost;
            } else if (landedSpace.getOwner() != null) {
                return player.getName() + " paid $" + amountLost + " rent to " + landedSpace.getOwner().getName();
            } else if (landedSpace.getName().contains("Tax")) {
                return player.getName() + " paid $" + amountLost + " in taxes";
            } else {
                return player.getName() + " paid $" + amountLost;
            }
        } else if (newBalance > oldBalance) { // Player gained money
            int amountGained = newBalance - oldBalance;

            if (landedSpace.getName().equals("Go")) {
                return player.getName() + " collected $" + amountGained + " for landing on GO";
            } else {
                return player.getName() + " collected $" + amountGained + " for passing GO";
            }
        }

        return ""; // No balance change
    }
}