package entity;

public abstract class Entity {
    private Position position;

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;

        return;
    }

    public abstract String getColor();

    public abstract String toString();
}
