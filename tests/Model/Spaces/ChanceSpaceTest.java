package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Cards.ChanceCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChanceSpaceTest {

    @BeforeAll
    static void setUp() {
       Banker.reset();
    }


    @Test
    public void testGetPurchasePrice() {
        GameBoard gameBoard = new GameBoard();
        ChanceSpace chanceSpace= new ChanceSpace(7,gameBoard.getChanceCard());
        assertEquals(0,chanceSpace.getPurchasePrice());

    }

    @Test
   public void testSetOwner() {
        GameBoard gameBoard = new GameBoard();
        ChanceSpace chanceSpace= new ChanceSpace(7,gameBoard.getChanceCard());
        assertNull(chanceSpace.getOwner());
    }

    @Test
    public void testCalculateRent() {
        GameBoard gameBoard = new GameBoard();
        ChanceSpace chanceSpace= new ChanceSpace(7,gameBoard.getChanceCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        assertEquals(0,chanceSpace.calculateRent(player2));
    }

    @Test
    public void testGetOwner() {
        GameBoard gameBoard = new GameBoard();
        ChanceSpace chanceSpace= new ChanceSpace(7,gameBoard.getChanceCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        assertNull(chanceSpace.getOwner());
    }

    @Test
    public void testOnLanding() {

    }

    @Test
    public void testOnPassing() {
        GameBoard gameBoard = new GameBoard();
        ChanceSpace chanceSpace= new ChanceSpace(7,gameBoard.getChanceCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        //do nothing basically
    }
}