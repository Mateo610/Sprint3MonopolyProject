package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxSpaceTest {

    private TaxSpace incomeTaxSpace;
    private TaxSpace luxuryTaxSpace;
    private Player player;
    private GameBoard gameBoard;
    private Banker banker;
    private static final int INCOME_TAX_AMOUNT = 200;
    private static final int LUXURY_TAX_AMOUNT = 75;

    @BeforeEach
    void setUp() throws PlayerNotFoundException {
        // Reset singletons
        Banker.reset();
        GameBoard.resetInstance();

        // Create test objects
        gameBoard = GameBoard.getInstance();
        banker = Banker.getInstance();
        incomeTaxSpace = new TaxSpace("Income Tax", 4);
        luxuryTaxSpace = new TaxSpace("Luxury Tax", 38);
        player = new HumanPlayer("TestPlayer", gameBoard);
        banker.addPlayer(player);
    }

    @Test
    void getPurchasePrice() {
        // Tax spaces should not be purchasable
        assertEquals(0, incomeTaxSpace.getPurchasePrice());
        assertEquals(0, luxuryTaxSpace.getPurchasePrice());
    }

    @Test
    void setOwner() {
        // Set owner should have no effect
        incomeTaxSpace.setOwner(player);
        assertNull(incomeTaxSpace.getOwner());

        luxuryTaxSpace.setOwner(player);
        assertNull(luxuryTaxSpace.getOwner());
    }

    @Test
    void calculateRent() {
        // Tax spaces should not have rent
        assertEquals(0, incomeTaxSpace.calculateRent(player));
        assertEquals(0, luxuryTaxSpace.calculateRent(player));
    }

    @Test
    void getOwner() {
        // Tax spaces should not have an owner
        assertNull(incomeTaxSpace.getOwner());
        assertNull(luxuryTaxSpace.getOwner());
    }

    @Test
    void onLanding_IncomeTax() throws PlayerNotFoundException {
        int initialBalance = banker.getBalance(player);

        // Landing on Income Tax
        incomeTaxSpace.onLanding(player);

        // In the current implementation, player pays the lower of:
        // - 10% of their balance
        // - Flat fee of $200
        int percentageTax = (int)(initialBalance * 0.1); // 10% of balance
        int expectedTax = Math.min(percentageTax, 200); // Take the lower amount

        // Balance should be reduced by tax amount
        assertEquals(initialBalance - expectedTax, banker.getBalance(player));
    }

    @Test
    void onLanding_LuxuryTax() throws PlayerNotFoundException {
        int initialBalance = banker.getBalance(player);

        // Landing on Luxury Tax
        luxuryTaxSpace.onLanding(player);

        // Balance should be reduced by luxury tax amount
        assertEquals(initialBalance - LUXURY_TAX_AMOUNT, banker.getBalance(player));
    }

    @Test
    void onPassing() throws PlayerNotFoundException {
        // Nothing should happen when passing tax spaces
        int initialBalance = banker.getBalance(player);

        incomeTaxSpace.onPassing(player);
        assertEquals(initialBalance, banker.getBalance(player));

        luxuryTaxSpace.onPassing(player);
        assertEquals(initialBalance, banker.getBalance(player));
    }
}