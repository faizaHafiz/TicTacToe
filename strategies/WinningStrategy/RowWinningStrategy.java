package tictactoe.strategies.WinningStrategy;

import tictactoe.models.Board;
import tictactoe.models.Move;
import tictactoe.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    private Map<Integer, Map<Symbol,Integer>> countMap = new HashMap<>(); //map of maps
    //one map for each Row
    // we dont have board size fixed as 3x3 for eg
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!countMap.containsKey((row))){ // no symbol in that particular row
            countMap.put(row,new HashMap<>()); // add a hashmap for that row
        }
        //getting that particular row's hashmap
        Map<Symbol,Integer> rowMap = countMap.get(row);
        if (!rowMap.containsKey(symbol)){
            rowMap.put(symbol,0);
        }

        rowMap.put(symbol,rowMap.get(symbol) + 1);

        if(rowMap.get(symbol) == board.getSize()){
            return true;
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol,Integer> rowMap = countMap.get(row);
        rowMap.put(symbol,rowMap.get(symbol) - 1);
    }
}
