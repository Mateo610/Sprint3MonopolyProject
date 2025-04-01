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

    @BeforeEach
    public void setUp() throws PlayerNotFoundException {
        Banker.reset();
        Banker banker = Banker.getInstance();
        GameBoard board = new GameBoard();
        owner = new HumanPlayer("TestOwner", board);
        otherPlayer = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(owner);
        banker.addPlayer(otherPlayer);
        colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        property = new Property(
                "Boardwalk",
                39,
                400,
                50,
                new int[]{200, 600, 1400, 1700},
                2000,
                200,
                PropertyColor.DARK_BLUE,
                colorGroup);
        colorGroup.addProperty(property);
    }

    @Test
    public void testConstructor() {
        assertEquals("Boardwalk", property.getName());
        assertEquals(39, property.getPosition());
        assertEquals(400, property.getPurchasePrice());
        assertNull(property.getOwner());
        assertFalse(property.isMortgaged());
        assertEquals(0, property.getNumHouses());
        assertFalse(property.hasHotel());
    }

    @Test
    public void testRentCalculationNoHouses() throws PlayerNotFoundException {
        property.setOwner(owner);
        assertEquals(50, property.calculateRent(owner));
    }
}