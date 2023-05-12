import java.util.ArrayList;
import java.util.Random;

import entity.*;
import entity.fakenews.*;
import entity.item.*;
import utils.*;

public class Game {
    private static final int BOARD_SIZE = 9;
    private static final int NUMBER_OF_BARRIERS = 4;
    private static final int NUMBER_OF_ITEMS = 2;
    private static final int NUMBER_OF_FN_PER_TYPE = 2;

    private Entity[][] board;
    private ArrayList<Player> playerList;
    private ArrayList<FakeNews> fakeNewsList;

    private int turns;
    private int numberOfPlayers;
    private int numberOfFakeNews;

    public Game() {
        this.board = new Entity[Game.BOARD_SIZE][Game.BOARD_SIZE];
        this.playerList = new ArrayList<Player>();
        this.fakeNewsList = new ArrayList<FakeNews>();
    }

    public int getTurns() {
        return this.turns;
    }

    public void setTurns(int turns) {
        if (turns < 0)
            return;

        this.turns = turns;
        return;
    } 

    public Entity[][] getBoard() {
        return this.board;
    }

    public void setBoard(Entity[][] board) {
        this.board = board;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 0)
            return;

        this.numberOfPlayers = numberOfPlayers;
        return;
    }

    public int getNumberOfFakeNews() {
        return this.numberOfFakeNews;
    }

    public void setNumberOfFakeNews(int numberOfFakeNews) {
        if (numberOfFakeNews < 0)
            return;

        this.numberOfFakeNews = numberOfFakeNews;
        return;
    }

    private void initializePlayers() {
        this.setNumberOfPlayers(4);
        int last = Game.BOARD_SIZE - 1;
        int middle = Game.BOARD_SIZE / 2;

        Player p1 = new Player(new Position(0, middle));
        this.playerList.add(p1);
        this.board[0][middle] = p1;

        Player p2 = new Player(new Position(middle, last));
        this.playerList.add(p2);
        this.board[middle][last] = p2;

        Player p3 = new Player(new Position(last, middle));
        this.playerList.add(p3);
        this.board[last][middle] = p3;

        Player p4 = new Player(new Position(middle, 0));
        this.playerList.add(p4);
        this.board[middle][0] = p4;

        return;
    }

    public void init() {
        Random random = new Random();
        Position pos;

        this.initializePlayers();

        // Initialize restrict sectors
        for (int i = 0; i < Game.NUMBER_OF_BARRIERS; i++) {
            pos = getRandomEmptyPosition(0, Game.BOARD_SIZE);
            this.board[pos.getX()][pos.getY()] = new Barrier(pos);
        }

        // Initialize items
        for (int i = 0; i < Game.NUMBER_OF_ITEMS; i++) {
            pos = getRandomEmptyPosition(0, Game.BOARD_SIZE);

            int k = random.nextInt(ItemType.values().length);
            ItemType type = ItemType.values()[k]; 

            this.board[pos.getX()][pos.getY()] = ItemFactory.createItem(pos, type);
        }

        // Initialize fake news
        for (int i = 0; i < FakeNewsType.values().length; i++) {
            for (int j = 0; j < NUMBER_OF_FN_PER_TYPE; j++) {
                pos = getRandomEmptyPosition(1, Game.BOARD_SIZE - 1);
                FakeNewsType type = FakeNewsType.values()[i];

                FakeNews fn = FakeNewsFactory.createFakeNews(pos, type);
                this.fakeNewsList.add(fn);
                this.board[pos.getX()][pos.getY()] = fn;
            }
        }

        this.setNumberOfFakeNews(
            FakeNewsType.values().length * NUMBER_OF_FN_PER_TYPE
        );

        return;
    }

    public void print() {
        this.printBoardSeparator();

        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            System.out.print("|");

            for (int j = 0; j < Game.BOARD_SIZE; j++) {
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

            System.out.println();
            this.printBoardSeparator();
        }

        return;
    }

    public void run() {
        while (
            this.turns > 0 &&
            (this.numberOfPlayers > 0 || this.numberOfFakeNews > 0)
        ) {
             
        }
        
        return;
    }

    private Position getRandomEmptyPosition(int lowerBound, int upperBound) {
        Random random = new Random();

        int x = -1;
        int y = -1;

        boolean found = false;

        while (!found) {
            x = random.nextInt(upperBound - lowerBound) + lowerBound;
            y = random.nextInt(upperBound - lowerBound) + lowerBound;

            if (this.board[x][y] == null) {
                found = true;
            }
        }

        return new Position(x, y);
    }

    private void printBoardSeparator() {
        System.out.print("+");

        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            System.out.print("----+");
        }

        System.out.println();

        return;
    }
}
