package game.entity;

import java.util.ArrayList;

import game.*;

public abstract class EntityMovable extends Entity {
    public ArrayList<PossibleMove> getPossibleMoves(Board board) {

        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        // North
        if (x - 1 >= 0 && !(board.getPosition(x - 1, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 1, y, "Norte"));

        // South
        if (
            x + 1 < board.getBoardSize() &&
            !(board.getPosition(x + 1, y) instanceof Barrier)
        )
            possibleMoves.add(new PossibleMove(x + 1, y, "Sul"));

        // West
        if (y - 1 >= 0 && !(board.getPosition(x, y - 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y - 1, "Oeste"));

        // East
        if (
            y + 1 < board.getBoardSize() &&
            !(board.getPosition(x, y + 1) instanceof Barrier)
        )
            possibleMoves.add(new PossibleMove(x, y + 1, "Leste"));

        return possibleMoves;
    }
}
