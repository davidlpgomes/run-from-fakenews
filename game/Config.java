package game;

public class Config {
    private int difficulty;
    private int numFakeNews;
    private static int numTypePerFakeNews;
    private static int numItems;
    private static int maxTurns;

    public Config(int difficulty) {
        this.difficulty = difficulty;
        this.numFakeNews = 0;
        Config.numTypePerFakeNews = 0;
        Config.numItems = 0;
        Config.maxTurns = 0;
    }

    public int getDifficulty() {
        return this.difficulty;
    }


    public int getNumFakeNews() {
        return this.numFakeNews;
    }

    public static int getNumTypePerFakeNews() {
        return numTypePerFakeNews;
    }

    public static int getNumItems() {
        return numItems;
    }

    public static int getMaxTurns() {
        return maxTurns;
    }

    public void setNumFakeNews(int numFakeNews) {
        this.numFakeNews = numFakeNews;
    }

    public void setNumTypePerFakeNews(int numTypePerFakeNews) {
        Config.numTypePerFakeNews = numTypePerFakeNews;
    }

    public void setNumItems(int numItems) {
        Config.numItems = numItems;
    }

    public void setMaxTurns(int maxTurns) {
        Config.maxTurns = maxTurns;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void ConfigGame(String difficulty) {
        if (difficulty == "easy") {
            this.setNumFakeNews(2);
            this.setNumTypePerFakeNews(2);
            this.setNumItems(5);
            this.setMaxTurns(20);
        } else if (difficulty == "medium") {
            this.setNumFakeNews(3);
            this.setNumTypePerFakeNews(2);
            this.setNumItems(2);
            this.setMaxTurns(20);
        } else if (difficulty == "hard") {
            this.setNumFakeNews(3);
            this.setNumTypePerFakeNews(2);
            this.setNumItems(3);
            this.setMaxTurns(18);
        } else if (difficulty == "insane") {
            this.setNumFakeNews(4);
            this.setNumTypePerFakeNews(3);
            this.setNumItems(4);
            this.setMaxTurns(15);
        } else if (difficulty == "impossible") {
            this.setNumFakeNews(5);
            this.setNumTypePerFakeNews(4);
            this.setNumItems(5);
            this.setMaxTurns(13);
        }
    }

}
