package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;

public class FakeNewsTwoSquare extends FakeNews {
    public static final FakeNewsType type = FakeNewsType.TWO_SQUARE;

    public FakeNewsTwoSquare(Position position) {
        super(position);
    }

    public String toString() {
        return "F2";
    }

    public FakeNewsType getType() {
        return FakeNewsDiagonal.type;
    }

    public ArrayList<PossibleMove> getPossibleMoves(Board board){
        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        // North
        if (!board.isPosBarrier(x - 2, y))
            possibleMoves.add(new PossibleMove(x - 2, y, "Norte"));

        // South
        if (!board.isPosBarrier(x + 2, y))
            possibleMoves.add(new PossibleMove(x + 2, y, "Sul"));

        // West
        if (!board.isPosBarrier(x, y - 2))
            possibleMoves.add(new PossibleMove(x, y - 2, "Oeste"));

        // East
        if (!board.isPosBarrier(x, y + 2))
            possibleMoves.add(new PossibleMove(x, y + 2, "Leste"));

        return possibleMoves;
    }
}
