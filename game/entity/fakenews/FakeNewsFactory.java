package game.entity.fakenews;

import game.entity.*;

public class FakeNewsFactory {
    public static FakeNews createFakeNews(Position position, FakeNewsType type) {
        switch (type) {
            case ONE_SQUARE:
                return new FakeNewsOneSquare(position);
            case TWO_SQUARE:
                return new FakeNewsTwoSquare(position);
            case DIAGONAL:
                return new FakeNewsDiagonal(position);
            default:
                return null;
        }
    }
}
