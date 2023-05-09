public class ItemReport extends Entity implements ItemInterface {
    private static final String color = Colors.ANSI_YELLOW;

    public ItemReport(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return ItemReport.color;
    }

    public String toString() {
        return "??";
    }
}
