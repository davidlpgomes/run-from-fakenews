public class Player extends Entity {
    private static final String color = Colors.ANSI_GREEN;
    private static int lastId = 0;

    private int id;

    public Player(Position position) {
        this.setPosition(position);

        this.id = ++lastId;
    }

    public String getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "J" + this.id;
    }
}
