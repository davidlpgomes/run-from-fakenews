package game.entity.item;

import game.entity.*;

public class ItemReadReal extends Item {
    public ItemReadReal(Position position) {
        super(position);
    }

    public String getName() {
        return "Ler notícia real";
    }
}
