package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;

public class FakeNewsDiagonal extends FakeNews {
    public FakeNewsDiagonal(Position position) {
        super(position);
    }

    public ArrayList<PossibleMove> getPossibleMoves(Board board){

        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        //NOROESTE
        if(x - 1 >= 0 && y - 1 >= 0 && !(board.getPosition(x - 1, y - 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 1, y, "Noroeste"));
        //NORDESTE
        if(x - 1 >= 0 && y + 1 < board.getBoardSize() && !(board.getPosition(x - 1, y + 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 1, y, "Nordeste"));
        //SUDESTE
        if(x + 1 < board.getBoardSize() && y + 1 < board.getBoardSize() && !(board.getPosition(x + 1, y + 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y + 1, "Sudeste"));
        //SUDOESTE
        if(x + 1 < board.getBoardSize() && y - 1 >= 0 && !(board.getPosition(x + 1, y - 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y - 1, "Sudoeste"));
        
        return possibleMoves;
    }

    public String toString() {
        return "F3";
    }
}
