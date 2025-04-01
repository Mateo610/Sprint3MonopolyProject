package Model.GameTests;

import Model.Board.Banker;
import Model.Board.ComputerPlayer;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;
import Model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    public void setUp() {
        Banker.reset();
    }

    @Test
    public void startGame() throws PlayerNotFoundException {
        Game game = new Game();
        assertNotNull(game.getBoard());
        Player player = new HumanPlayer("Player 1", game.getBoard());
        Player computerPlayer = new ComputerPlayer("Player 2", game.getBoard());
        game.addPlayer(player);
        game.addPlayer(computerPlayer);
        game.startGame();
        assertTrue(game.gameInProgress());
        assertNotNull(game.getPlayers());
        assertEquals(2, game.getPlayers().size());
        game.outputGameState();
    }

    @Test
    public void gameInProgress() {
        Game game = new Game();
        assertFalse(game.gameInProgress());
        Player player = new HumanPlayer("Player 1", game.getBoard());
        Player computerPlayer = new ComputerPlayer("Player 2", game.getBoard());
        game.addPlayer(player);
        game.addPlayer(computerPlayer);
        game.startGame();
        assertTrue(game.gameInProgress());
    }
}