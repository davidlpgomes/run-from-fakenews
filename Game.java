import java.util.Random;

public class Game {
    private static final int BOARD_SIZE = 9;
    private static final int NUMBER_OF_BARRIERS = 4;
    private static final int NUMBER_OF_ITEMS = 2;
    private static final int NUMBER_OF_FN_PER_TYPE = 2;

    private int turns;
    private Entity[][] board;

    public Game() {
        this.board = new Entity[this.BOARD_SIZE][this.BOARD_SIZE];
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
        Random random = new Random();
        Position pos;

        // Initialize players
        int middle = this.BOARD_SIZE / 2;

        this.board[0][middle] = new Player(new Position(0, middle));
        this.board[middle][8] = new Player(new Position(middle, 8));
        this.board[8][middle] = new Player(new Position(8, middle));
        this.board[middle][0] = new Player(new Position(middle, 0));

        // Initialize restrict sectors
        for (int i = 0; i < this.NUMBER_OF_BARRIERS; i++) {
            pos = getRandomEmptyPosition(0, this.BOARD_SIZE);
            this.board[pos.getX()][pos.getY()] = new Barrier(pos);
        }

        // Initialize items
        for (int i = 0; i < this.NUMBER_OF_ITEMS; i++) {
            pos = getRandomEmptyPosition(0, this.BOARD_SIZE);

            int k = random.nextInt(ItemType.values().length);
            ItemType type = ItemType.values()[k]; 

            this.board[pos.getX()][pos.getY()] = new Item(pos, type);
        }

        // Initialize fake news
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < NUMBER_OF_FN_PER_TYPE; j++) {
                pos = getRandomEmptyPosition(1, this.BOARD_SIZE - 1);
                FakeNewsType type = FakeNewsType.values()[i];

                this.board[pos.getX()][pos.getY()] = new FakeNews(pos, type);
            }
        }
    }

    public void print() {
        this.printBoardSeparator();

        for (int i = 0; i < this.BOARD_SIZE; i++) {
            System.out.print("|");

            for (int j = 0; j < this.BOARD_SIZE; j++) {
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

        for (int i = 0; i < this.BOARD_SIZE; i++) {
            System.out.print("----+");
        }

        System.out.println();

        return;
    }
}