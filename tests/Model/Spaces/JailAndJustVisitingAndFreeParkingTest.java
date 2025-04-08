package Model.Spaces;

import Model.Board.*;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JailAndJustVisitingAndFreeParkingTest {

    private JailAndJustVisitingAndFreeParking jailSpace;
    private Player player;
    private GameBoard gameBoard;
    private Banker banker;
    private static final int JAIL_FINE = 50;

    @BeforeEach
    void setUp() throws PlayerNotFoundException {
        // Reset singletons
        Banker.reset();
        GameBoard.resetInstance();

        // Create test objects
        gameBoard = GameBoard.getInstance();
        banker = Banker.getInstance();
        jailSpace = new JailAndJustVisitingAndFreeParking("Jail / Just Visiting", 10);
        player = new HumanPlayer("TestPlayer", gameBoard);
        banker.addPlayer(player);
    }

    @Test
    void getPurchasePrice() {
        // Jail space should not be purchasable
        assertEquals(0, jailSpace.getPurchasePrice());
    }

    @Test
    void setOwner() {
        // Set owner should have no effect
        jailSpace.setOwner(player);
        assertNull(jailSpace.getOwner());
    }

    @Test
    void calculateRent() {
        // Jail space should not have rent
        assertEquals(0, jailSpace.calculateRent(player));
    }

    @Test
    void getOwner() {
        // Jail space should not have an owner
        assertNull(jailSpace.getOwner());
    }

    @Test
    void onLanding_JustVisiting() {
        // When player is not in jail, they're just visiting
        player.setInJail(false);

        // Landing on jail space should not put them in jail
        jailSpace.onLanding(player);
        assertFalse(player.isInJail());
    }

    @Test
    void onLanding_InJail() throws PlayerNotFoundException {
        // Put player in jail
        player.setInJail(true);

        // Initial balance and jail turn count
        int initialBalance = banker.getBalance(player);
        int initialJailTurns = player.getTurnsInJail();

        // Player is already in jail, landing should increment their jail turns
        jailSpace.onLanding(player);

        // Still in jail after first turn
        assertTrue(player.isInJail());

        // Balance should remain unchanged when just visiting jail
        assertEquals(initialBalance, banker.getBalance(player));
    }

    @Test
    void payToGetOutOfJail() throws PlayerNotFoundException {
        // Put player in jail
        player.setInJail(true);
        int initialBalance = banker.getBalance(player);

        // Player pays to get out of jail
        boolean result = jailSpace.payToGetOutOfJail(player);

        // Should be successful
        assertTrue(result);

        // Player should be out of jail
        assertFalse(player.isInJail());

        // Balance should be reduced by jail fine
        assertEquals(initialBalance - JAIL_FINE, banker.getBalance(player));
    }

    @Test
    void payToGetOutOfJail_NotInJail() throws PlayerNotFoundException {
        // Player is not in jail
        player.setInJail(false);
        int initialBalance = banker.getBalance(player);

        // Attempt to pay to get out of jail
        boolean result = jailSpace.payToGetOutOfJail(player);

        // Should fail because player is not in jail
        assertFalse(result);

        // Balance should remain unchanged
        assertEquals(initialBalance, banker.getBalance(player));
    }

    @Test
    void payToGetOutOfJail_InsufficientFunds() throws PlayerNotFoundException {
        // Put player in jail
        player.setInJail(true);

        // Set player's balance to below jail fine
        banker.withdraw(player, banker.getBalance(player) - 40);

        // Attempt to pay to get out of jail
        boolean result = jailSpace.payToGetOutOfJail(player);

        // Should fail due to insufficient funds
        assertFalse(result);

        // Player should still be in jail
        assertTrue(player.isInJail());
    }

    @Test
    void useGetOutOfJailFreeCard_HasCard() {
        // Put player in jail
        player.setInJail(true);

        // Give player a Get Out of Jail Free card
        player.addGetOutOfJailFreeCard();

        // Use card to get out of jail
        boolean result = jailSpace.useGetOutOfJailFreeCard(player);

        // Should be successful
        assertTrue(result);

        // Player should be out of jail
        assertFalse(player.isInJail());

        // Player should have used their card
        assertEquals(0, player.getGetOutOfJailFreeCard());
    }

    @Test
    void useGetOutOfJailFreeCard_NoCard() {
        // Put player in jail
        player.setInJail(true);

        // Player has no Get Out of Jail Free card
        assertEquals(0, player.getGetOutOfJailFreeCard());

        // Attempt to use card
        boolean result = jailSpace.useGetOutOfJailFreeCard(player);

        // Should fail
        assertFalse(result);

        // Player should still be in jail
        assertTrue(player.isInJail());
    }

    @Test
    void useGetOutOfJailFreeCard_NotInJail() {
        // Player is not in jail
        player.setInJail(false);

        // Give player a Get Out of Jail Free card
        player.addGetOutOfJailFreeCard();

        // Attempt to use card
        boolean result = jailSpace.useGetOutOfJailFreeCard(player);

        // Should fail because player is not in jail
        assertFalse(result);

        // Player should still have their card
        assertEquals(1, player.getGetOutOfJailFreeCard());
    }

    @Test
    void onPassing() {
        // Nothing should happen when passing jail
        boolean initialJailStatus = player.isInJail();

        jailSpace.onPassing(player);

        // Jail status should remain unchanged
        assertEquals(initialJailStatus, player.isInJail());
    }
}