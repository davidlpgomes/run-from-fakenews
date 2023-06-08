import game.Game;
import game.Config;
import game.utils.Sleep;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            Config config = new Config(2);
            config.ConfigGame("medium");
        }
        for(int i = 0; i<args.length; i++) {
            if (args.length == 1){
                if (args[0].equals("easy")){
                    Config config = new Config(1);
                    config.ConfigGame("easy");
                }
                else if (args[0].equals("medium")){
                    Config config = new Config(2);
                    config.ConfigGame("medium");
                }
                else if (args[0].equals("hard")){
                    Config config = new Config(3);
                    config.ConfigGame("hard");
                }
                else if (args[0].equals("insane")){
                    Config config = new Config(4);
                    config.ConfigGame("insane");
                }
                else if (args[0].equals("impossible")){
                    Config config = new Config(5);
                    config.ConfigGame("impossible");
                }
                else if (args[0].equals("help")){
                    System.out.println("Usage: make ARGS=\"[difficulty]\"");
                    System.out.println("Difficulty levels:");
                    System.out.println("easy");
                    System.out.println("medium");
                    System.out.println("hard");
                    System.out.println("insane");
                    System.out.println("impossible");

                    Config config = new Config(2);
                    config.ConfigGame("medium");

                    Sleep.sleep(5);
                }
                else {
                    System.out.println("Usage: make ARGS=˜[difficulty]");
                    System.out.println("Difficulty levels:");
                    System.out.println("easy");
                    System.out.println("medium");
                    System.out.println("hard");
                    System.out.println("insane");
                    System.out.println("impossible");

                    Config config = new Config(2);
                    config.ConfigGame("medium");

                    Sleep.sleep(5);
                }
            } 
         }

        System.out.println("[RFFN] Initializing game...");

        Game game = new Game();

        game.init();
        game.run();

        return;
    }
}
