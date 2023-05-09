public class ItemReadReal extends Entity implements ItemInterface {
    private static final String color = Colors.ANSI_YELLOW;

    public ItemReadReal(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return ItemReadReal.color;
    }

    public String toString() {
        return "??";
    }
}