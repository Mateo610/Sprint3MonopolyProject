package Model.Board;

import static org.junit.jupiter.api.Assertions.*;

import Model.Exceptions.PlayerNotFoundException;
import org.junit.jupiter.api.Test;


public class PlayerTest{

    @Test
    public void testPlayerConstructor() {
        Banker banker = new Banker();
        GameBoard gameBoard = new GameBoard(banker);
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        assertEquals("TestPlayer", player.getName());
        assertFalse(player.isInJail());
        assertEquals(0, player.getPosition());
    }

//    @Test
//    public void testPlayerMoveToProperty() throws PlayerNotFoundException {
//        Banker banker = new Banker();
//        GameBoard gameBoard = new GameBoard(banker);
//        Player player = new HumanPlayer("TestPlayer", gameBoard);
//        banker.addPlayer(player);
//        Token token = new Token("TestToken");
//        player.setTokenToPlayer(token);
//        player.move(player, 1);
//        assertEquals(1, player.getPosition());
//        assertEquals(1440, banker.getBalance(player));
//    }
//
//    @Test
//    public void testPlayerMoveToRailRoad() throws PlayerNotFoundException {
//        Banker banker = new Banker();
//        GameBoard gameBoard = new GameBoard(banker);
//        Player player = new HumanPlayer("TestPlayer", gameBoard);
//        Token token = new Token("TestToken");
//        player.setTokenToPlayer(token);
//        assertEquals(1500, banker.getBalance(player));
//        assertEquals(0, player.getPosition());
//        player.move(player, 4);
//        assertEquals(4, player.getPosition());
//        assertEquals(1700, banker.getBalance(player));
//    }

    @Test
    public void testPlayerMoveToJailVisiting() throws PlayerNotFoundException {
        Banker banker = new Banker();
        GameBoard gameBoard = new GameBoard(banker);
        Player player = new HumanPlayer("TestPlayer", gameBoard);
        Token token = new Token("TestToken");
        player.setTokenToPlayer(token);
        player.move(player,1);
        assertEquals(1,player.getPosition());
        banker.addPlayer(player);
        assertEquals(1500,banker.getBalance(player));
        player.move(player,9);
        assertEquals(10,player.getPosition());
        assertEquals("Jail / Just Visiting", gameBoard.getBoardElements()[10].getName());
    }


}