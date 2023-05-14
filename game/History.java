package game;

import java.util.ArrayList;
import java.time.*;

public class History{
    private ArrayList<String> history;
    private int maxLenght;

    public History(int maxLenght){
        this.history = new ArrayList<String>(maxLenght);
        this.setMaxLenght(maxLenght);
    }

    //GETTERS
    public int getMaxLenght(){
        return this.maxLenght;
    }

    //SETTERS
    public void setMaxLenght(int maxLenght){
        if(maxLenght < 0)
            return;

        this.maxLenght = maxLenght;
    }

    //METHODS
    public void add(String add){
        Instant instant = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        String now = String.format("[%02d:%02d:%02d] ", ldt.getHour(), ldt.getMinute(), ldt.getSecond());

        this.history.add(0, now + add);

        if(this.history.size() > this.maxLenght)
            this.history.remove(this.history.size() - 1);
    }

    public String get(int index){
        if(this.history.size() > index)
            return this.history.get(index);
        return null;
    }
}