package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitySpaceTest {

    @BeforeEach
    public void setUp() {
       Banker.reset();
    }

    @Test
    public void getPurchasePrice() {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        assertEquals(150, utilitySpace.getPurchasePrice());
    }

    @Test
    public void setOwner() {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        utilitySpace.setOwner(player);
        assertEquals(player, utilitySpace.getOwner());
    }

    @Test
    public void getOwner() {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        utilitySpace.setOwner(player);
        assertEquals(player, utilitySpace.getOwner());
    }

    @Test
    public void onLanding() throws PlayerNotFoundException {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player);
        banker.addAvailableProperty(utilitySpace);
        utilitySpace.onLanding(player);
        assertEquals(player, utilitySpace.getOwner());
        assertEquals(1350, banker.getBalance(player));
    }

    @Test
    public void calculateRent() throws PlayerNotFoundException {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        GameBoard gameBoard = new GameBoard();
        Player player1 = new HumanPlayer("Player1", gameBoard);
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player1);
        banker.addPlayer(player2);
        banker.addAvailableProperty(utilitySpace);
        utilitySpace.onLanding(player1);
        assertEquals(1350, banker.getBalance(player1));
        utilitySpace.onLanding(player2);
    }

    @Test
    public void onPassing() {
        UtilitySpace utilitySpace = new UtilitySpace("Electric Company", 12);
        GameBoard gameBoard = new GameBoard();
        Player player = new HumanPlayer("Player1", gameBoard);
        utilitySpace.onPassing(player);
        // do nothing
    }
}