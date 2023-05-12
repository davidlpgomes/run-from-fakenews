package entity.fakenews;

import entity.*;
import utils.*;

public abstract class FakeNews extends Entity {
    private static final String color = Colors.ANSI_RED;

    public FakeNews(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return this.color;
    }
}
