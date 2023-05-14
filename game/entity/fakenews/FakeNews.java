package game.entity.fakenews;

import game.entity.*;
import game.utils.*;

public abstract class FakeNews extends Entity {
    private static final String color = Colors.ANSI_RED;

    public FakeNews(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return this.color;
    }
}
