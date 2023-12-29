package tictactoe.models;

import java.util.Scanner;

public class Player {
 private Symbol symbol;
 private String name;
 private Long id;
 private PlayerType playerType;
 private Scanner scanner;

 public Player(Long id, String name,Symbol symbol,PlayerType playerType){
  this.id = id;
  this.name = name;
  this.symbol = symbol;
  this.playerType = playerType;
  this.scanner = new Scanner(System.in);
 }

 public Symbol getSymbol() {
  return symbol;
 }

 public void setSymbol(Symbol symbol) {
  this.symbol = symbol;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public PlayerType getPlayerType() {
  return playerType;
 }

 public void setPlayerType(PlayerType playerType) {
  this.playerType = playerType;
 }

 public Move makeMove(Board board){ //player is going to make the move...game should validate and update board
   System.out.println("Please enter row where you want to make the move");
   int row = scanner.nextInt();
   System.out.println("Please enter column where you want to make the move");
   int col = scanner.nextInt();

   return new Move(new Cell(row,col),this);
   //player will only return move

 }
}
