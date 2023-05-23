package game.entity.item;

import game.entity.*;

public class ItemFactory {
    public static Item createItem(Position position, ItemType type){
        switch (type) {
            case RUN_AWAY:
                return new ItemReadReal(position);
            case REPORT_FAKE_NEWS:
                return new ItemReadReal(position);
            case READ_A_REAL_NEWS:
                return new ItemReadReal(position);
            case HEAR_A_RUMOR:
                return new ItemReadReal(position);
            default:
                return null;
        }
    }
}
