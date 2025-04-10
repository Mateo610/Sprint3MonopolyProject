package Model.Property;

import Model.Board.Banker;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColorGroupTest {
    private ColorGroup colorGroup;
    private Property boardwalk;
    private Property parkPlace;
    private Player owner;
    private Banker banker;


    @BeforeEach
    public void setUp() {
        Banker.reset();
        banker = Banker.getInstance();
        colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        owner = new HumanPlayer("TestOwner", null);

        boardwalk = new Property(
                "Boardwalk",
                39,
                400,
                50,
                new int[]{200, 600, 1400, 1700},
                2000,
                200,
                PropertyColor.DARK_BLUE,
                colorGroup
        );

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
        banker.addPlayer(owner);

    }

    @Test
    public void testConstructor() {
        ColorGroup newGroup = new ColorGroup(PropertyColor.RED, 3);
        assertEquals(PropertyColor.RED, newGroup.getColor());
        assertEquals(3, newGroup.getPropertiesInGroup());
        assertTrue(newGroup.getProperties().isEmpty());
    }

    @Test
    public void testAddMultipleProperties() {
        colorGroup.addProperty(boardwalk);
        colorGroup.addProperty(parkPlace);
        assertEquals(2, colorGroup.getProperties().size());
        assertTrue(colorGroup.getProperties().contains(boardwalk));
        assertTrue(colorGroup.getProperties().contains(parkPlace));
    }

    @Test
    public void testCannotAddMoreThanMaxProperties() {
        colorGroup.addProperty(boardwalk);
        colorGroup.addProperty(parkPlace);
        Property extraProperty = new Property(
                "Extra",
                40,
                400,
                50,
                new int[]{200, 600, 1400, 1700},
                2000,
                200,
                PropertyColor.DARK_BLUE,
                colorGroup
        );
        colorGroup.addProperty(extraProperty);
        assertEquals(2, colorGroup.getProperties().size());
    }

    @Test
    public void testCanBuyHouse() throws PlayerNotFoundException {
        boardwalk.setOwner(owner);
        parkPlace.setOwner(owner);

        assertTrue(colorGroup.canBuyHouse(boardwalk));
        assertTrue(colorGroup.canBuyHouse(parkPlace));

        boardwalk.buyHouse(banker);
        assertTrue(colorGroup.canBuyHouse(parkPlace));
        assertFalse(colorGroup.canBuyHouse(boardwalk));
        assertEquals(1, boardwalk.getNumHouses());

        parkPlace.buyHouse(banker);
        assertTrue(colorGroup.canBuyHouse(boardwalk));
        assertTrue(colorGroup.canBuyHouse(parkPlace));
        assertEquals(1, parkPlace.getNumHouses());

        boardwalk.buyHouse(banker);
        parkPlace.buyHouse(banker);
        assertEquals(2, boardwalk.getNumHouses());
        assertEquals(2, parkPlace.getNumHouses());

        boardwalk.buyHouse(banker);
        parkPlace.buyHouse(banker);
        assertEquals(3, boardwalk.getNumHouses());
        assertEquals(3, parkPlace.getNumHouses());

        banker.deposit(owner, 1000);

        boardwalk.buyHouse(banker);
        parkPlace.buyHouse(banker);
        assertEquals(4, boardwalk.getNumHouses());
        assertEquals(4, parkPlace.getNumHouses());

        assertTrue(colorGroup.canBuyHotel(boardwalk));

    }

    @Test
    public void testCanBuyHotel() throws PlayerNotFoundException {
        banker.deposit(owner, 5000);

        boardwalk.setOwner(owner);
        parkPlace.setOwner(owner);

        assertFalse(colorGroup.canBuyHotel(boardwalk));
        assertFalse(colorGroup.canBuyHotel(parkPlace));
        for (int i = 1; i <= 4; i++) {
            boardwalk.buyHouse(banker);
            parkPlace.buyHouse(banker);
        }
        assertTrue(colorGroup.canBuyHotel(boardwalk));
        assertTrue(colorGroup.canBuyHotel(parkPlace));

        boardwalk.buyHotel(banker);
        assertTrue(boardwalk.hasHotel());
        assertFalse(colorGroup.canBuyHotel(boardwalk));
    }

}