package game.entity.item;

import game.entity.*;

public class ItemReport extends Item {
    public ItemReport(Position position) {
        super(position);
    }

    public ItemType getType() {
        return ItemType.REPORT_FAKE_NEWS;
    }

    public ItemMessage getMessage() {
        return ItemMessage.REPORT_FAKE_NEWS;
    }
}
