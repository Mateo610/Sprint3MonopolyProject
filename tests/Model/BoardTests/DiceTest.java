package Model.BoardTests;

import static org.junit.jupiter.api.Assertions.*;

import Model.Board.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiceTest {

    private Dice dice;

    @BeforeEach
    public void setUp() {
        dice = Dice.getInstance();
        dice.reset();
    }

    @Test
    public void testRollWithinValidRange() {
        for (int i = 0; i < 100; i++) {
            dice.roll();
            assertTrue(dice.getDie1() >= 1 && dice.getDie1() <= 6, "Die1 is out of range");
            assertTrue(dice.getDie2() >= 1 && dice.getDie2() <= 6, "Die2 is out of range");
        }
    }

    @Test
    public void testSumIsValid() {
        for (int i = 0; i < 100; i++) {
            dice.roll();
            int sum = dice.getSum();
            assertTrue(sum >= 2 && sum <= 12, "Sum is out of range");
        }
    }

    @Test
    public void testDoublesDetection() {
        boolean foundDouble = false;
        for (int i = 0; i < 100; i++) {
            dice.roll();
            if (dice.getDie1() == dice.getDie2()) {
                foundDouble = true;
                assertTrue(dice.isDouble(), "Dice should detect doubles correctly");
            }
        }
        assertTrue(foundDouble, "At least one double should appear in 100 rolls");
    }

    @Test
    public void testTripleDoubleGoesToJail() {
        int doubleCount = 0;
        for (int i = 0; i < 3; i++) {
            do {
                dice.roll();
            } while
            (dice.getDie1() != dice.getDie2());
            doubleCount++;
            dice.isDouble();
        }
        assertEquals(3, doubleCount, "Should have rolled three doubles");
        assertTrue(dice.goToJail(), "Player should go to jail after three doubles");

    }
}
