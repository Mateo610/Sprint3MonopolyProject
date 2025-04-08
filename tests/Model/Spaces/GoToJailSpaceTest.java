package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailSpaceTest {

    private GoToJailSpace goToJailSpace;
    private Player player;
    private GameBoard gameBoard;
    private Banker banker;

    @BeforeEach
    void setUp() {
        // Reset singletons
        Banker.reset();
        GameBoard.resetInstance();

        // Create test objects
        gameBoard = GameBoard.getInstance();
        banker = Banker.getInstance();
        goToJailSpace = new GoToJailSpace(30);
        player = new HumanPlayer("TestPlayer", gameBoard);
        banker.addPlayer(player);
    }

    @Test
    void getPurchasePrice() {
        // Go To Jail space should not be purchasable
        assertEquals(0, goToJailSpace.getPurchasePrice());
    }

    @Test
    void setOwner() {
        // Set owner should have no effect
        goToJailSpace.setOwner(player);
        assertNull(goToJailSpace.getOwner());
    }

    @Test
    void calculateRent() {
        // Go To Jail space should not have rent
        assertEquals(0, goToJailSpace.calculateRent(player));
    }

    @Test
    void getOwner() {
        // Go To Jail space should not have an owner
        assertNull(goToJailSpace.getOwner());
    }

    @Test
    void onLanding() throws PlayerNotFoundException {
        // Set initial position
        player.setPosition(30);

        // Player lands on Go To Jail
        goToJailSpace.onLanding(player);

        // Should move player to jail (position 10)
        assertEquals(10, player.getPosition());

        // Should set player's jail status to true
        assertTrue(player.isInJail());
    }

    @Test
    void onPassing() {
        // Nothing should happen when passing Go To Jail
        int initialPosition = player.getPosition();
        boolean initialJailStatus = player.isInJail();

        goToJailSpace.onPassing(player);

        // Position and jail status should remain unchanged
        assertEquals(initialPosition, player.getPosition());
        assertEquals(initialJailStatus, player.isInJail());
    }
}