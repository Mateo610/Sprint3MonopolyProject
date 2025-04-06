package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.Property;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RailroadTest {

    @BeforeAll
    public static void setUp() {
        Banker.reset();
        GameBoard.resetInstance();
    }

    @Test
    public void getOwner() {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        assertNull(railroad.getOwner());
    }

    @Test
    public void setOwner() {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        assertNull(railroad.getOwner());
        GameBoard gameBoard = GameBoard.getInstance();
        Player owner = new HumanPlayer("Owner", gameBoard);
        railroad.setOwner(owner);
        assertEquals(owner, railroad.getOwner());
    }

    @Test
    public void getPurchasePrice() {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        assertEquals(railroad.getPurchasePrice(), 200);
    }

    @Test
    public void onLanding() throws PlayerNotFoundException {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        GameBoard gameBoard = GameBoard.getInstance();
        Banker banker = Banker.getInstance();
        Player player = new HumanPlayer("Player", gameBoard);
        banker.addPlayer(player);
        banker.addAvailableProperty(railroad);
        railroad.onLanding(player);
        assertEquals(railroad.getOwner(), player);
        assertEquals(1300,banker.getBalance(player));
    }

    @Test
    public void onPassing() {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        GameBoard gameBoard = GameBoard.getInstance();
        Banker banker = Banker.getInstance();
        Player player = new HumanPlayer("Player", gameBoard);
        banker.addPlayer(player);
        railroad.onPassing(player);
       // do nothing
    }

    @Test
    public void calculateRent() throws PlayerNotFoundException {
        Railroad railroad = new Railroad("Reading Railroad", 5);
        GameBoard gameBoard = GameBoard.getInstance();
        Banker banker = Banker.getInstance();
        Player player = new HumanPlayer("Player", gameBoard);
        Player player2 = new HumanPlayer("Player2", gameBoard);
        banker.addPlayer(player);
        banker.addPlayer(player2);
        banker.addAvailableProperty(railroad);
        railroad.onLanding(player);
        railroad.onLanding(player2);
    }
}