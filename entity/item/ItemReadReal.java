package entity.item;

import entity.*;

public class ItemReadReal extends Item {
    public ItemReadReal(Position position) {
        super(position);
    }

    public String getName() {
        return "Ler notícia real";
    }
}
