package entity;

import utils.*;

public class Barrier extends Entity {
    private static final String color = Colors.ANSI_WHITE;

    public Barrier(Position position) {
        this.setPosition(position);
    }
    
    public String getColor() {
        return this.color;
    }

    public String toString() {
        return "XX";
    }
}
