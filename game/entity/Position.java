package game.entity;

public class Position {
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (x < 0)
            return;

        this.x = x;
        return;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        if (y < 0)
            return;

        this.y = y;
        return;
    }

    public String toString(){
        return String.format("Position: (x:%d, y:%d)", this.x, this.y);
    }
}
