//package tests.Model.Board;
//
//import Model.Board.*;
//import Model.Exceptions.PlayerNotFoundException;
//import Model.Game;
//import Model.Property.ColorGroup;
//import Model.Property.Property;
//import Model.Property.PropertyColor;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class BankerAuctionTest {

//    private Banker banker;
//    private Game game;
//    private GameBoard gameBoard;
//    private Player humanPlayer;
//    private Player computerPlayer;
//    private Property testProperty;
//    private List<Player> players;
//
//    @Before
//    public void setUp() {
//        // Create a new game instance for each test
//        game = new Game();
//        gameBoard = game.getBoard();
//        banker = gameBoard.getBanker();
//
//        // Create players
//        humanPlayer = new HumanPlayer("TestHuman", gameBoard);
//        computerPlayer = new ComputerPlayer("Cpu", gameBoard);
//
//        // Add players to game
//        game.addPlayer(humanPlayer);
//        game.addPlayer(computerPlayer);
//
//        // Create a test property
//        ColorGroup colorGroup = new ColorGroup(PropertyColor.BROWN, 2);
//        testProperty = new Property("Test Property", 1, 100,
//                                   10, new int[]{30, 90, 160, 250},
//                                   350, 50, PropertyColor.BROWN, colorGroup);
//
//        // Set up initial balances
//        try {
//            banker.deposit(humanPlayer, 1000);
//            banker.deposit(computerPlayer, 1000);
//        } catch (PlayerNotFoundException e) {
//            fail("Player not found during setup");
//        }
//
//        // Set up player list for auction
//        players = new ArrayList<>();
//        players.add(humanPlayer);
//        players.add(computerPlayer);
//    }
//
//    /**
//     * Test that the auction returns a winner
//     */
//    @Test
//    public void testAuctionHasWinner() {
//        Player winner = banker.auctionProperty(testProperty, players);
//
//        // Assertions
//        assertNotNull("Auction should have a winner", winner);
//        assertEquals("Property owner should be set to winner", winner, testProperty.getOwner());
//    }
//
//    /**
//     * Test that the auction deducts money from the winner
//     */
//    @Test
//    public void testAuctionDeductsMoney() {
//        try {
//            // Record balances before auction
//            int humanBalanceBefore = banker.getBalance(humanPlayer);
//            int computerBalanceBefore = banker.getBalance(computerPlayer);
//
//            // Run auction
//            Player winner = banker.auctionProperty(testProperty, players);
//
//            // Check that winner's balance decreased
//            if (winner == humanPlayer) {
//                int balanceAfter = banker.getBalance(humanPlayer);
//                assertTrue("Winner's balance should decrease", balanceAfter < humanBalanceBefore);
//            } else if (winner == computerPlayer) {
//                int balanceAfter = banker.getBalance(computerPlayer);
//                assertTrue("Winner's balance should decrease", balanceAfter < computerBalanceBefore);
//            } else {
//                fail("Unexpected winner in auction");
//            }
//
//        } catch (PlayerNotFoundException e) {
//            fail("Player not found: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Test auction with no bidders
//     */
//    @Test
//    public void testAuctionWithNoBidders() {
//        List<Player> emptyList = new ArrayList<>();
//        Player winner = banker.auctionProperty(testProperty, emptyList);
//
//        // Assertions
//        assertNull("Auction should have no winner with empty bidders list", winner);
//        assertNull("Property should remain unowned", testProperty.getOwner());
//    }
//
//    /**
//     * Test auction with null property
//     */
//    @Test
//    public void testAuctionWithNullProperty() {
//        Player winner = banker.auctionProperty(null, players);
//
//        // Assertions
//        assertNull("Auction should have no winner with null property", winner);
//    }
//
//    /**
//     * Test property onLanding with auction
//     */
//    @Test
//    public void testPropertyOnLandingWithAuction() {
//        try {
//            // Set human player to not afford property
//            banker.withdraw(humanPlayer, 960);
//            assertEquals(40, banker.getBalance(humanPlayer));
//
//            // Land on property
//            testProperty.onLanding(humanPlayer);
//
//            // Property should now have an owner
//            assertNotNull("Property should have an owner after landing", testProperty.getOwner());
//
//        } catch (PlayerNotFoundException e) {
//            fail("Player not found: " + e.getMessage());
//        }
//    }
//}