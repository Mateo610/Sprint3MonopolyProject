package Model.Board;

import static org.junit.jupiter.api.Assertions.*;

import Model.Exceptions.PlayerNotFoundException;
import Model.Property.ColorGroup;
import Model.Property.Property;
import Model.Property.PropertyColor;
import Model.Spaces.Railroad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest{

    @BeforeEach
    public void setUp() {
        Banker.reset();
        GameBoard.resetInstance();
    }


    @Test
    public void testPlayerConstructor() {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        assertEquals("TestPlayer", player.getName());
        assertFalse(player.isInJail());
        assertEquals(0, player.getPosition());
    }

    @Test
    public void testPlayerMoveToProperty() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard =GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        banker.addPlayer(player);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        player.move(player, 1);
        assertEquals(1, player.getPosition());
        assertEquals(1440, banker.getBalance(player));
    }

    @Test
    public void testPlayerMoveToRailRoad() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        assertEquals(1500, banker.getBalance(player));
        assertEquals(0, player.getPosition());
        player.move(player, 5);
        assertEquals(5, player.getPosition());
        assertEquals(1300, banker.getBalance(player));
    }

    @Test
    public void testPlayerMoveToUtility() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        player.move(player, 12);
        assertEquals(12, player.getPosition());
        assertEquals(1350, banker.getBalance(player));
    }

    @Test
    public void testPlayerMoveToJailVisiting() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        player.move(player,1);
        assertEquals(1,player.getPosition());
        assertEquals(1440,banker.getBalance(player));
        player.move(player,9);
        assertEquals(10,player.getPosition());
        assertEquals("Jail / Just Visiting", gameBoard.getBoardElements()[10].getName());
    }


    //luxury tax
    @Test
    public void testPlayerMoveToTaxSpace() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        player.move(player, 38);
        assertEquals(38, player.getPosition());
        assertEquals(1425, banker.getBalance(player));
    }

    @Test
    public void testComputerPlayerName(){
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new ComputerPlayer("TestPlayer", gameBoard);
        assertEquals("Cpu", player.getName());
    }


    @Test
    public void testPlayerBuyHouse() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        player.move(player, 1);
        assertEquals(1, player.getPosition());
        assertEquals(1440, banker.getBalance(player));
        assertEquals(1,player.getProperties().size());
        Property property = (Property) player.getProperties().get(0);
        Property matchingProperty = null;
        for (Property groupProperty : property.getColorGroup().getProperties()) {
            if (groupProperty != property) {
                groupProperty.setOwner(player);
                banker.addTitleDeed(player, groupProperty);
                matchingProperty = groupProperty;
                break;
            }
        }
        player.sellHouse(property, player);
        assertEquals(31, banker.getAvailableHouses());
    }
    @Test
    public void testPlayerBuyHouses() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();

        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);

        player.move(player, 1);

        Property mediterraneanAvenue = (Property) player.getProperties().get(0);
        Property balticAvenue = null;

        for (Property property : mediterraneanAvenue.getColorGroup().getProperties()) {
            if (property != mediterraneanAvenue) {
                property.setOwner(player);
                banker.addTitleDeed(player, property);
                balticAvenue = property;
                break;
            }
        }

        assertNotNull(balticAvenue);

        // Buy houses evenly
        int initialHouses = banker.getAvailableHouses();
        System.out.println("Initial available houses: " + initialHouses);

        // Buy four houses on each property
        for (int i = 0; i < 8; i++) {
            try {
                Property propertyToBuyHouseOn = (i % 2 == 0) ? mediterraneanAvenue : balticAvenue;
                int beforeHouses = banker.getAvailableHouses();
                player.sellHouse(propertyToBuyHouseOn, player);
                int afterHouses = banker.getAvailableHouses();
                assertEquals(beforeHouses - 1, afterHouses, "Available houses should decrease by 1");
                System.out.println("Bought house on " + propertyToBuyHouseOn.getName() + ". Available houses: " + afterHouses);
            } catch (Exception e) {
                System.out.println("Failed to buy house " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
                fail("House purchase failed");
            }
        }

//// Buy a hotel on the first property
//        try {
//            int initialHotels = banker.getAvailableHotels();
//            player.sellHotel(mediterraneanAvenue, player);
//            assertTrue(mediterraneanAvenue.hasHotel());
//            assertEquals(0, mediterraneanAvenue.getNumHouses());
//            assertEquals(initialHotels - 1, banker.getAvailableHotels(), "Available hotels should decrease by 1");
//        } catch (Exception e) {
//            System.out.println("Failed to buy hotel: " + e.getMessage());
//            e.printStackTrace();
//            fail("Hotel purchase failed");
//        }
    }

    @Test
    public void testPlayerMoveToIncomeTaxSpace() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        player.move(player, 4);
        assertEquals(4, player.getPosition());
        assertEquals(1350, banker.getBalance(player));
    }

    @Test
    public void testPropertySellProperty() throws PlayerNotFoundException {
        Banker banker = Banker.getInstance();
        GameBoard gameBoard = GameBoard.getInstance();
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        banker.addPlayer(player);
        ColorGroup colorGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
        Property boardwalk = new Property(
                "Boardwalk",
                39,
                400,
                50,
                new int[]{200, 600, 1400, 1700},
                2000,
                200,
                PropertyColor.DARK_BLUE,
                colorGroup);
        banker.addAvailableProperty(boardwalk);
        player.sellProperty(boardwalk, player);
        assertEquals(1, player.getProperties().size());
        assertEquals(1100, banker.getBalance(player));
    }

}