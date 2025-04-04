package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoSpaceTest {

    @BeforeAll
    public static void setUp() {
        Banker.reset();
    }


    @Test
    public void getPurchasePrice() {
        GoSpace goSpace = new GoSpace();
        int purchasePrice = goSpace.getPurchasePrice();
        assertEquals(0, purchasePrice);
    }

    @Test
    public void setOwner() {
        GoSpace goSpace = new GoSpace();
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        goSpace.setOwner(player);
        assertEquals(null, goSpace.getOwner()); //needs no owner
    }

    @Test
    public void calculateRent() {
        GoSpace goSpace = new GoSpace();
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        int rent = goSpace.calculateRent(player);
        assertEquals(0, rent);
    }

    @Test
    public void getOwner() {
        GoSpace goSpace = new GoSpace();
        Player owner = goSpace.getOwner();
        assertEquals(null, owner);
    }

    @Test
    public void onLanding() throws PlayerNotFoundException {
        GoSpace goSpace = new GoSpace();
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player);
        goSpace.onLanding(player);
        assertEquals(1700, banker.getBalance(player));
    }

    @Test
    public void onPassing() throws PlayerNotFoundException {
        GoSpace goSpace = new GoSpace();
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player);
        goSpace.onPassing(player);
        assertEquals(1700, banker.getBalance(player));
    }
}