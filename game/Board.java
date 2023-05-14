package game;

import java.util.Random;

import game.entity.*;
import game.entity.fakenews.*;
import game.entity.item.*;
import game.utils.*;

public class Board{

    private Entity[][] board;
    private int boardSize;

    public Board(int boardSize){
        this.setBoardSize(boardSize);
        this.setBoard(new Entity[boardSize][boardSize]);
    }

    // GETTERS 
    public Entity[][] getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    // SETTERS 
    public void setBoard(Entity[][] board) {
        this.board = board;
    }

    public void setBoardSize(int boardSize) {
        if(boardSize < 0)
            return;

        this.boardSize = boardSize;
    }

    // METHODS
    //getPosition 
    public Entity getPosition(Position position){
        return this.board[position.getX()][position.getY()];
    }

    public Entity getPosition(int x, int y){
        return this.board[x][y];
    }

    //setPosition 
    public void setPosition(Position position){
        this.board[position.getX()][position.getY()] = null;
    }

    public void setPosition(int x, int y){
        this.board[x][y] = null;
    }

    public void setPosition(Position position, Entity entity){
        this.board[position.getX()][position.getY()] = entity;
    }

    public void setPosition(int x, int y, Entity entity){
        this.board[x][y] = entity;
    }

    public void setPosition(Entity entity){
        this.board[entity.getPosition().getX()][entity.getPosition().getY()] = entity;
    }

    // getRandomPos
    public Position getRandomEmptyPosition(int margin) {
        Random random = new Random();

        int x = -1;
        int y = -1;

        boolean found = false;

        while (!found) {
            x = random.nextInt(this.getBoardSize() - margin) + margin;
            y = random.nextInt(this.getBoardSize() - margin) + margin;

            if (this.board[x][y] == null)
                found = true;
        }

        return new Position(x, y);
    }

    // print Board
    private void printBoardSeparator() {
        System.out.print("+");

        for (int i = 0; i < this.boardSize; i++) {
            System.out.print("----+");
        }

        System.out.println();

        return;
    }

    private void refreshScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printBoard(History history){
        this.refreshScreen();

        this.printBoardSeparator();

        for (int i = 0; i < this.boardSize; i++) {
            System.out.print("|");

            for (int j = 0; j < this.boardSize; j++) {
                if (this.board[i][j] != null) {
                    System.out.print(
                        " " + 
                        this.board[i][j].getColor() +
                        this.board[i][j].toString() +
                        Colors.ANSI_RESET +
                        " |"
                    );
                } else {
                    System.out.print("    |");
                }
            }

            //PRINT HISTORY
            if(history.get(i) != null)
                System.out.printf("  ● %s", history.get(i));

            System.out.println();
            this.printBoardSeparator();
        }

        return;
    }

}