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
    private static final int NUMBER_OF_ITEMS = 22;
    private static final int NUMBER_OF_FN_PER_TYPE = 3;
    private static final int MAX_TURNS = 20;

    private Board board;
    private History history;
    private ArrayList<Player> playerList;
    private ArrayList<FakeNews> fakeNewsList;
    private FakeNews tempFakeNews;

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

                Sleep.sleep(2);
                this.board.printBoard(this.history);
            }

            ListIterator<FakeNews> fItr = this.fakeNewsList.listIterator();
            while (fItr.hasNext()) {
                if (!this.moveFakeNews(fItr.next()))
                    fItr.remove();

                if (this.tempFakeNews != null) {
                    fItr.add(tempFakeNews);
                    this.tempFakeNews = null;
                }

                Sleep.sleep(3);
                this.board.printBoard(this.history);
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

        return this.movePlayerToNewPos(p, possibleMoves.get(opt));
    }

    private boolean movePlayerToNewPos(Player p, PossibleMove newPos) {
        Position pos = p.getPosition();
        Entity before = this.board.getPosition(newPos);

        this.board.setPosition(pos);

        if (before == null) {
            this.history.add(String.format("%s: %s -> %s (%s)",
                p.toString(),
                this.board.getBoardCoordByPosition(p),
                this.board.getBoardCoordByPosition(newPos),
                newPos.getDirection()
            ));

            p.setPosition(newPos);
            this.board.setPosition(p);

            return true;
        } else if (before instanceof FakeNews) {
            System.out.printf(
                "Jogador %s se moveu a uma posição com Fake News e morreu!",
                p.toString()
            );

            this.history.add(Colors.ANSI_RED + String.format("%s: %s -> %s (%s)",
                p.toString(),
                this.board.getBoardCoordByPosition(p),
                this.board.getBoardCoordByPosition(newPos),
                newPos.getDirection()
            ) + Colors.ANSI_RESET);

            Sleep.sleep(2);
            return false;
        } else if (before instanceof Item) {
            System.out.printf(
                "Jogador %s pegou um item!",
                p.toString()
            );

            System.out.println();

            this.history.add(Colors.ANSI_GREEN + String.format("%s: %s -> %s (%s)",
                p.toString(),
                this.board.getBoardCoordByPosition(p),
                this.board.getBoardCoordByPosition(newPos),
                newPos.getDirection()
            ) + Colors.ANSI_RESET);

            ArrayList<Item> playerItems = p.getItems();
            playerItems.add((Item) before);
            p.setItems(playerItems);

            if (before instanceof ItemHearRumor) {
                System.out.printf("Jogador %s pegou o item 'Ouvir um boato' e agora seu proximo movimento será aleatório", p.toString());
            } else if (before instanceof ItemReadReal) {
                System.out.printf("Jogador %s pegou o item 'Ler noticia real' e agora pode eliminar uma fake news", p.toString());
                System.out.println();
                System.out.println("Escolha uma fake news para eliminar:");

                int opt = -1;

                ListIterator<FakeNews> fItr = this.fakeNewsList.listIterator();

                while (fItr.hasNext()) {
                    FakeNews fakeNews = fItr.next();
                    System.out.printf("%d) %s [%s]\n", fItr.nextIndex(), fakeNews.toString(), this.board.getBoardCoordByPosition(fakeNews));
                }

                System.out.println();

                while (opt < 1 || opt > fakeNewsList.size()) {
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

                FakeNews fakeNews = this.fakeNewsList.get(opt - 1);

                this.board.setPosition(fakeNews.getPosition());
                this.fakeNewsList.remove(fakeNews);

                System.out.printf("Jogador %s eliminou a fake news %s\n", p.toString(), fakeNews.toString());

                this.history.add(Colors.ANSI_GREEN + String.format("%s: %s -> %s (%s)",
                    p.toString(),
                    this.board.getBoardCoordByPosition(p),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                ) + Colors.ANSI_RESET);

                Sleep.sleep(2);
            } else if (before instanceof ItemReport) {
                System.out.printf("Jogador %s pegou o item 'Denunciar Fake News' e agora pode eliminar qualquer fake news das 8 posições adjacentes", p.toString());
                System.out.println();
                int opt = -1;

                ArrayList<Position> adjacentPositions = this.board.getFakeNewsAdjFakeNews(p.getPosition());

                while ((opt < 1 || opt > adjacentPositions.size())) {
                    try {
                        if (adjacentPositions.size() == 0) {
                            System.out.println();
                            System.out.println(
                                Colors.ANSI_RED + "Não há Fake News" +
                                Colors.ANSI_RESET);

                            Sleep.sleep(2);
                            return true;
                        }
                        System.out.println("Escolha uma posição para eliminar a fake news:");
                        for(int i = 0; i < adjacentPositions.size(); i++)
                            System.out.printf("%d) %s\n", i + 1, this.board.getBoardCoordByPosition(adjacentPositions.get(i)));
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

                Position adjacentPosition = adjacentPositions.get(opt - 1);

                System.out.println("Posição escolhida: " + this.board.getBoardCoordByPosition(adjacentPosition));

                FakeNews fakeNews = (FakeNews) this.board.getPosition(adjacentPosition);


                this.board.setPosition(fakeNews.getPosition());
                this.fakeNewsList.remove(fakeNews);
                

                System.out.printf("Jogador %s eliminou a fake news %s\n", p.toString(), fakeNews.toString());

                this.history.add(Colors.ANSI_GREEN + String.format("%s: %s -> %s (%s)",
                    p.toString(),
                    this.board.getBoardCoordByPosition(p),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                ) + Colors.ANSI_RESET);

                Sleep.sleep(2);
            } else if (before instanceof ItemRun) {
                System.out.println("Jogador " + p.toString() + " pegou o item 'Correr' e agora pode se mover para qualquer posição do tabuleiro");
                System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");

                int row = 0;
                char column = ' ';
                while (true) {
                    row = this.scanner.nextInt();
                    column = this.scanner.next().charAt(0);
                    System.out.println();

                    if (column < 'A' || column > 'I' || row < 1 || row > 9) {
                        System.out.println(
                            Colors.ANSI_RED + "Posição inválida!" +
                            Colors.ANSI_RESET);
                        System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");
                        this.scanner.nextLine();
                        continue;
                    }

                    int columnInt = column - 'A' + 1;

                    System.out.printf("Jogador %s se moveu para a posição [%d:%s]", p.toString(), row, column);

                    // Se a posição escolhida for uma fake news, avisa o jogador
                    if (this.board.getPosition(new Position(8 - (row - 1), columnInt - 1)) instanceof FakeNews) {
                        System.out.println();
                        System.out.println(
                            Colors.ANSI_RED + "Posição inválida! A posição escolhida é uma fake news!" +
                            Colors.ANSI_RESET);
                        System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");
                        this.scanner.nextLine();
                        continue;
                    }
                    // Se a posição escolhida for um item, avisa o jogador
                    if (this.board.getPosition(new Position(8 - (row - 1), columnInt - 1)) instanceof Item) {
                        System.out.println();
                        System.out.println(
                            Colors.ANSI_RED + "Posição inválida! A posição escolhida é um item!" +
                            Colors.ANSI_RESET);
                        System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");
                        this.scanner.nextLine();
                        continue;
                    }
                    // Se a posição escolhida for um jogador, avisa o jogador
                    if (this.board.getPosition(new Position(8 - (row - 1), columnInt - 1)) instanceof Player) {
                        System.out.println();
                        System.out.println(
                            Colors.ANSI_RED + "Posição inválida! A posição escolhida é um jogador!" +
                            Colors.ANSI_RESET);
                        System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");
                        this.scanner.nextLine();
                        continue;
                    }
                    // Se a posição escolhida for uma barreira, avisa o jogador
                    if (this.board.getPosition(new Position(8 - (row - 1), columnInt - 1)) instanceof Barrier) {
                        System.out.println();
                        System.out.println(
                            Colors.ANSI_RED + "Posição inválida! A posição escolhida é uma barreira!" +
                            Colors.ANSI_RESET);
                        System.out.println("Linha (Número) e Coluna (Letra) separados por espaço (Ex: 1 A)");
                        this.scanner.nextLine();
                        continue;
                    }

                    p.setPosition(new Position(8 - (row - 1), columnInt - 1));
                    this.board.setPosition(p);

                    this.history.add(Colors.ANSI_GREEN + String.format("%s: %s -> %s (%s)",
                        p.toString(),
                        this.board.getBoardCoordByPosition(p),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    ) + Colors.ANSI_RESET);
                    return true;
                }
            }

            p.setPosition(newPos);
            this.board.setPosition(p);

            this.createRandomItem();

            return true;
        }

        return true;
    }

    private boolean moveFakeNews(FakeNews fn) {
        System.out.println("--- Movendo fake news " + fn.toString());
        int opt = -1;

        ArrayList<PossibleMove> possibleMoves = fn.getPossibleMoves(this.board);

        if (possibleMoves.size() == 0) {
            System.out.println("Sem movimentos disponíveis...");

            Sleep.sleep(2);
            return true;
        }

        Random random = new Random();
        opt = random.nextInt(possibleMoves.size());

        PossibleMove move = possibleMoves.get(opt);
        boolean status = true;

        if (
            move.isValid() &&
            move.getX() < this.board.getSize() &&
            move.getY() < this.board.getSize()
        ) {
            status = this.moveFakeNewsToNewPos(fn, move);
        } else {
            this.history.add(
                String.format("%s: %s -> fora do tabuleiro (%s)",
                    fn.toString(),
                    this.board.getBoardCoordByPosition(fn),
                    move.getDirection()
                )
            );

            System.out.printf(
                "Fake news %s se moveu para fora do tabuleiro e morreu!",
                fn.toString()
            );

            this.board.setPosition(fn.getPosition());
            status = false;
        }

        return status;
    }

    private boolean moveFakeNewsToNewPos(FakeNews fn, PossibleMove newPos) {
        boolean status = true;

        Position pos = fn.getPosition();
        Entity before = this.board.getPosition(newPos);

        this.board.setPosition(pos);

        if (before == null) {
            this.history.add(
                String.format("%s: %s -> %s (%s)",
                    fn.toString(),
                    this.board.getBoardCoordByPosition(fn),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                )
            );

            fn.setPosition(newPos);
            this.board.setPosition(fn);

            System.out.printf(
                "Fake news %s se moveu para %s.",
                fn.toString(),
                this.board.getBoardCoordByPosition(newPos)
            );
        } else if (before instanceof Player) {
            this.history.add(
                Colors.ANSI_RED + 
                String.format("%s: %s -> %s (%s)",
                    fn.toString(),
                    this.board.getBoardCoordByPosition(fn),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                ) 
                + Colors.ANSI_RESET
            );

            Player p = (Player) before;
            this.playerList.remove(p);

            fn.setPosition(newPos);
            this.board.setPosition(fn);

            System.out.printf(
                "Fake news %s se moveu a uma posição com o jogador %s, que morreu.",
                fn.toString(),
                p.toString()
            );
        } else if (before instanceof FakeNews) {
            status = false;

            this.history.add(
                String.format("%s: %s -> %s (%s)",
                    fn.toString(),
                    this.board.getBoardCoordByPosition(fn),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                )
            );

            System.out.printf(
                "Fake news %s se moveu a uma posição com outra fake news e morreu!",
                fn.toString()
            );
        } else if (before instanceof Item) {
            this.history.add(
                Colors.ANSI_YELLOW + 
                String.format("%s: %s -> %s (%s)",
                    fn.toString(),
                    this.board.getBoardCoordByPosition(fn),
                    this.board.getBoardCoordByPosition(newPos),
                    newPos.getDirection()
                ) 
                + Colors.ANSI_RESET
            );

            fn.setPosition(newPos);
            this.board.setPosition(fn);
            this.createRandomItem();

            FakeNewsType type = fn.getType();
            Position newFnPos = this.board.getRandomEmptyAdjacentPosition(
                newPos
            );

            FakeNews newFn = FakeNewsFactory.createFakeNews(newFnPos, type);
            this.board.setPosition(newFn);
            this.tempFakeNews = newFn;
        }

        return status;
    }

    private void initializePlayers() {
        int last = this.board.getSize() - 1;
        int middle = this.board.getSize() / 2;

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
                    this.board.getSize() - 1
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
                    0, this.board.getSize());
            this.board.setPosition(new Barrier(pos));
        }

        return;
    }

    private void createRandomItem() {
        Random random = new Random();

        Position pos = this.board.getRandomEmptyPosition(
                0, this.board.getSize());

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
