package game.entity;

import java.util.ArrayList;

import game.entity.item.*;
import game.utils.*;
import game.*;

public class Player extends Entity {
    private static final String color = Colors.ANSI_GREEN;
    private static int lastId = 0;
    private int id;
    private ArrayList<Item> items;

    public Player(Position position) {
        this.setPosition(position);
        this.id = ++lastId;
        this.items = new ArrayList<Item>();
    }

    // GETTERS
    public String getColor() {
        return Player.color;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    // SETTERS
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    //METHODS
    public ArrayList<PossibleMove> getPossibleMoves(Board board) {
        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int s = board.getSize();
        
        // North
        if (x - 1 >= 0 && !(board.getPosition(x - 1, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 1, y, "Norte"));

        // South
        if (x + 1 < s && !(board.getPosition(x + 1, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x + 1, y, "Sul"));

        // West
        if (y - 1 >= 0 && !(board.getPosition(x, y - 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y - 1, "Oeste"));

        // East
        if (y + 1 < s && !(board.getPosition(x, y + 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y + 1, "Leste"));

        return possibleMoves;
    }

    public String toString() {
        return "J" + this.id;
    }
}
