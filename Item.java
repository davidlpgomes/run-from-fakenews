public class Item extends Entity {
    private static final String color = Colors.ANSI_YELLOW;

    private ItemType type;

    public Item(Position position, ItemType type) {
        this.setPosition(position);
        this.setType(type);
    }

    public String getColor() {
        return Item.color;
    }

    public ItemType getType() {
        return this.type;
    }
    
    public void setType(ItemType type) {
        this.type = type;

        return;
    }

    public String toString() {
        return "??";
    }
}
