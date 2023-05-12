package entity.item;

import entity.*;
import utils.*;

public class Item extends Entity {
    private static final String color = Colors.ANSI_YELLOW;

    public Item(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return Item.color;
    }

    public String toString() {
        return "??";
    }
}
