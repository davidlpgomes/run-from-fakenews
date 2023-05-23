package game;

public interface HistoryInterface {
    
    //GETTERS
    public int getMaxLenght();

    //SETTERS
    public void setMaxLenght(int maxLenght);

    //METHODS
    public void add(String add);

    public String get(int index);
}
