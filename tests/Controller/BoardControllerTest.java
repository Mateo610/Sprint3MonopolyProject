package Controller;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.ColorGroup;
import Model.Property.Property;
import Model.Property.PropertyColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardControllerTest {

//    private BoardController boardController;
//    private GameBoard gameBoard;
//    private Player player;
//    private Property property;
//    private Banker banker;
//    private ColorGroup colorGroup;
//
//    @BeforeEach
//    public void setUp() {
//        // Reset singleton instances
//        Banker.reset();
//        GameBoard.resetInstance();
//
//        // Create a new game board and controller
//        gameBoard = GameBoard.getInstance();
//        boardController = new BoardController();
//        banker = Banker.getInstance();
//
//        // Create a player
//        player = new HumanPlayer("TestPlayer", gameBoard);
//        banker.addPlayer(player);
//
//        // Create a test property
//        colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
//        property = new Property(
//                "Boardwalk",
//                39,
//                400,
//                50,
//                new int[]{200, 600, 1400, 1700},
//                2000,
//                200,
//                PropertyColor.DARK_BLUE,
//                colorGroup
//        );
//        colorGroup.addProperty(property);
//    }
//
//    @Test
//    public void testBuyHouse_PropertyNotOwnedByPlayer() {
//        // Property doesn't belong to player
//        property.setOwner(null);
//
//        // Try to buy house
//        boolean result = boardController.buyHouse(property, player);
//
//        // Should fail because player doesn't own property
//        assertFalse(result);
//    }
//
//    @Test
//    public void testBuyHotel_PropertyNotOwnedByPlayer() {
//        // Property doesn't belong to player
//        property.setOwner(null);
//
//        // Try to buy hotel
//        boolean result = boardController.buyHotel(property, player);
//
//        // Should fail because player doesn't own property
//        assertFalse(result);
//    }
//
//    @Test
//    public void testSellHouse_PropertyNotOwnedByPlayer() {
//        // Property doesn't belong to player
//        property.setOwner(null);
//
//        // Try to sell house
//        boolean result = boardController.sellHouse(property, player);
//
//        // Should fail because player doesn't own property
//        assertFalse(result);
//    }
//
//    @Test
//    public void testSellHotel_PropertyNotOwnedByPlayer() {
//        // Property doesn't belong to player
//        property.setOwner(null);
//
//        // Try to sell hotel
//        boolean result = boardController.sellHotel(property, player);
//
//        // Should fail because player doesn't own property
//        assertFalse(result);
//    }
//
//    @Test
//    public void testBuyHotel_PlayerOwnsProperty() {
//        // Set property owner to player
//        property.setOwner(player);
//        banker.addTitleDeed(player, property);
//
//        // Add 4 houses to meet hotel requirement (private method prevents this from succeeding in normal tests)
//        // This test just verifies that the method is called and returns false in current implementation
//        boolean result = boardController.buyHotel(property, player);
//
//        // Current implementation returns false for canBuyHotel
//        assertFalse(result);
//    }
//
//    @Test
//    public void testBuyHouse_PlayerOwnsProperty() {
//        // Set property owner to player
//        property.setOwner(player);
//        banker.addTitleDeed(player, property);
//
//        // Current implementation returns false for canBuyHouse
//        boolean result = boardController.buyHouse(property, player);
//
//        // Current implementation returns false
//        assertFalse(result);
//    }
//
//    @Test
//    public void testMissingPlayerException() {
//        // Test exception handling for null player
//        Player nullPlayer = null;
//
//        // RuntimeException wraps PlayerNotFoundException
//        assertThrows(RuntimeException.class, () -> boardController.buyHouse(property, nullPlayer));
//        assertThrows(RuntimeException.class, () -> boardController.buyHotel(property, nullPlayer));
//        assertThrows(RuntimeException.class, () -> boardController.sellHouse(property, nullPlayer));
//        assertThrows(RuntimeException.class, () -> boardController.sellHotel(property, nullPlayer));
//    }
}