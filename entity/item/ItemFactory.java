package entity.item;

import entity.*;

public class ItemFactory {
    public static Entity createItem(Position position, ItemType type){
        switch (type) {
            case RUN_AWAY:
                return new ItemRun(position);
            case REPORT_FAKE_NEWS:
                return new ItemReport(position);
            case READ_A_REAL_NEWS:
                return new ItemReadReal(position);
            case HEAR_A_RUMOR:
                return new ItemHearRumor(position);
            default:
                return null;
        }
    }
}
