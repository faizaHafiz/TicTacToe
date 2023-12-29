package tictactoe.strategies.WinningStrategy;

import tictactoe.models.Board;
import tictactoe.models.Move;
import tictactoe.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{
    private Map<Integer, Map<Symbol,Integer>> countMap = new HashMap<>(); //map of maps
    //one map for each Row
    // we dont have board size fixed as 3x3 for eg
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!countMap.containsKey((col))){ // no symbol in that particular row
            countMap.put(col,new HashMap<>()); // add a hashmap for that row
        }
        //getting that particular row's hashmap
        Map<Symbol,Integer> colMap = countMap.get(col);
        if (!colMap.containsKey(symbol)){
            colMap.put(symbol,0);
        }

        colMap.put(symbol,colMap.get(symbol) + 1);

        if(colMap.get(symbol) == board.getSize()){
            return true;
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol,Integer> colMap = countMap.get(col);
        colMap.put(symbol,colMap.get(symbol) - 1);
    }
}
