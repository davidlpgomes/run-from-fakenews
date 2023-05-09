public class ItemHearRumor extends Entity implements ItemInterface {
    private static final String color = Colors.ANSI_YELLOW;

    public ItemHearRumor(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return ItemHearRumor.color;
    }

    public String toString() {
        return "??";
    }
}
