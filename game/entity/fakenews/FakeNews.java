package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;
import game.utils.*;

public abstract class FakeNews extends Entity {
    private static final String color = Colors.ANSI_RED;

    public FakeNews(Position position) {
        this.setPosition(position);
    }

    // GETTERS
    public String getColor() {
        return FakeNews.color;
    }

    //METHODS
    public abstract FakeNewsType getType();

    public abstract ArrayList<PossibleMove> getPossibleMoves(Board board);
}
