package tictactoe;

import tictactoe.Exceptions.BotCountMoreThanOneException;
import tictactoe.Exceptions.DuplicateSymbolException;
import tictactoe.Exceptions.PlayerCountException;
import tictactoe.controller.GameController;
import tictactoe.models.*;
import tictactoe.strategies.WinningStrategy.ColWinningStrategy;
import tictactoe.strategies.WinningStrategy.DiagonalWinningStrategy;
import tictactoe.strategies.WinningStrategy.RowWinningStrategy;
import tictactoe.strategies.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        int dimensionOfGame = 3;

        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L,"Faiza",new Symbol('X'), PlayerType.HUMAN)
        );
        players.add(
           new Bot(2L,"bot1",new Symbol('O'),BotDifficulty.EASY)
        );
        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
//        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        Game game = null;
        try{
            game = gameController.startGame(
                    dimensionOfGame,
                    players,
                    winningStrategies
            );
            while (gameController.checkGameState(game).equals(GameState.IN_PROGRESS)){
                gameController.printBoard(game);
                System.out.println("Do you want to undo (y/n)");
                String undo = scanner.next();
                if (undo.equalsIgnoreCase("y")){
                    gameController.undo(game);
                    continue;
                }
                gameController.makeMove(game);
            }

        }catch (Exception ex){
            System.out.println("Something went wrong");
        }

        gameController.printBoard(game);
        System.out.println("Game is Over");
        GameState gameState = gameController.checkGameState(game);
        if(gameState.equals(GameState.WIN)){
            System.out.println("Winner is "+ gameController.getWinner(game));

        }else if(gameState.equals(GameState.DRAW)){
            System.out.println("Draw");
        }
    }
}
