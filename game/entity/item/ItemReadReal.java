package game.entity.item;

import game.entity.*;

public class ItemReadReal extends Item {
    public ItemReadReal(Position position) {
        super(position);
    }

    public ItemType getType() {
        return ItemType.READ_A_REAL_NEWS;
    }

    public ItemMessage getMessage() {
        return ItemMessage.READ_A_REAL_NEWS;
    }
}
