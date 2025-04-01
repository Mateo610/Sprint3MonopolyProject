package Model.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerTest {
    private TurnManager turnManager;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private GameBoard gameBoard;

    @BeforeEach
    public void setUp() {
        gameBoard = new GameBoard();
        player1 = new HumanPlayer("Player1", gameBoard);
        player2 = new HumanPlayer("Player2", gameBoard);
        player3 = new HumanPlayer("Player3", gameBoard);
        player4 = new HumanPlayer("Player4", gameBoard);
    }

    @Test
    public void testConstructorWithValidPlayerCount() {
        List<Player> players = Arrays.asList(player1, player2);
        turnManager = new TurnManager(players);
        assertNotNull(turnManager);
        assertEquals(2, turnManager.getTurnOrder().size());
    }

    @Test
    public void testConstructorWithMaximumPlayerCount() {
        List<Player> players = Arrays.asList(player1, player2, player3, player4);
        turnManager = new TurnManager(players);
        assertNotNull(turnManager);
        assertEquals(4, turnManager.getTurnOrder().size());
    }

    @Test
    public void testConstructorWithInvalidPlayerCount() {
        List<Player> tooFewPlayers = Arrays.asList(player1);
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(tooFewPlayers));

        List<Player> tooManyPlayers = Arrays.asList(player1, player2, player3, player4,
                new HumanPlayer("Player5", gameBoard));
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(tooManyPlayers));
    }

    @Test
    public void testGetCurrentPlayer() {
        List<Player> players = Arrays.asList(player1, player2);
        turnManager = new TurnManager(players);

        Player currentPlayer = turnManager.getCurrentPlayer();
        assertNotNull(currentPlayer);
        assertTrue(players.contains(currentPlayer));
    }

    @Test
    public void testNextTurn() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        turnManager = new TurnManager(players);

        Player firstPlayer = turnManager.getCurrentPlayer();
        turnManager.nextTurn();
        Player secondPlayer = turnManager.getCurrentPlayer();

        assertNotEquals(firstPlayer, secondPlayer);

        turnManager.nextTurn();
        Player thirdPlayer = turnManager.getCurrentPlayer();

        assertNotEquals(secondPlayer, thirdPlayer);
        assertNotEquals(firstPlayer, thirdPlayer);

        turnManager.nextTurn();
        Player backToFirstPlayer = turnManager.getCurrentPlayer();

        assertEquals(firstPlayer, backToFirstPlayer);
    }

    @Test
    public void testGetTurnOrder() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        turnManager = new TurnManager(players);

        List<Player> turnOrder = turnManager.getTurnOrder();

        assertEquals(3, turnOrder.size());
        assertTrue(turnOrder.contains(player1));
        assertTrue(turnOrder.contains(player2));
        assertTrue(turnOrder.contains(player3));

        // Test that the returned list is a copy (can't modify the internal state)
        turnOrder.clear();
        assertEquals(3, turnManager.getTurnOrder().size());
    }
}