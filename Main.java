import game.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("[RFFN] Initializing game...");

        Game game = new Game();

        game.init();
        game.run();

        return;
    }
}
