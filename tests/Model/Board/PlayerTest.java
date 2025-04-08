package Model.Board;

import static org.junit.jupiter.api.Assertions.*;

import Model.Exceptions.PlayerNotFoundException;
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


    //income tax
//    @Test
//    public void testPlayerMoveToIncomeTaxSpace() throws PlayerNotFoundException {
//        Banker banker = Banker.getInstance();
//        GameBoard gameBoard = new GameBoard();
//        Player player = new HumanPlayer("TestPlayer", gameBoard);
//        Token token = new Token("TestToken");
//        player.setTokenToPlayer(token);
//        banker.addPlayer(player);
//        player.move(player, 4);
//        assertEquals(4, player.getPosition());
//        assertEquals(1400, banker.getBalance(player));
//    }

    //chance cards
//    @Test
//    public void testPlayerChanceCards() throws PlayerNotFoundException {
//        Banker banker = Banker.getInstance();
//        GameBoard gameBoard = new GameBoard();
//        Player player = new HumanPlayer("TestPlayer", gameBoard);
//        Token token = new Token("TestToken");
//        player.setTokenToPlayer(token);
//        banker.addPlayer(player);
//        player.move(player, 7);
//        assertEquals(7, player.getPosition());
//        gameBoard.getCommunityChestCard().drawCard();
//    }


    //community chest cards
//    @Test
//    public void testPlayerCommunityChestCards() throws PlayerNotFoundException {
//        Banker banker = Banker.getInstance();
//        GameBoard gameBoard = new GameBoard();
//        Player player = new HumanPlayer("TestPlayer", gameBoard);
//        Token token = new Token("TestToken");
//        player.setTokenToPlayer(token);
//        banker.addPlayer(player);
//        player.move(player, 2);
//        assertEquals(2, player.getPosition());
//        gameBoard.getCommunityChestCard().drawCard();
//    }
}