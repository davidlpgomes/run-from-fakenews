public class FakeNewsTwoSquare extends Entity implements FakeNewsInterface {
    private static final String color = Colors.ANSI_RED;

    public FakeNewsTwoSquare(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return FakeNewsTwoSquare.color;
    }

    public String toString() {
        return "F2";
    }
}
