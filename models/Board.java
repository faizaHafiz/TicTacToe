package tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> board;

    //constructor - make constructors in every class to avoid nullPointerException
    public Board(int size){
        this.size = size;
        this.board = new ArrayList<>();

        for(int i=0;i<size;i++){
            board.add(new ArrayList<>()); // [[],[]]
            for(int j=0;j<size;j++){
                board.get(i).add(new Cell(i,j));
            }
        }
    }

    // Getters and setters
    public void setSize(int size) {
        this.size = size;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }
    public void printBoard(){
        for(List<Cell> row: board){
            for (Cell cell:row){
                cell.display();
            }
            System.out.println();
        }
    }
}
