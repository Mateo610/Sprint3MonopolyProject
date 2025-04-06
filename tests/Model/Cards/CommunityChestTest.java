package Model.Cards;

import Model.Board.Banker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommunityChestTest {

    @BeforeEach
    public void setUp() {
        Banker.reset();
        CommunityChestCard.reset();
    }

    @Test
    public void TestCommunityChestDatabaseCardType() {
        CommunityChestCard communityChestCard = CommunityChestCard.getInstance();
        Assertions.assertEquals(CardType.Community_Chest, communityChestCard.getCardType());
    }

    @Test
    public void TestCommunityChestDatabaseDeck() {
        CommunityChestCard communityChestCard = CommunityChestCard.getInstance();
        ArrayList<String> deckTest = new ArrayList<>();
        deckTest.add("Advance to Go (Collect $200)");
        deckTest.add("You inherit $100");
        assertEquals(deckTest.get(0), communityChestCard.getCardDeck().get(0));
        assertEquals(deckTest.get(deckTest.size() - 1), communityChestCard.getCardDeck().get(communityChestCard.getCardDeck().size() - 1));
        assertEquals(16, communityChestCard.getCardDeck().size());
    }

    @Test
    public void TestCommunityChestCardDatabaseDeckSize() {
        CommunityChestCard communityChestCard = CommunityChestCard.getInstance();
        assertEquals(16, communityChestCard.getCardDeck().size());
    }


    @Test
    public void testDrawCardFromDeck(){
        CommunityChestCard communityChestCard = CommunityChestCard.getInstance();
        communityChestCard.drawCard();
        assertEquals(15, communityChestCard.getCardDeck().size());
    }


    @Test
    public void testDrawAllCardsAndRestore(){
        CommunityChestCard communityChestCard = CommunityChestCard.getInstance();
        for (int i = 0; i < 16; i++) {
            communityChestCard.drawCard();
        }
        assertEquals(0, communityChestCard.getCardDeck().size());
        communityChestCard.cardRestore();
        assertEquals(16, communityChestCard.getCardDeck().size());
    }


}