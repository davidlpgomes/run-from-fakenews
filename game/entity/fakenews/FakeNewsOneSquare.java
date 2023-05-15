package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;

public class FakeNewsOneSquare extends FakeNews {
    public static final FakeNewsType type = FakeNewsType.ONE_SQUARE;

    public FakeNewsOneSquare(Position position) {
        super(position);
    }

    public String toString() {
        return "F1";
    }

    public FakeNewsType getType() {
        return FakeNewsDiagonal.type;
    }

    public ArrayList<PossibleMove> getPossibleMoves(Board board) {
        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        // North
        if (!board.isPosBarrier(x - 1, y))
            possibleMoves.add(new PossibleMove(x - 1, y, "Norte"));

        // South
        if (!board.isPosBarrier(x + 1, y))
            possibleMoves.add(new PossibleMove(x + 1, y, "Sul"));

        // West
        if (!board.isPosBarrier(x, y - 1))
            possibleMoves.add(new PossibleMove(x, y - 1, "Oeste"));

        // East
        if (!board.isPosBarrier(x, y + 1))
            possibleMoves.add(new PossibleMove(x, y + 1, "Leste"));

        return possibleMoves;
    }
}
