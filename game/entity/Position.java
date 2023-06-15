package game.entity;

public class Position {
    protected boolean valid;
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.setValid(true);
        this.setX(x);
        this.setY(y);
    }

    // GETTERS
    public boolean isValid() {
        return this.valid;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // SETTERS
    public void setValid(boolean valid) {
        this.valid = valid;
        return;
    }

    public void setX(int x) {
        if (x < 0)
            this.setValid(false);

        this.x = x;
        return;
    }

    public void setY(int y) {
        if (y < 0)
            this.setValid(false);

        this.y = y;
        return;
    }

    public String toString(){
        return String.format("Position: (x:%d, y:%d)", this.x, this.y);
    }
}
