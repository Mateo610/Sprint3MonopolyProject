package Model.Exceptions;

import Model.Game;
import Model.Board.Banker;
import Model.Board.HumanPlayer;
import Model.Board.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GameExceptionTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;

    @BeforeEach
    void setUp() {
        Banker.reset();
        game = new Game();
        player1 = new HumanPlayer("Alice",game.getBoard());
        player2 = new HumanPlayer("Bob",game.getBoard());
        player3 = new HumanPlayer("Charlie",game.getBoard());
        player4 = new HumanPlayer("Dave",game.getBoard());
        player5 = new HumanPlayer("Eve",game.getBoard());
    }

    @Test
    void addPlayerThrowsMaximumPlayerReachedException() throws Exception {
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        assertThrows(MaximumPlayerReachedException.class, () -> game.addPlayer(player5));
    }

    @Test
    void addPlayerThrowsPlayerAlreadyExistsException() throws Exception {
        game.addPlayer(player1);
        assertThrows(PlayerAlreadyExistsException.class, () -> game.addPlayer(player1));
    }

    @Test
    void startGameThrowsNotEnoughPlayersException() {
        game.addPlayer(player1);
        assertThrows(NotEnoughPlayersException.class, game::startGame);
    }

    @Test
    void startGameThrowsGameInProgressException() throws Exception {
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
        assertThrows(GameInProgressException.class, game::startGame);
    }

    @Test
    void winnerThrowsGameNotInProgressException() {
        assertThrows(GameNotInProgressException.class, game::winner);
    }

    @Test
    void bankerThrowsPlayerNotFoundException() {
        Banker banker = Banker.getInstance();
        assertThrows(PlayerNotFoundException.class, () -> banker.getBalance(new HumanPlayer("Nonexistent", game.getBoard())));
    }

    @Test
    void resetGameThrowsGameNotInProgressException() {
        assertThrows(GameNotInProgressException.class, game::resetGame);
    }

    @Test
    void endGameThrowsGameNotInProgressException() {
        assertThrows(GameNotInProgressException.class, game::endGame);
    }
}