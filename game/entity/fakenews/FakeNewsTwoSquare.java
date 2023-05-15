package game.entity.fakenews;

import java.util.ArrayList;

import game.*;
import game.entity.*;

public class FakeNewsTwoSquare extends FakeNews {
    public FakeNewsTwoSquare(Position position) {
        super(position);
    }

    public ArrayList<PossibleMove> getPossibleMoves(Board board){

        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        //NORTE
        if(x - 2 >= 0 && !(board.getPosition(x - 2, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 2, y, "Norte"));
        //SUL
        if(x + 2 < board.getBoardSize() && !(board.getPosition(x + 2, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x + 2, y, "Sul"));
        //LESTE
        if(y + 2 < board.getBoardSize() && !(board.getPosition(x, y + 2) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y + 2, "Leste"));
        //OESTE
        if(y - 2 >= 0 && !(board.getPosition(x, y - 2) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y - 2, "Oeste"));
        

        return possibleMoves;
    }

    public String toString() {
        return "F2";
    }
}
