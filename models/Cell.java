package tictactoe.models;

public class Cell {
    private Player player;
    private CellState cellState;
    private int row;
    private int col;

    public Cell(int i,int j){
        this.row = i;
        this.col = j;
        this.cellState = CellState.EMPTY;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public void display(){
        if(player==null){
            System.out.print("|-|");
        }else {
            System.out.print("|"+player.getSymbol().getChar()+"|");
        }

    }
}
