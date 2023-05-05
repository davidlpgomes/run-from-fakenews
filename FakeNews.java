public class FakeNews extends Entity {
    private static final String color = Colors.ANSI_RED;

    private FakeNewsType type;

    public FakeNews(Position position, FakeNewsType type) {
        this.setPosition(position);
        this.setType(type);
    }

    public String getColor() {
        return this.color;
    }

    public FakeNewsType getType() {
        return this.type;
    }

    public void setType(FakeNewsType type) {
        this.type = type;
    }

    public String toString() {
        return "F" + (this.getType().ordinal() + 1);
    }
}
