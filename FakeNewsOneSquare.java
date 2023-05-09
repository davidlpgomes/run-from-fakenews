public class FakeNewsOneSquare extends Entity implements FakeNewsInterface {
    private static final String color = Colors.ANSI_RED;

    public FakeNewsOneSquare(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return FakeNewsOneSquare.color;
    }

    public String toString() {
        return "F1";
    }
}
