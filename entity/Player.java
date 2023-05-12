package entity;

import java.util.ArrayList;

import entity.item.*;
import utils.*;

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

    public String toString() {
        return "J" + this.id;
    }
}
