package Model.Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommunityChestTest {

    @Test
    public void TestCommunityChestDatabaseCardType() {
        CommunityChestCard database = new CommunityChestCard();
        Assertions.assertEquals(CardType.Community_Chest, database.getCardType());
    }

    @Test
    public void TestCommunityChestDatabaseDeck() {
        CommunityChestCard database = new CommunityChestCard();
        ArrayList<String> deckTest = new ArrayList<>();
        deckTest.add("Advance to Go (Collect $200)");
        deckTest.add("You inherit $100");
        assertEquals(deckTest.get(0), database.getCardDeck().get(0));
        assertEquals(deckTest.get(deckTest.size() - 1), database.getCardDeck().get(database.getCardDeck().size() - 1));
        assertEquals(16, database.getCardDeck().size());
    }

    @Test
    public void TestCommunityChestCardDatabaseDeckSize() {
        CommunityChestCard CommunityChestCard = new CommunityChestCard();
        assertEquals(16, CommunityChestCard.getCardDeck().size());
    }


    @Test
    public void testDrawCardFromDeck(){
        CommunityChestCard CommunityChestCard = new CommunityChestCard();
        CommunityChestCard.drawCard();
        assertEquals(15, CommunityChestCard.getCardDeck().size());
    }


    @Test
    public void testDrawAllCardsAndRestore(){
        CommunityChestCard CommunityChestCard = new CommunityChestCard();
        for (int i = 0; i < 16; i++) {
            CommunityChestCard.drawCard();
        }
        assertEquals(0, CommunityChestCard.getCardDeck().size());
        CommunityChestCard.cardRestore();
        assertEquals(16, CommunityChestCard.getCardDeck().size());
    }


}