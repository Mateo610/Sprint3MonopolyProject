package Model.BoardTests;


import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Exceptions.PlayerNotFoundException;
import Model.Spaces.BoardSpace;
import Model.Board.Player;
import Model.Board.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class BoardSpaceTest {
    private BoardSpace testSpace;
    private Token testToken;
    private GameBoard board;


    private class TestBoardSpace extends BoardSpace {

        public TestBoardSpace(String name, int position) {
            super(name, position);
        }

        @Override
        public int getPurchasePrice() {
            return 0;
        }

        @Override
        public void setOwner(Player owner) {

        }

        @Override
        public int calculateRent(Player player) throws PlayerNotFoundException {
            return 0;
        }

        @Override
        public Player getOwner() {
            return null;
        }

        @Override
        public void onLanding(Player player) {

        }

        @Override
        public void onPassing(Player player) {

        }
    }
    @BeforeEach
    public void setUp() {
        Banker.reset();
        Banker.getInstance();
        board = new GameBoard();
        testSpace = new TestBoardSpace("Test Space", 5);
        testToken = new Token("Car");
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Space", testSpace.getName());
        assertEquals(5, testSpace.getPosition());
        assertTrue(testSpace.getTokens().isEmpty());
    }

    @Test
    public void testAddToken() {
        testSpace.addToken(testToken);
        assertEquals(1, testSpace.getTokens().size());
        assertTrue(testSpace.getTokens().contains(testToken));
    }

    @Test
    public void testRemoveToken() {
        testSpace.addToken(testToken);
        testSpace.removeToken(testToken);
        assertTrue(testSpace.getTokens().isEmpty());
    }

    @Test
    public void testGetTokensReturnsNewList() {
        testSpace.addToken(testToken);
        List<Token> tokens = testSpace.getTokens();
        tokens.clear();
        assertEquals(1, testSpace.getTokens().size());
    }

    @Test
    public void testMultipleTokens() {
        Token token2 = new Token("Dog");
        testSpace.addToken(testToken);
        testSpace.addToken(token2);
        assertEquals(2, testSpace.getTokens().size());
    }
}