package game.entity.item;

import game.entity.*;

public class ItemHearRumor extends Item {
    public ItemHearRumor(Position position) {
        super(position);
    }

    public ItemType getType() {
        return ItemType.HEAR_A_RUMOR;
    }

    public ItemMessage getMessage() {
        return ItemMessage.HEAR_A_RUMOR;
    }
}
