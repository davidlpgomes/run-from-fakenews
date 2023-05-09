public class FakeNewsDiagonal extends Entity implements FakeNewsInterface {
    private static final String color = Colors.ANSI_RED;

    public FakeNewsDiagonal(Position position) {
        this.setPosition(position);
    }

    public String getColor() {
        return this.color;
    }

    public String toString() {
        return "F3";
    }
}
