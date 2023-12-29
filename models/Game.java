package tictactoe.models;


import tictactoe.Exceptions.BotCountMoreThanOneException;
import tictactoe.Exceptions.DuplicateSymbolException;
import tictactoe.Exceptions.PlayerCountException;
import tictactoe.strategies.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private int dimension;

    //constructor - make constructors in every class to avoid nullPointerException
    private Game(int dimension,List<Player> players,List<WinningStrategy> winningStrategies){
        //To avoid null pointer exceptions create constructors
        //players = new ArrayList<>();
        this.dimension = dimension;
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.board = new Board(dimension);
        this.moves = new ArrayList<>();
        this.nextPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
    }

    public static Builder builder(){
        return new Builder();
    }

    public int getSize() {
        return dimension;
    }

    public void setSize(int size) {
        this.dimension = size;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

   public static class Builder{
        //In builder  we put attributes provided by client bcz we need to validate those mostly
       private List<Player> players;
       private List<WinningStrategy> winningStrategies;
       private int dimensions;



       public Builder setPlayers(List<Player> players) {
           this.players = players;
           return  this;
       }

       public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
           this.winningStrategies = winningStrategies;
           return  this;
       }

       public Builder setDimensions(int dimensions) {
           this.dimensions = dimensions;
           return  this;
       }

       private void validateBotCount() throws BotCountMoreThanOneException{
           int botSize = 0;
           for(Player player: players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botSize+=1;
                }
           }
           if(botSize > 1){
               throw new BotCountMoreThanOneException();
           }
       }
       private void validateDimensionAndPlayerCount() throws PlayerCountException{
           if(players.size() != this.dimensions - 1){
               throw new PlayerCountException();
           }
       }

       private void validateSymbolUniqueness() throws DuplicateSymbolException{
           Map<Character,Integer> symbolCount = new HashMap<>();
           for(Player player:players){
               if(symbolCount.containsKey(player.getSymbol().getChar())){
                   symbolCount.put(player.getSymbol().getChar(),
                           symbolCount.get(player.getSymbol().getChar()) + 1
                        );
               }else{
                   symbolCount.put(player.getSymbol().getChar(),1);
               }
               if(symbolCount.get(player.getSymbol().getChar()) > 1){
                   throw new DuplicateSymbolException();
               }
           }
//           Above logic can be replaced with a set as well
       }
       private void validate() throws BotCountMoreThanOneException, DuplicateSymbolException, PlayerCountException {
           validateBotCount();
           validateSymbolUniqueness();
           validateDimensionAndPlayerCount();
       }
       public Game build() throws BotCountMoreThanOneException, PlayerCountException, DuplicateSymbolException {
           validate();
           return new Game(dimensions,players,winningStrategies);
       }
   }
   private boolean validateMove(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row >= board.getSize() || col >= board.getSize()) {
            return false;
        }

        if(!board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)){
            return false;
        }
        return true;
   }
    private boolean checkWinner(Board board,Move move){
        for(WinningStrategy winningStrategy:winningStrategies){
            if(winningStrategy.checkWinner(board,move)){
                return true;
            }
        }
        return false;
    }
    public void makeMove(){
        Player currentMovePlayer = players.get(nextPlayerIndex);
        System.out.println("It is "+currentMovePlayer.getName()+"'s turn");

        //player will make move
        Move currentPlayerMove = currentMovePlayer.makeMove(board);

        //After making move, validate if its correct move or not
        if(!validateMove(currentPlayerMove)){
            System.out.println("Invalid move. Please try again");
            return;
        }
        //after move is validated, update board
        int row = currentPlayerMove.getCell().getRow();
        int col = currentPlayerMove.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        //move list for undo functionality
        // cell should be from board that is why new obj of Move
        Move finalMoveObject = new Move(cellToChange,currentMovePlayer);
        moves.add(finalMoveObject);

        nextPlayerIndex += 1;
        nextPlayerIndex %= players.size();

        if(checkWinner(board,finalMoveObject)){
            gameState = GameState.WIN;
            winner = currentMovePlayer;
        }else if(moves.size() == this.board.getSize() * this.board.getSize()){
            gameState = GameState.DRAW;
        }

    }

    public void printBoard(){
        board.printBoard();
    }

    public void undo(){
        if(moves.size()==0){
            System.out.println("No moves to Undo");
            return;
        }
        //delete lastmove
        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        //make cell empty
        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }

        nextPlayerIndex -= 1;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();
    }
}
