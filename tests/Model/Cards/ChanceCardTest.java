package Model.Cards;

import Model.Board.Banker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChanceCardTest {

    @BeforeEach
    public void setUp() {
        Banker.reset();
        ChanceCard.reset();
    }

    @Test
    public void TestChanceCardDatabaseCardType() {
        ChanceCard chanceCard = ChanceCard.getInstance();
        Assertions.assertEquals(CardType.Chance, chanceCard.getCardType());
    }

    @Test
    public void TestChanceCardDatabaseDeck() {
        ArrayList<String> testDescriptions = new ArrayList<>();
        testDescriptions.add("Advance to Boardwalk.");
        testDescriptions.add("Your building loan matures. Collect $150.");
        ChanceCard chanceCard = ChanceCard.getInstance();
        assertEquals(testDescriptions.get(0), chanceCard.getCardDeck().get(0));
        assertEquals(testDescriptions.get(testDescriptions.size() - 1), chanceCard.getCardDeck().get(chanceCard.getCardDeck().size() - 1));        assertEquals(16, chanceCard.getCardDeck().size());
    }

    @Test
    public void TestChanceCardDatabaseDeckSize() {
        ChanceCard chanceCard = ChanceCard.getInstance();
        assertEquals(16, chanceCard.getCardDeck().size());
    }


    @Test
    public void testDrawCardFromDeck(){
        ChanceCard chanceCard = ChanceCard.getInstance();
        chanceCard.drawCard();
        assertEquals(15, chanceCard.getCardDeck().size());
    }


    @Test
    public void testDrawAllCardsAndRestore(){
        ChanceCard chanceCard = ChanceCard.getInstance();
        for (int i = 0; i < 16; i++) {
            chanceCard.drawCard();
        }
        assertEquals(0, chanceCard.getCardDeck().size());
        chanceCard.cardRestore();
        assertEquals(16, chanceCard.getCardDeck().size());
    }

}