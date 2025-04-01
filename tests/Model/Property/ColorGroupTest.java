package Model.Property;

import Model.Board.Banker;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class ColorGroupTest {
    private ColorGroup colorGroup;
    private Property boardwalk;
    private Property parkPlace;
    private Player owner;


    @BeforeEach
    public void setUp() {
        Banker.reset();
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
    }

    @Test
    public void testConstructor() {
        assertEquals(PropertyColor.DARK_BLUE, colorGroup.getColor());
        assertEquals(2, colorGroup.getPropertiesInGroup());
        assertTrue(colorGroup.getProperties().isEmpty());
    }

    @Test
    public void testAddProperty() {
        colorGroup.addProperty(boardwalk);
        assertEquals(1, colorGroup.getProperties().size());
        assertTrue(colorGroup.getProperties().contains(boardwalk));
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
    public void testGetPropertiesReturnsCopy() {
        colorGroup.addProperty(boardwalk);
        List<Property> properties = colorGroup.getProperties();
        properties.clear(); // Modifying the returned list
        assertEquals(1, colorGroup.getProperties().size());
    }
}