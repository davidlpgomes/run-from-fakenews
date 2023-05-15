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

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        return;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (x < 0)
            this.setValid(false);

        this.x = x;
        return;
    }

    public int getY() {
        return this.y;
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
