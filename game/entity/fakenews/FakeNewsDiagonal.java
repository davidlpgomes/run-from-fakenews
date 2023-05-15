package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;

public class FakeNewsDiagonal extends FakeNews {
    public static final FakeNewsType type = FakeNewsType.DIAGONAL;

    public FakeNewsDiagonal(Position position) {
        super(position);
    }

    public String toString() {
        return "F3";
    }

    public FakeNewsType getType() {
        return FakeNewsDiagonal.type;
    }

    public ArrayList<PossibleMove> getPossibleMoves(Board board){
        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        // Northwest
        if (!board.isPosBarrier(x - 1, y - 1))
            possibleMoves.add(new PossibleMove(x - 1, y - 1, "Noroeste"));

        // Northeast
        if (!board.isPosBarrier(x - 1, y + 1))
            possibleMoves.add(new PossibleMove(x - 1, y + 1, "Nordeste"));

        // Southwest
        if (!board.isPosBarrier(x + 1, y - 1))
            possibleMoves.add(new PossibleMove(x + 1, y - 1, "Sudoeste"));

        // Southeast
        if (!board.isPosBarrier(x + 1, y + 1))
            possibleMoves.add(new PossibleMove(x + 1, y + 1, "Sudeste"));
        
        return possibleMoves;
    }
}
