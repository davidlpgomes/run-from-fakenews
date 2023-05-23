package game.entity.item;

import game.entity.*;
import game.utils.*;

public abstract class Item extends Entity {
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

    public abstract ItemType getType();

    public abstract ItemMessage getMessage();
}
