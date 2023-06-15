package game.entity;

public abstract class Entity {
    private Position position;

    // GETTERS
    public Position getPosition() {
        return this.position;
    }

    // SETTERS
    public void setPosition(Position position) {
        this.position = position;
    }

    // METHODS
    public abstract String getColor();

    public abstract String toString();
}
