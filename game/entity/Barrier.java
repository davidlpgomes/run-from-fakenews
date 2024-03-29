package game.entity;

import game.utils.*;

public class Barrier extends Entity {
    private static final String color = Colors.ANSI_WHITE;

    public Barrier(Position position) {
        this.setPosition(position);
    }
    
    public String getColor() {
        return Barrier.color;
    }

    public String toString() {
        return "XX";
    }
}
