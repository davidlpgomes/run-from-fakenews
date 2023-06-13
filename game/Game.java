package game;

import game.entity.*;
import game.entity.fakenews.*;
import game.entity.item.*;
import game.utils.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int BOARD_SIZE = 9;
    private static final int NUMBER_OF_BARRIERS = 4;

    private Board board;
    private History history;
    private ArrayList<Player> playerList;
    private ArrayList<FakeNews> fakeNewsList;
    private FakeNews tempFakeNews;

    private Scanner scanner;

    private int turns;

    public Game() {
        int maxTurns = Config.getMaxTurns();

        this.turns = maxTurns;
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
        if (turns < 0) return;

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
        boolean win = false, lost = false;

        while (!win && !lost) {
            this.printGameState();

            ListIterator<Player> pItr = this.playerList.listIterator();
            while (pItr.hasNext()) {
                if (!this.movePlayer(pItr.next())) pItr.remove();

                Sleep.sleep(2);
                this.printGameState();
            }

            ListIterator<FakeNews> fItr = this.fakeNewsList.listIterator();
            while (fItr.hasNext()) {
                if (!this.moveFakeNews(fItr.next())) fItr.remove();

                if (this.tempFakeNews != null) {
                    fItr.add(tempFakeNews);
                    this.tempFakeNews = null;
                }

                Sleep.sleep(3);
                this.printGameState();
            }

            if (this.fakeNewsList.size() <= 0) win = true;

            if (this.playerList.size() <= 0 || this.turns <= 0) lost = true;

            this.turns--;
        }

        System.out.println();
        // Se 0 fakenews, ganhou
        if (win) System.out.println(
            Colors.ANSI_GREEN + "Parabens, voce ganhou!!" + Colors.ANSI_RESET
        );

        // Se 0 players ou 0 turnos perdeu
        if (lost) System.out.println(
            Colors.ANSI_RED + "Voce perdeu :(. Tente novamente." + Colors.ANSI_RESET
            );
        
        System.out.println();
        return;
    }

    private boolean movePlayer(Player p) {
        boolean playerHasHearRumor = false;
        int opt = -1;

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

        //Checa que o jogador possui itens e questiona se os deseja usar.
        if (!playerHasHearRumor && p.getItems().size() > 0) {
            int c = 0;
            int iOpt = -1;

            System.out.printf(
                "Jogador " + p.toString() + " possui itens (" +
                p.getItems().size() + ") no inventario. Deseja usar?\n"
            );

            System.out.println("0) Nao usar itens.");

            ListIterator<Item> itr = p.getItems().listIterator();
            while (itr.hasNext()) {
                System.out.printf("%d) %s.\n", ++c, itr.next().getMessage().toString());
            }

            System.out.println("");
            System.out.println("");

            //Entrada da opcao do usuario
            while (iOpt < 0 || iOpt > p.getItems().size()) {
                try {
                    System.out.print("Digite a opção: ");
                    iOpt = this.scanner.nextInt();
                    System.out.println();
                } catch (Exception e) {
                    System.out.println(
                        Colors.ANSI_RED + "Opção inválida!" + Colors.ANSI_RESET
                    );
                    this.scanner.nextLine();
                }
            }

            if (iOpt > 0) {
                // Usa o item (opt-1)
                Item selectedItem = p.getItems().get(--iOpt);

                if (selectedItem instanceof ItemReadReal) {
                    opt = -1;
                    System.out.println("Escolha uma fake news para eliminar:");

                    ListIterator<FakeNews> fItr = this.fakeNewsList.listIterator();

                    while (fItr.hasNext()) {
                        FakeNews fakeNews = fItr.next();
                        System.out.printf(
                            "%d) %s [%s]\n",
                            fItr.nextIndex(),
                            fakeNews.toString(),
                            this.board.getBoardCoordByPosition(fakeNews)
                        );
                    }

                    System.out.println();

                    while (opt < 1 || opt > fakeNewsList.size()) {
                        try {
                            System.out.print("Digite a opção: ");
                            opt = this.scanner.nextInt();
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println(
                                Colors.ANSI_RED + "Opção inválida!" + Colors.ANSI_RESET
                            );
                            this.scanner.nextLine();
                        }
                    }

                    FakeNews fakeNews = this.fakeNewsList.get(opt - 1);

                    this.board.setPosition(fakeNews.getPosition());
                    this.fakeNewsList.remove(fakeNews);

                    System.out.println(
                        Colors.ANSI_GREEN +
                        String.format(
                            "Jogador %s eliminou a fake news %s",
                            p.toString(),
                            fakeNews.toString()
                        ) +
                        Colors.ANSI_RESET
                    );

                    this.history.add(
                            Colors.ANSI_GREEN +
                            String.format(
                                "%s eliminou %s",
                                p.toString(),
                                fakeNews.toString()
                            ) +
                            Colors.ANSI_RESET
                        );

                    Sleep.sleep(2);
                } else if (selectedItem instanceof ItemReport) {
                    opt = -1;

                    ArrayList<Position> adjacentPositions =
                        this.board.getFakeNewsAdjFakeNews(p.getPosition());

                    while ((opt < 1 || opt > adjacentPositions.size())) {
                        try {
                            if (adjacentPositions.size() == 0) {
                                System.out.println();
                                System.out.println(
                                        Colors.ANSI_RED + "Não há Fake News" + Colors.ANSI_RESET);

                                Sleep.sleep(2);
                                return true;
                            }
                            
                            System.out.println("Escolha uma posição para eliminar a fake news:");
                            
                            for (int i = 0; i < adjacentPositions.size(); i++)
                                System.out.printf("%d) %s\n", i + 1, this.board.getBoardCoordByPosition(adjacentPositions.get(i)));
                                
                            System.out.print("Digite a opção: ");
                            opt = this.scanner.nextInt();
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println(
                                Colors.ANSI_RED + "Opção inválida!" + Colors.ANSI_RESET
                            );
                            this.scanner.nextLine();
                        }
                    }

                    Position adjacentPosition = adjacentPositions.get(opt - 1);

                    System.out.println("Posição escolhida: " + this.board.getBoardCoordByPosition(adjacentPosition));

                    FakeNews fakeNews = (FakeNews) this.board.getPosition(adjacentPosition);

                    this.board.setPosition(fakeNews.getPosition());
                    this.fakeNewsList.remove(fakeNews);

                    System.out.printf(
                        "Jogador %s eliminou a fake news %s\n",
                        p.toString(),
                        fakeNews.toString()
                    );

                    this.history.add(
                            Colors.ANSI_GREEN +
                            String.format(
                                "%s eliminou %s",
                                p.toString(),
                                fakeNews.toString()
                            ) +
                            Colors.ANSI_RESET
                        );

                    Sleep.sleep(2);
                } else if (selectedItem instanceof ItemRun) {
                    System.out.println("Jogador " + p.toString() + " usou o item 'Correr' e agora pode se mover para qualquer posição do tabuleiro");

                    Position emptyPos = null;
                    String sPosition = "";

                    do {
                        System.out.print("Digite a linha e a coluna (ex: 1A):");
                        sPosition = this.scanner.next();
                        emptyPos = this.board.isPositionEmpty(sPosition);
                    } while (emptyPos == null);

                    Position oldPos = p.getPosition();

                    PossibleMove newPos = new PossibleMove(emptyPos, "*Correr*");
                    p.setPosition(newPos);
                    this.board.setPosition(p);
                    this.board.setPosition(oldPos);

                    this.history.add(
                            String.format(
                                "%s: %s -> %s (%s)",
                                p.toString(),
                                this.board.getBoardCoordByPosition(oldPos),
                                this.board.getBoardCoordByPosition(newPos),
                                newPos.getDirection()
                            )
                        );
                }
                //Remove o item do inventario.            
                p.getItems().remove(iOpt);
            }

            this.printGameState();
        }

        opt = -1;
        System.out.println("--- Movendo jogador " + p.toString());

        ArrayList<PossibleMove> possibleMoves = p.getPossibleMoves(this.board);

        if (possibleMoves.size() == 0) {
            System.out.println("Sem movimentos disponíveis...");
            return true;
        }

        if (playerHasHearRumor) {
            System.out.println(
                "Jogador possui o item 'Ouvir um boato'," +
                " movendo para posição aleatória..."
            );

            Random random = new Random();
            opt = random.nextInt(possibleMoves.size());
        } else {
            for (int i = 0; i < possibleMoves.size(); i++) System.out.printf(
                "%d) %5s\n",
                i + 1,
                possibleMoves.get(i).getDirection()
            );

            System.out.println();

            while (opt < 1 || opt > possibleMoves.size()) {
                try {
                    System.out.print("Digite a opção: ");
                    opt = this.scanner.nextInt();
                    System.out.println();
                } catch (Exception e) {
                    System.out.println(
                        Colors.ANSI_RED + "Opção inválida!" + Colors.ANSI_RESET
                    );
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
            this.history.add(
                    String.format(
                        "%s: %s -> %s (%s)",
                        p.toString(),
                        this.board.getBoardCoordByPosition(p),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    )
                );

            p.setPosition(newPos);
            this.board.setPosition(p);

            return true;
        } else if (before instanceof FakeNews) {
            System.out.printf(
                "Jogador %s se moveu a uma posição com Fake News e morreu!",
                p.toString()
            );

            this.history.add(
                    Colors.ANSI_RED +
                    String.format(
                        "%s: %s -> %s (%s)",
                        p.toString(),
                        this.board.getBoardCoordByPosition(p),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    ) +
                    Colors.ANSI_RESET
                );

            Sleep.sleep(2);
            return false;
        } else if (before instanceof Item) {
            this.history.add(
                    Colors.ANSI_GREEN +
                    String.format(
                        "%s: %s -> %s (%s)",
                        p.toString(),
                        this.board.getBoardCoordByPosition(p),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    ) +
                    Colors.ANSI_RESET
                );

            ArrayList<Item> playerItems = p.getItems();
            playerItems.add((Item) before);
            p.setItems(playerItems);

            if (before instanceof ItemHearRumor) {
                System.out.printf(
                    "Jogador %s pegou o item 'Ouvir um boato' e agora seu proximo movimento será aleatório",
                    p.toString()
                );
                Sleep.sleep(2);
            } else if (before instanceof ItemReadReal) {
                System.out.printf(
                    "Jogador %s pegou o item 'Ler noticia real' e agora pode eliminar uma fake news",
                    p.toString()
                );
                Sleep.sleep(2);
            } else if (before instanceof ItemReport) {
                System.out.printf(
                    "Jogador %s pegou o item 'Denunciar Fake News' e agora pode eliminar qualquer fake news das 8 posições adjacentes",
                    p.toString()
                );
                Sleep.sleep(2);
            } else if (before instanceof ItemRun) {
                System.out.printf(
                    "Jogador %s pegou o item 'Correr' e agora pode se mover para qualquer posição do tabuleiro",
                    p.toString()
                );
                Sleep.sleep(2);
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
                    String.format(
                        "%s: %s -> fora do tabuleiro (%s)",
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
                    String.format(
                        "%s: %s -> %s (%s)",
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
                    String.format(
                        "%s: %s -> %s (%s)",
                        fn.toString(),
                        this.board.getBoardCoordByPosition(fn),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    ) +
                    Colors.ANSI_RESET
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
                    String.format(
                        "%s: %s -> %s (%s)",
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
                    String.format(
                        "%s: %s -> %s (%s)",
                        fn.toString(),
                        this.board.getBoardCoordByPosition(fn),
                        this.board.getBoardCoordByPosition(newPos),
                        newPos.getDirection()
                    ) +
                    Colors.ANSI_RESET
                );

            fn.setPosition(newPos);
            this.board.setPosition(fn);
            this.createRandomItem();

            FakeNewsType type = fn.getType();
            Position newFnPos = this.board.getRandomEmptyAdjacentPosition(newPos);

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

        for (int i = 0; i < this.playerList.size(); i++)
            this.board.setPosition(
                    playerList.get(i));

        return;
    }
    
    /**
     * Initialize fake News
     * 
     * @return void
     * 
     */
    private void initializeFakeNews() {
        Position pos;

        int number = Config.getNumTypePerFakeNews();

        int numberOfTypes = FakeNewsType.values().length;

        for (int i = 0; i < numberOfTypes; i++) {
            for (int j = 0; j < number; j++) {
                pos = this.board.getRandomEmptyPosition(1, this.board.getSize() - 1);

                FakeNews fn = FakeNewsFactory.createFakeNews(
                    pos,
                    FakeNewsType.values()[i]
                );

                this.fakeNewsList.add(fn);
            }
        }

        for (int i = 0; i < this.fakeNewsList.size(); i++) this.board.setPosition(
                fakeNewsList.get(i)
            );

        return;
    }

    private void initializeBarriers() {
        Position pos;

        for (int i = 0; i < Game.NUMBER_OF_BARRIERS; i++) {
            pos = this.board.getRandomEmptyPosition(0, this.board.getSize());
            this.board.setPosition(new Barrier(pos));
        }

        return;
    }

    private void createRandomItem() {
        Random random = new Random();

        Position pos = this.board.getRandomEmptyPosition(0, this.board.getSize());

        ItemType type = ItemType.values()[random.nextInt(ItemType.values().length)];

        Item item = ItemFactory.createItem(pos, type);
        this.board.setPosition(item);

        return;
    }

    private void initializeItems() {
        int number = Config.getNumItems();

        for (int i = 0; i < number; i++)
            this.createRandomItem();

        return;
    }

    // Impressao do estado do jogo
    private void printBoardSeparator() {
        System.out.print("  +");

        for (int i = 0; i < this.board.getSize(); i++) {
            System.out.print("----+");
        }

        System.out.println();

        return;
    }

    private void refreshScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printGameState() {
        this.refreshScreen();

        // Imprime turnos
        System.out.printf("Turnos: %2d/%2d\n", (Config.getMaxTurns() - this.turns) + 1, Config.getMaxTurns());

        this.printBoardSeparator();

        for (int i = 0; i < this.board.getSize(); i++) {
            System.out.printf("%d |", this.board.getSize() - i);

            for (int j = 0; j < this.board.getSize(); j++) {
                if (this.board.getPosition(i, j) != null) {
                System.out.print(
                    " " +
                    this.board.getPosition(i, j).getColor() +
                    this.board.getPosition(i, j).toString() +
                    Colors.ANSI_RESET +
                    " |"
                );
                } else {
                    System.out.print("    |");
                }
            }

            // Prints board's history
            if (history.get(i) != null)
                System.out.printf("  ● %s", this.history.get(i));

            System.out.println();
            this.printBoardSeparator();
        }

        System.out.print("    ");
        for (int i = 65; i < this.board.getSize() + 65; i++)
            System.out.printf("%c    ", i);

        System.out.println("\n");
    }
}
