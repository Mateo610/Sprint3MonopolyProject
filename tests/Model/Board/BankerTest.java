package Model.Board;

import Model.Exceptions.InsufficientFundsException;
import Model.Exceptions.InvalidTransactionException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.ColorGroup;
import Model.Property.Property;
import Model.Property.PropertyColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class BankerTest {


    @BeforeEach
    public void setUp() {
        Banker.reset();
        GameBoard.resetInstance();
    }


    @Test
    public void testAddPlayerAndGetBalance() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        assertEquals(1500, banker.getBalance(player));
    }

    @Test
    public void testRemovePlayer() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        banker.removePlayer(player);
        assertThrows(PlayerNotFoundException.class, () -> banker.getBalance(player));
    }

    @Test
    public void testAddPlayerTwice() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        Player player2 = new HumanPlayer("TestPlayer2", board);
        banker.addPlayer(player);
        banker.addPlayer(player2);
        assertEquals(1500,banker.getBalance(player));
        assertEquals(1500,banker.getBalance(player2));
    }

    @Test
    public void testDepositToPlayer() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        banker.deposit(player, 500);
        assertEquals(2000, banker.getBalance(player));
    }

    @Test
    public void testWithdrawFromPlayer() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        banker.withdraw(player, 500);
        assertEquals(1000, banker.getBalance(player));
    }

    @Test
    public void testWithdrawToAPlayerWhoDoesntExist() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        assertThrows(PlayerNotFoundException.class, () -> banker.withdraw(player, 500));
    }

    @Test
    public void testDepositToAPlayerWhoDoesntExist() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        assertThrows(PlayerNotFoundException.class, () -> banker.deposit(player, 500));
    }

    @Test
    public void testDepositWithNegativeAmount() throws InvalidTransactionException, PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        assertThrows(InvalidTransactionException.class, () -> banker.deposit(player, -500));
    }

    @Test
    public void testWithdrawFromAPlayerWithNegativeAmount() throws InvalidTransactionException, PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        assertThrows(InvalidTransactionException.class, () -> banker.withdraw(player, -500));
    }

    @Test
    public void testWithdrawFromAPlayerWithInsufficientFunds() throws InsufficientFundsException, PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        assertThrows(InsufficientFundsException.class, () -> banker.withdraw(player, 1600));
    }


    @Test
    public void testAddPlayerTittleDeedProperty() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        assertEquals(0, banker.getTitleDeedsAll().size());
        ColorGroup colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        Property property = new Property("Boardwalk", 39, 400,
                50, new int[]{200, 600, 1400, 1700}, 2000,
                200, PropertyColor.DARK_BLUE, colorGroup);
        Property property2 = new Property("Park Place", 37, 350,
                35, new int[]{175, 500, 1100, 1300}, 1500,
                200, PropertyColor.DARK_BLUE, colorGroup);
        banker.addTitleDeed(player, property);
        assertEquals(1, banker.getTitleDeedsAll().size());
        banker.removeTitleDeed(player, property);
        assertEquals(0, banker.getTitleDeedsAll().size());
        banker.addTitleDeed(player, property);
        banker.addTitleDeed(player, property2);
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        assertEquals(properties, banker.getPlayerProperties(player));
    }

    @Test
    public void testRemovePlayerTitleDeedProperty() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        ColorGroup colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        Property property = new Property("Boardwalk", 39, 400,
                50, new int[]{200, 600, 1400, 1700}, 2000,
                200, PropertyColor.DARK_BLUE, colorGroup);
        Property property2 = new Property("Park Place", 37, 350,
                35, new int[]{175, 500, 1100, 1300}, 1500,
                200, PropertyColor.DARK_BLUE, colorGroup);
        banker.addTitleDeed(player, property);
        banker.addTitleDeed(player, property2);
        banker.removeTitleDeed(player, property2);
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(property);
        assertEquals(properties, banker.getPlayerProperties(player));
    }

    @Test
    public void testSellPropertyToAPlayer() throws PlayerNotFoundException {
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player);
        ColorGroup colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        Property property = new Property("Boardwalk", 39, 400,
                50, new int[]{200, 600, 1400, 1700}, 2000,
                200, PropertyColor.DARK_BLUE, colorGroup);
        banker.addAvailableProperty(property);
        banker.sellProperty(property, player);
        assertEquals(1, banker.getPlayerProperties(player).size());
    }

    @Test
    public void testPreloadAllAvailablePropertiesAndSellToAPlayer() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        banker.initializeProperties();
        assertEquals(28,banker.getAvailableProperties().size());
        banker.sellProperty(banker.getAvailableProperties().get(0), player);
        assertEquals(27, banker.getAvailableProperties().size());
    }

    @Test
    public void testAddHouseToAProperty(){
        Banker banker = Banker.getInstance();
        GameBoard board = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", board);
        banker.addPlayer(player);
        ColorGroup colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        Property property = new Property("Boardwalk", 39, 400,
                50, new int[]{200, 600, 1400, 1700}, 2000,
                200, PropertyColor.DARK_BLUE, colorGroup);
        banker.addTitleDeed(player, property);
        assertEquals(32, banker.getAvailableHouses());
        assertEquals(12, banker.getAvailableHotels());
    }



}