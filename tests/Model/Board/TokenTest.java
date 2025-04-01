package Model.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {
    private Token token;
    private Player player;
    private GameBoard gameBoard;

    @BeforeEach
    public void setUp() {
        Banker banker = new Banker();
        gameBoard = new GameBoard(banker);
        player = new HumanPlayer("TestPlayer", gameBoard);
        token = new Token("Car");
    }

    @Test
    public void testConstructor() {
        assertEquals("Car", token.getType());
        assertEquals(0, token.getPosition());
        assertNull(token.getOwner());
    }

    @Test
    public void testSetOwner() {
        token.setOwner(player);
        assertEquals(player, token.getOwner());
    }

    @Test
    public void testSetPosition() {
        token.setPosition(10);
        assertEquals(10, token.getPosition());

        token.setPosition(39);
        assertEquals(39, token.getPosition());
    }

    @Test
    public void testGetType() {
        assertEquals("Car", token.getType());

        Token anotherToken = new Token("Dog");
        assertEquals("Dog", anotherToken.getType());
    }

    @Test
    public void testTokenPlayerAssociation() {
        // Test two-way association between token and player
        player.setTokenToPlayer(token);

        assertEquals(player, token.getOwner());
        assertEquals(token, player.getToken());
    }

    @Test
    public void testTokenPositionUpdates() {
        player.setTokenToPlayer(token);
        assertEquals(0, token.getPosition());

        // Normally the token position would be updated through Player.move(),
        // but we can test direct updates here
        token.setPosition(5);
        assertEquals(5, token.getPosition());

        token.setPosition(20);
        assertEquals(20, token.getPosition());
    }
}