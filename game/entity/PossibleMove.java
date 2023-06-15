package game.entity;

public class PossibleMove extends Position {
    private String direction;

    public PossibleMove(int x, int y, String direction) {
        super(x, y);
        this.setDirection(direction);
    }

    public PossibleMove(Position position, String direction) {
        super(position.getX(), position.getY());
        this.setDirection(direction);
    }

    //GETTERS
    public String getDirection() {
        return this.direction;
    }

    //SETTERS
    public void setDirection(String direction) {
         switch (direction) {
            case "Norte":
            case "Sul":
            case "Leste":
            case "Oeste":
            case "Noroeste":
            case "Nordeste":
            case "Sudeste":
            case "Sudoeste":
            case "*Correr*":
                break;
            default:
                return;
        }

        this.direction = direction;
        return;
    }

    public String toString(){
        return String.format("(x:%d, y:%d) -> %5s", this.x, this.y, this.direction);
    }
}
