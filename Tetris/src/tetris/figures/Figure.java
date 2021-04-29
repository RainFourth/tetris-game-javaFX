package tetris.figures;

import static tetris.Utils.decr;
import static tetris.Utils.incr;
import tetris.Block;

public abstract class Figure {

    public static Figure getRandomFigure(){
        switch (((int)(Math.random()*100))%7){
            case 0: return new TetraSquare();
            case 1: return new TetraL();
            case 2: return new TetraJ();
            case 3: return new TetraT();
            case 4: return new TetraZ();
            case 5: return new TetraS();
            case 6: return new TetraLine();

            default:return new TetraSquare();
        }
    }


    private final int startX;
    private final int startY;

    private int x;
    private int y;

    private final Block[][][] allBlocks;
    private int currPos;

    public Figure(int startX, int startY, Block[][]... blocks) {
        this.startX = startX;
        this.startY = startY;
        allBlocks = blocks;
        currPos = 0;
    }

    public Block[][] getBlocks() { return allBlocks[currPos]; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isNull(int x, int y){return getBlocks()[x][y] == null;}
    public boolean nonNull(int x, int y){return getBlocks()[x][y] != null;}

    public void right(){x++;}
    public void left(){x--;}
    public void down(){y--;}

    public boolean nonRotatable(){return allBlocks.length<=1;}
    public void rotateRight() {  currPos = incr(currPos, allBlocks.length-1); }
    public void rotateLeft() { currPos = decr(currPos, allBlocks.length-1); }
    public Block[][] getNextPos() { return allBlocks[incr(currPos, allBlocks.length-1)]; }
    public Block[][] getPrevPos() { return allBlocks[decr(currPos, allBlocks.length-1)]; }


}
