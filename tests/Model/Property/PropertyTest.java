package Model.Property;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Exceptions.PlayerNotFoundException;
import Model.Board.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {

    private Property property;
    private ColorGroup colorGroup;
    private Player owner;
    private Player otherPlayer;
    private Banker banker;
    private Property boardwalk;
    private Property parkPlace;

    @BeforeEach
    public void setUp() throws PlayerNotFoundException {
        GameBoard.resetInstance();
        Banker.reset();
        banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        owner = new HumanPlayer("TestOwner", board);
        otherPlayer = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(owner);
        banker.addPlayer(otherPlayer);
        colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        boardwalk = new Property(
                "Boardwalk",
                39,
                400,
                50,
                new int[]{200, 600, 1400, 1700},
                2000,
                200,
                PropertyColor.DARK_BLUE,
                colorGroup);
        parkPlace = new Property(
                "Park Place",
                37,
                350,
                35,
                new int[]{175, 500, 1100, 1300},
                1500,
                175,
                PropertyColor.DARK_BLUE,
                colorGroup
        );
    }

    @Test
    public void testConstructor() {
        assertEquals("Boardwalk", boardwalk.getName());
        assertEquals(39, boardwalk.getPosition());
        assertEquals(400, boardwalk.getPurchasePrice());
        assertNull(boardwalk.getOwner());
        assertFalse(boardwalk.isMortgaged());
        assertEquals(0, boardwalk.getNumHouses());
        assertFalse(boardwalk.hasHotel());
    }

    @Test
    public void testPropertyOwnership() {
        assertNull(boardwalk.getOwner());
        boardwalk.setOwner(owner);
        assertEquals(owner, boardwalk.getOwner());
    }

    @Test
    public void testRentCalculationNoHouses() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        assertEquals(50, boardwalk.calculateRent(otherPlayer));
    }

    @Test
    public void testRentCalculationWithMonopoly() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        parkPlace.setOwner(owner);
        assertTrue(colorGroup.hasMonopoly(owner));
        assertEquals(100, boardwalk.calculateRent(otherPlayer));
    }

    @Test
    public void testCanBuyHouse() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        assertFalse(boardwalk.canBuyHouse(banker));
        parkPlace.setOwner(owner);
        assertTrue(boardwalk.canBuyHouse(banker));
    }
    @Test
    public void testBuyHouse() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        assertFalse(boardwalk.canBuyHouse(banker));
        parkPlace.setOwner(owner);
        assertTrue(boardwalk.canBuyHouse(banker));
        int initialBalance = banker.getBalance(owner);
        assertTrue(boardwalk.buyHouse(banker));
        assertEquals(1, boardwalk.getNumHouses());
        assertEquals(initialBalance - boardwalk.getHousePrice(), banker.getBalance(owner));
    }

    @Test
    public void testCannotBuyHouseWithoutOwner() {
        assertFalse(boardwalk.canBuyHouse(banker));
    }

    @Test
    public void testCannotBuyHouseWhenMortgaged() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        parkPlace.setOwner(owner);
        boardwalk.setMortgaged(true);
        assertFalse(boardwalk.canBuyHouse(banker));
    }



}