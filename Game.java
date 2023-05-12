import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import entity.*;
import entity.fakenews.*;
import entity.item.*;
import utils.*;

public class Game {
    private static final int BOARD_SIZE = 9;
    private static final int NUMBER_OF_BARRIERS = 4;
    private static final int NUMBER_OF_ITEMS = 2;
    private static final int NUMBER_OF_FN_PER_TYPE = 2;
    private static final int MAX_TURNS = 20;

    private Entity[][] board;
    private ArrayList<Player> playerList;
    private ArrayList<FakeNews> fakeNewsList;

    private Scanner scanner;

    private int turns;

    public Game() {
        this.turns = MAX_TURNS;
        this.board = new Entity[Game.BOARD_SIZE][Game.BOARD_SIZE];
        this.playerList = new ArrayList<Player>();
        this.fakeNewsList = new ArrayList<FakeNews>();

        this.scanner = new Scanner(System.in);
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

    public void init() {
        this.initializePlayers();
        this.initializeBarriers();
        this.initializeItems();
        this.initializeFakeNews();

        return;
    }

    public void run() {
        while (
            this.turns > 0 &&
            (this.playerList.size() > 0 || this.fakeNewsList.size() > 0)
        ) {
            this.print();

            ListIterator<Player> itr = this.playerList.listIterator();
            while (itr.hasNext()) {
                if(!this.movePlayer(itr.next()))
                    itr.remove();
            }


            this.turns--;
        }

        return;
    }

    private boolean movePlayer(Player p) {
        boolean playerHasHearRumor = false;

        if (p.getItems().size() > 0) {
            ListIterator<Item> itr = p.getItems().listIterator();
            while (itr.hasNext()) {
                if (itr.next() instanceof ItemHearRumor) {
                    playerHasHearRumor = true;
                    itr.remove();
                    break;
                }
            }
        }
   

        System.out.println("Movendo jogador " + p.toString());
        int opt = -1;

        if (playerHasHearRumor) {
            System.out.println("Jogador possui o item 'Ouvir um boato'," +
                    " movendo para posição aleatória...");

            Random random = new Random();
            opt = random.nextInt(1, 5);
        } else {
            System.out.println("1) Norte  2) Sul  3) Oeste  4) Leste");

            while (opt < 1 || opt > 4) {
                System.out.print("Digite a opção: ");
                opt = this.scanner.nextInt();
                System.out.println();
            }
        }

        Position pos = p.getPosition(), newPos;
        boolean status = true;

        if (opt == 1) {
            if (pos.getX() - 1 < 0) {
                // TODO: Fora do tabuleiro, jogador morre?
            } else {
                newPos = new Position(pos.getX() - 1, pos.getY());
                status = this.movePlayerToNewPos(p, newPos);
            }
        } else if (opt == 2) {
            if (pos.getX() + 1 >= this.BOARD_SIZE) {
                // TODO: Fora do tabuleiro, jogador morre?
            } else {
                newPos = new Position(pos.getX() + 1, pos.getY());
                status = this.movePlayerToNewPos(p, newPos);
            }
        } else if (opt == 3) {
            if (pos.getY() - 1 >= this.BOARD_SIZE) {
                // TODO: Fora do tabuleiro, jogador morre?
            } else {
                newPos = new Position(pos.getX(), pos.getY() - 1);
                status = this.movePlayerToNewPos(p, newPos);
            }
        } else if (opt == 4) {
            if (pos.getY() + 1 < 0) {
                // TODO: Fora do tabuleiro, jogador morre?
            } else {
                newPos = new Position(pos.getX(), pos.getY() + 1);
                status = this.movePlayerToNewPos(p, newPos);
            }
        }
               
        this.print();

        return status;
    }

    private boolean movePlayerToNewPos(Player p, Position newPos) {
        Position pos = p.getPosition();

        Entity before = this.board[newPos.getX()][newPos.getY()];
        System.out.println("Before is " + before);

        this.board[pos.getX()][pos.getY()] = null;

        if (before == null) {
            this.board[newPos.getX()][newPos.getY()] = p;
            p.setPosition(newPos);
        } else if (before instanceof FakeNews) {
            return false;
        } else if (before instanceof Item) {
            ArrayList<Item> playerItems = p.getItems();
            playerItems.add((Item) before);
            p.setItems(playerItems);

            this.board[newPos.getX()][newPos.getY()] = p;
            p.setPosition(newPos);

            this.createRandomItem();
        }

        return true;
    }

    private void initializePlayers() {
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

    private void initializeFakeNews() {
        FakeNewsType type;
        Position pos;

        int numberOfTypes = FakeNewsType.values().length;

        for (int i = 0; i < numberOfTypes; i++) {
            for (int j = 0; j < NUMBER_OF_FN_PER_TYPE; j++) {
                pos = getRandomEmptyPosition(1, Game.BOARD_SIZE - 1);
                type = FakeNewsType.values()[i];

                FakeNews fn = FakeNewsFactory.createFakeNews(pos, type);
                this.fakeNewsList.add(fn);
                this.board[pos.getX()][pos.getY()] = fn;
            }
        }

        return;
    }

    private void initializeBarriers() {
        Position pos;

        for (int i = 0; i < Game.NUMBER_OF_BARRIERS; i++) {
            pos = getRandomEmptyPosition(0, Game.BOARD_SIZE);
            this.board[pos.getX()][pos.getY()] = new Barrier(pos);
        }

        return;
    }

    private void createRandomItem() {
        Random random = new Random();
        Position pos = getRandomEmptyPosition(0, Game.BOARD_SIZE);

        int k = random.nextInt(ItemType.values().length);
        ItemType type = ItemType.values()[k]; 

        this.board[pos.getX()][pos.getY()] = ItemFactory.createItem(
            pos,
            type
        );

        return;
    }
    
    private void initializeItems() {
        for (int i = 0; i < Game.NUMBER_OF_ITEMS; i++) {
            this.createRandomItem();
        }

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
