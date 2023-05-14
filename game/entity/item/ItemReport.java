package game.entity.item;

import game.entity.*;

public class ItemReport extends Item {
    public ItemReport(Position position) {
        super(position);
    }

    public String getName() {
        return "Denunciar fake news";
    }
}
