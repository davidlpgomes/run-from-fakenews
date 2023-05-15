package game;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import game.entity.*;
import game.entity.fakenews.*;
import game.entity.item.*;
import game.utils.*;

public class Game {
    private static final int BOARD_SIZE = 9;
    private static final int NUMBER_OF_BARRIERS = 4;
    private static final int NUMBER_OF_ITEMS = 2;
    private static final int NUMBER_OF_FN_PER_TYPE = 2;
    private static final int MAX_TURNS = 20;

    private Board board;
    private History history;
    private ArrayList<Player> playerList;
    private ArrayList<FakeNews> fakeNewsList;

    private Scanner scanner;

    private int turns;

    public Game() {
        this.turns = MAX_TURNS;
        this.board = new Board(Game.BOARD_SIZE);
        this.history = new History(Game.BOARD_SIZE);
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
            this.board.printBoard(this.history);

            ListIterator<Player> pItr = this.playerList.listIterator();
            while (pItr.hasNext()) {
                if(!this.movePlayer(pItr.next()))
                    pItr.remove();
            }

            ListIterator<FakeNews> fItr = this.fakeNewsList.listIterator();
            while (fItr.hasNext()) {
                if (!this.moveFakeNews(fItr.next()))
                    fItr.remove();
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

        System.out.println("--- Movendo jogador " + p.toString());
        int opt = -1;

        ArrayList<PossibleMove> possibleMoves = p.getPossibleMoves(this.board);

        if (possibleMoves.size() == 0) {
            System.out.println("Sem movimentos disponíveis...");
            return true;
        }

        if (playerHasHearRumor) {
            System.out.println("Jogador possui o item 'Ouvir um boato'," +
                    " movendo para posição aleatória...");

            Random random = new Random();
            opt = random.nextInt(possibleMoves.size());
        } else {
            for(int i = 0; i < possibleMoves.size(); i++)
                System.out.printf("%d) %5s\n", 
                    i + 1, possibleMoves.get(i).getDirection());

            System.out.println();

            while (opt < 1 || opt > possibleMoves.size()) {
                try {
                    System.out.print("Digite a opção: ");
                    opt = this.scanner.nextInt();
                    System.out.println();
                } catch (Exception e) {
                    System.out.println(
                        Colors.ANSI_RED + "Opção inválida!" +
                        Colors.ANSI_RESET);
                    this.scanner.nextLine();
                }
            }

            opt--;
        }

        PossibleMove move = possibleMoves.get(opt);

        this.history.add(
            String.format("%s: %s -> %s (%s)",
                p.toString(),
                this.board.getBoardCoordByPosition(p.getPosition()),
                this.board.getBoardCoordByPosition(move),
                move.getDirection()
            )
        );

        boolean status = this.movePlayerToNewPos(p, move);

        this.board.printBoard(this.history);

        return status;
    }

    private boolean movePlayerToNewPos(Player p, Position newPos) {
        Position pos = p.getPosition();

        Entity before = this.board.getPosition(newPos);
        System.out.println("Before is " + before);

        this.board.setPosition(pos);

        if (before == null) {
            p.setPosition(newPos);
            this.board.setPosition(p);
        } else if (before instanceof FakeNews) {
            System.out.printf(
                "Jogador %s se moveu a uma posição com Fake News e morreu!",
                p.toString()
            );

            Sleep.sleep(2);
            return false;
        } else if (before instanceof Item) {
            ArrayList<Item> playerItems = p.getItems();
            playerItems.add((Item) before);
            p.setItems(playerItems);

            p.setPosition(newPos);
            this.board.setPosition(p);

            this.createRandomItem();
        }

        return true;
    }

    private boolean moveFakeNews(FakeNews fn) {
    
        return true;
    }

    private void initializePlayers() {
        int last = this.board.getBoardSize() - 1;
        int middle = this.board.getBoardSize() / 2;

        this.playerList.add(new Player(new Position(0, middle)));
        this.playerList.add(new Player(new Position(middle, last)));
        this.playerList.add(new Player(new Position(last, middle)));
        this.playerList.add(new Player(new Position(middle, 0)));

        for(int i = 0; i < this.playerList.size(); i++)
          this.board.setPosition(playerList.get(i));

        return;
    }

    private void initializeFakeNews() {
        Position pos;

        int numberOfTypes = FakeNewsType.values().length;

        for (int i = 0; i < numberOfTypes; i++) {
            for (int j = 0; j < NUMBER_OF_FN_PER_TYPE; j++) {
                pos = this.board.getRandomEmptyPosition(
                    1,
                    this.board.getBoardSize() - 1
                );

                FakeNews fn = FakeNewsFactory.createFakeNews(
                    pos,
                    FakeNewsType.values()[i]
                );

                this.fakeNewsList.add(fn);
            }
        }

        for(int i = 0; i < this.fakeNewsList.size(); i++)
          this.board.setPosition(fakeNewsList.get(i));

        return;
    }

    private void initializeBarriers() {
        Position pos;

        for (int i = 0; i < Game.NUMBER_OF_BARRIERS; i++) {
            pos = this.board.getRandomEmptyPosition(
                    0, this.board.getBoardSize());
            this.board.setPosition(new Barrier(pos));
        }

        return;
    }

    private void createRandomItem() {
        Random random = new Random();

        Position pos = this.board.getRandomEmptyPosition(
                0, this.board.getBoardSize());

        ItemType type = ItemType.values()[random.nextInt(
                ItemType.values().length)]; 

        Item item = ItemFactory.createItem(pos, type);
        this.board.setPosition(item);

        return;
    }
    
    private void initializeItems() {
        for (int i = 0; i < Game.NUMBER_OF_ITEMS; i++)
            this.createRandomItem();

        return;
    }
}
