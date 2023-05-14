package game.entity;

public class PossibleMove extends Position {
    private String direction;

    public PossibleMove(int x, int y, String direction) {
        super(x, y);
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
                break;
            default:
                return;
        }

        this.direction = direction;
        return;
    }
    
    public String toString(){
        return String.format("Vai para o %5s: (x:%d, y:%d)", this.direction, this.x, this.y);
    }
}
