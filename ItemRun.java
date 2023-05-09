public class ItemRun extends Entity implements ItemInterface {
    private static final String color = Colors.ANSI_YELLOW;

    public ItemRun(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return ItemRun.color;
    }

    public String toString() {
        return "??";
    }
}
