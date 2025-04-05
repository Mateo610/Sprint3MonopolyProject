package Model.Spaces;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Cards.ChanceCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommunityChestSpaceTest {

    @BeforeAll
    static void setUp() {
        Banker.reset();
    }


    @Test
    public void testGetPurchasePrice() {
        GameBoard gameBoard = new GameBoard();
        CommunityChestSpace communityChestSpace= new CommunityChestSpace(7,gameBoard.getCommunityChestCard());
        assertEquals(0,communityChestSpace.getPurchasePrice());

    }

    @Test
    public void testSetOwner() {
        GameBoard gameBoard = new GameBoard();
        CommunityChestSpace communityChestSpace= new CommunityChestSpace(7,gameBoard.getCommunityChestCard());
        assertNull(communityChestSpace.getOwner());
    }

    @Test
    public void testCalculateRent() {
        GameBoard gameBoard = new GameBoard();
        CommunityChestSpace communityChestSpace= new CommunityChestSpace(7,gameBoard.getCommunityChestCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        assertEquals(0,communityChestSpace.calculateRent(player2));
    }

    @Test
    public void testGetOwner() {
        GameBoard gameBoard = new GameBoard();
        CommunityChestSpace communityChestSpace= new CommunityChestSpace(7,gameBoard.getCommunityChestCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        assertNull(communityChestSpace.getOwner());
    }

    @Test
    public void testOnLanding() {

    }

    @Test
    public void testOnPassing() {
        GameBoard gameBoard = new GameBoard();
        CommunityChestSpace communityChestSpace= new CommunityChestSpace(7,gameBoard.getCommunityChestCard());
        Player player2 = new HumanPlayer("Player2", gameBoard);
        Banker banker = Banker.getInstance();
        banker.addPlayer(player2);
        //do nothing basically
    }
}