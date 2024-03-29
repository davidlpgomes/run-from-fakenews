package game;

import game.entity.*;
import game.entity.fakenews.*;
import game.entity.item.*;
import game.utils.*;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    private Entity[][] board;
    private int size;

    public Board(int size) {
        this.setSize(size);
        this.setBoard(new Entity[size][size]);
    }

    // GETTERS
    public Entity[][] getBoard() {
        return this.board;
    }

    public int getSize() {
        return this.size;
    }

    // SETTERS
    public void setBoard(Entity[][] board) {
        this.board = board;
        return;
    }

    public void setSize(int size) {
        if (size < 0) return;

        this.size = size;
        return;
    }

    // METHODS
    public Entity getPosition(Position position) {
        return this.board[position.getX()][position.getY()];
    }

    public Entity getPosition(int x, int y) {
        return this.board[x][y];
    }
    
    public void setPosition(Position position) {
        this.board[position.getX()][position.getY()] = null;
        return;
    }

    public void setPosition(int x, int y) {
        this.board[x][y] = null;
        return;
    }

    public void setPosition(Position position, Entity entity) {
        this.board[position.getX()][position.getY()] = entity;
        return;
    }

    public void setPosition(int x, int y, Entity entity) {
        this.board[x][y] = entity;
        return;
    }

    public void setPosition(Entity entity) {
        this.setPosition(entity.getPosition(), entity);
        return;
    }

    public Position getRandomEmptyPosition(int lowerBound, int upperBound) {
        Random random = new Random();

        int x = -1;
        int y = -1;

        boolean found = false;

        while (!found) {
            x = random.nextInt(upperBound - lowerBound) + lowerBound;
            y = random.nextInt(upperBound - lowerBound) + lowerBound;

            if (this.board[x][y] == null) found = true;
        }

        return new Position(x, y);
    }

    public Position getRandomEmptyAdjacentPosition(Position pos) {
        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        int x = pos.getX();
        int y = pos.getY();
        int s = this.getSize();

        // North
        if (x - 1 >= 0 && this.getPosition(x - 1, y) == null) 
            possiblePositions.add(new Position(x - 1, y));

        // South
        if (x + 1 < s && this.getPosition(x + 1, y) == null)
            possiblePositions.add(new Position(x + 1, y));

        // West
        if (y - 1 >= 0 && this.getPosition(x, y - 1) == null)
            possiblePositions.add(new Position(x, y - 1));

        // East
        if (y + 1 < s && this.getPosition(x, y + 1) == null)
            possiblePositions.add(new Position(x, y + 1));

        // Northwest
        if (x - 1 >= 0 && y - 1 >= 0 && this.getPosition(x - 1, y - 1) == null) 
            possiblePositions.add(new Position(x - 1, y - 1));

        // Northeast
        if (x - 1 >= 0 && y + 1 < s && this.getPosition(x - 1, y + 1) == null) 
            possiblePositions.add(new Position(x - 1, y + 1));

        // Southwest
        if (x + 1 < s && y - 1 >= 0 && this.getPosition(x + 1, y - 1) == null) 
            possiblePositions.add(new Position(x + 1, y - 1));

        // Southeast
        if (x + 1 < s && y + 1 < s && this.getPosition(x + 1, y + 1) == null) 
            possiblePositions.add(new Position(x + 1, y + 1));

        Random random = new Random();
        int i = random.nextInt(possiblePositions.size());

        return possiblePositions.get(i);
    }

    public ArrayList<Position> getFakeNewsAdjFakeNews(Position pos) {
        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        int x = pos.getX();
        int y = pos.getY();
        int s = this.getSize();

        // North
        if (x - 1 >= 0 && this.getPosition(x - 1, y) instanceof FakeNews) 
            possiblePositions.add(new Position(x - 1, y));

        // South
        if (x + 1 < s && this.getPosition(x + 1, y) instanceof FakeNews) 
            possiblePositions.add(new Position(x + 1, y));

        // West
        if (y - 1 >= 0 && this.getPosition(x, y - 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x, y - 1));

        // East
        if (y + 1 < s && this.getPosition(x, y + 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x, y + 1));

        // Northwest
        if (x - 1 >= 0 && y - 1 >= 0 && this.getPosition(x - 1, y - 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x - 1, y - 1));

        // Northeast
        if (x - 1 >= 0 && y + 1 < s && this.getPosition(x - 1, y + 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x - 1, y + 1));

        // Southwest
        if (x + 1 < s && y - 1 >= 0 && this.getPosition(x + 1, y - 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x + 1, y - 1));

        // Southeast
        if (x + 1 < s && y + 1 < s && this.getPosition(x + 1, y + 1) instanceof FakeNews) 
            possiblePositions.add(new Position(x + 1, y + 1));
        
        return possiblePositions;
    }

    public boolean isPosBarrier(int x, int y) {
        int s = this.getSize();

        boolean isBarrier = false;

        if (
            x >= 0 &&
            x < s &&
            y >= 0 &&
            y < s &&
            this.getPosition(x, y) instanceof Barrier
        ) isBarrier = true;

        return isBarrier;
    }

    public ArrayList<FakeNews> getFakeNewsList() {
        ArrayList<FakeNews> fakeNewsList = new ArrayList<FakeNews>();

        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                Entity entity = this.getPosition(i, j);

                if (entity instanceof FakeNews) fakeNewsList.add((FakeNews) entity);
            }
        }

        return fakeNewsList;
    }

    public FakeNews removeFakeNews(Position pos) {
        FakeNews fakeNews = (FakeNews) this.getPosition(pos);

        this.setPosition(pos);

        return fakeNews;
    }

    public boolean isPosBarrier(Position pos) {
        return this.isPosBarrier(pos.getX(), pos.getY());
    }

    public String getBoardCoordByPosition(Position pos) {
        char col = (char) (65 + pos.getY());
        int line = this.getSize() - pos.getX();

        return String.format("%c%d", col, line);
    }

    public String getBoardCoordByPosition(Entity entity) {
        return this.getBoardCoordByPosition(entity.getPosition());
    }

    public Position isPositionEmpty(String position) {
        if (position.length() != 2) return null;

        position = position.toUpperCase();

        char i = position.charAt(0), j = position.charAt(1);
        int x, y;

        if (Character.isAlphabetic(i) && Character.isDigit(j)) {
            // i eh coluna
            y = ((int) i) - 65;
            x = Character.getNumericValue(j);
        } else if (Character.isAlphabetic(j) && Character.isDigit(i)) {
            // j eh coluna
            y = ((int) j) - 65;
            x = Character.getNumericValue(i);
        } else return null;

        if (y < 0 || y > this.getSize() - 1) return null;
        if (x < 1 || x > this.getSize()) return null;

        // Reflecting
        x = this.getSize() - x;

        Entity e = this.getPosition(x, y);

        if (e instanceof FakeNews) {
            System.out.println();
            System.out.println(
                Colors.ANSI_RED +
                "Posição inválida! A posição escolhida é uma fake news!" +
                Colors.ANSI_RESET
            );
            return null;
        } else if (e instanceof Item) {
            System.out.println();
            System.out.println(
                Colors.ANSI_RED +
                "Posição inválida! A posição escolhida é um item!" +
                Colors.ANSI_RESET
            );
            return null;
        } else if (e instanceof Player) {
            System.out.println();
            System.out.println(
                Colors.ANSI_RED +
                "Posição inválida! A posição escolhida é um jogador!" +
                Colors.ANSI_RESET
            );
            return null;
        } else if (e instanceof Barrier) {
            System.out.println();
            System.out.println(
                Colors.ANSI_RED +
                "Posição inválida! A posição escolhida é uma barreira!" +
                Colors.ANSI_RESET
            );
            return null;
        }

        return new Position(x, y);
    }
}
