package game.entity.item;

import game.entity.*;

public class ItemRun extends Item {
    public ItemRun(Position position) {
        super(position);
    }

    public ItemType getType() {
        return ItemType.RUN_AWAY;
    }

    public ItemMessage getMessage() {
        return ItemMessage.RUN_AWAY;
    }
}
