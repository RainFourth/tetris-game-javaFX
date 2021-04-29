package tetris;

import static tetris.Utils.decr;
import static tetris.Utils.incr;

public class MainMenu {
    private int currMenuPos = 0; //0 - START   1 - LEVEL
    private final int max = 3;

    private Field field;

    public MainMenu(Field field) { this.field = field; }

    public int getStartLvl(){return field.getStartLevel();}
    public int getCurrMenuPos() { return currMenuPos; }
    public int getMax() { return max; }

    public void down(){currMenuPos = incr(currMenuPos, max);}
    public void up(){currMenuPos = decr(currMenuPos, max);}
    public void left(){if (currMenuPos==1) field.startLvlDown();}
    public void right(){if (currMenuPos==1) field.startLvlUp();}
}
