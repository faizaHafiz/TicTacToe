package tictactoe.controller;

import tictactoe.Exceptions.BotCountMoreThanOneException;
import tictactoe.Exceptions.DuplicateSymbolException;
import tictactoe.Exceptions.PlayerCountException;
import tictactoe.models.Game;
import tictactoe.models.GameState;
import tictactoe.models.Player;
import tictactoe.strategies.WinningStrategy.WinningStrategy;

import java.util.List;

public class GameController {
    private Game game; //Having a state
    public Game startGame(int dimension, List<Player> players,
                          List<WinningStrategy> winningStrategies) throws BotCountMoreThanOneException, PlayerCountException, DuplicateSymbolException {

        return Game.builder()
                .setDimensions(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies) // setting these three from client
                .build();
    }
    public void makeMove(Game game){
        game.makeMove();
    }
    public GameState checkGameState(Game game){
        return  game.getGameState();
    }
    public void printBoard(Game game){
        game.printBoard();
    }
    public void undo(Game game){
        game.undo();
    }
    public String getWinner(Game game){
        return game.getWinner().getName();
    }
}
