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

    public String getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    //Methods

    public ArrayList<Position> getPossibleMoves(Board board){

        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        
        //NORTE
        possibleMoves.add(new Position(this.getPosition().getX(),1));
        //SUL
        possibleMoves.add(new Position(this.getPosition().getX(),this.getPosition().getY()));
        //OESTE
        possibleMoves.add(new Position(this.getPosition().getX(),this.getPosition().getY()));
        //LESTE
        possibleMoves.add(new Position(this.getPosition().getX(),this.getPosition().getY()));

        return possibleMoves;
    }

    public String toString() {
        return "J" + this.id;
    }
}
