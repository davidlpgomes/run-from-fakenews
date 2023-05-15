package game.entity;

import java.util.ArrayList;

import game.*;

public abstract class EntityMovable extends Entity {

    public ArrayList<PossibleMove> getPossibleMoves(Board board){

        ArrayList<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        //NORTE
        if(x - 1 >= 0 && !(board.getPosition(x - 1, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x - 1, y, "Norte"));
        //SUL
        if(x + 1 < board.getBoardSize() && !(board.getPosition(x + 1, y) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x + 1, y, "Sul"));
        //LESTE
        if(y + 1 < board.getBoardSize() && !(board.getPosition(x, y + 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y + 1, "Leste"));
        //OESTE
        if(y - 1 >= 0 && !(board.getPosition(x, y - 1) instanceof Barrier))
            possibleMoves.add(new PossibleMove(x, y - 1, "Oeste"));
        

        return possibleMoves;
    }
}
