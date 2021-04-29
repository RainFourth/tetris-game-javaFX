package tetris;

import tetris.figures.Figure;

import java.util.*;

import static tetris.Utils.*;

//Model

public class Field {
    //private TetrisGame game;

    private volatile Block[][] blocks;
    //y
    //0 x
    private  Figure figure = null;
    private volatile Figure nextFigure = null;
    private volatile int hIscore = 0;
    private volatile int score = 0;
    private volatile int lines = 0;
    private volatile int currLevel = 0;
    private final int maxLevel;

    private int linesBeforeLvlUp = 10;
    private int startLevel = 0;


    public Field(int width, int height) {
        this.blocks = new Block[width][height];
        maxLevel = 10;
    }
    public Field() {
        this.blocks = new Block[10][20];
        maxLevel = 10;
    }

    //public TetrisGame getGame() { return game; }
    //public void setGame(TetrisGame game) { this.game = game; }
    public Block[][] getBlocks() { return blocks; }
    public int getWidth() { return blocks.length; }
    public int getHeight() { return blocks[0].length; }
    public void setBlocks(Block[][] blocks) { this.blocks = blocks; }
    public Figure getNextFigure() { return nextFigure; }
    public int getScore() { return score; }
    public int getHiScore() { return hIscore; }
    public void increaseScore(int count){
        score+=count;
        if(hIscore<score) hIscore = score;
    }
    public int getLines() { return lines; }
    public int getCurrLevel() { return currLevel; }
    public void setCurrLevel(int currLevel) { this.currLevel = currLevel; }
    public int getMaxLevel() { return maxLevel; }



    public void startLvlUp(){ startLevel = incr(startLevel, maxLevel); }
    public void startLvlDown(){ startLevel = decr(startLevel, maxLevel); }
    public int getStartLevel(){ return startLevel; }


    public boolean next(){
        boolean isGameOver = false;
        if (figure == null) {

            figure = nextFigure == null ? Figure.getRandomFigure() : nextFigure;
            nextFigure = Figure.getRandomFigure();

            figure.setX(getWidth()/2-1 + figure.getStartX());
            figure.setY(getHeight()-1 + figure.getStartY());

            for (int x = 0; x < figure.getBlocks().length; x++) {
                for (int y = 0; y < figure.getBlocks()[0].length; y++) {
                    if (figure.nonNull(x, y)){
                        if(nonNull(x+figure.getX(), y+figure.getY())) {isGameOver = true; continue;}
                        blocks[x+figure.getX()][y+figure.getY()] = figure.getBlocks()[x][y];
                    }
                }
            }

        }
        return !isGameOver;
    }

    public boolean moveDown(){
        for (int x = 0; x < figure.getBlocks().length; x++) {
            for (int y = 0; y < figure.getBlocks()[0].length; y++) {
                if(figure.nonNull(x, y)){
                    if(nonNullOrDownOut(figure.getX()+x, figure.getY()+y-1) ){
                        return false;
                    }
                    break;
                }
            }
        }

        removeFromBlocks();
        figure.down();
        drawToBlocks();

        return true;
    }


    public int clearLines(){
        int cnt = 0;
        loop: for (int y = 0; y < getHeight(); y++) {

            for (int x = 0; x < getWidth(); x++) {
                if (!nonNull(x,y)) continue loop;
            }

            for (int x = 0; x < getWidth(); x++) {
                setNull(x, y);
            }

            cnt++;
        }

        if (cnt>0)increaseScore(41*cnt+(cnt-1)*9);
        lines+=cnt;
        syncLevel(cnt);

        return cnt;
    }

    private void syncLevel(int deletedLines){
        linesBeforeLvlUp-=deletedLines;
        if (linesBeforeLvlUp<=0){lvlUp(); linesBeforeLvlUp+=10;}
    }

    private synchronized void lvlUp(){ currLevel=incrNotMore(currLevel, maxLevel); }

    public void deleteEmptyLines(){
        int count = 0;
        loop: for (int y = 0; y < getHeight()-1 ; y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (nonNull(x,y)) {
                    if (count>0){

                        for (int x1 = 0; x1 < getWidth(); x1++) {
                            if (nonNull(x1, y)){
                                blocks[x1][y-count] = blocks[x1][y];
                                setNull(x1, y);
                            }

                        }

                        y-=(count+1);
                        count = 0;
                    }
                    continue loop;
                }
            }
            count++;
        }
    }

    public void stop(){
        figure = null;
        increaseScore(3);
    }

    public void moveRight(){
        for (int y = 0; y < figure.getBlocks()[0].length; y++) {
            for (int x = figure.getBlocks().length-1; x >= 0; x--) {
                if (figure.nonNull(x, y)){
                    if (nonNullOrRightOut(figure.getX()+x+1, figure.getY()+y)) return;
                    break;
                }
            }
        }

        removeFromBlocks();
        figure.right();
        drawToBlocks();
    }

    public void moveLeft(){
        for (int y = 0; y < figure.getBlocks()[0].length; y++) {
            for (int x = 0; x < figure.getBlocks().length; x++) {
                if (figure.nonNull(x, y)){
                    if (nonNullOrLeftOut(figure.getX()+x-1, figure.getY()+y)) return;
                    break;
                }
            }
        }

        removeFromBlocks();
        figure.left();
        drawToBlocks();
    }



    public void rotateRight(){
        if (figure.nonRotatable() || cantBePlaced(figure.getNextPos())) return;
        removeFromBlocks();
        figure.rotateRight();
        drawToBlocks();
    }

    public void rotateLeft(){
        if (figure.nonRotatable() || cantBePlaced(figure.getPrevPos())) return;
        removeFromBlocks();
        figure.rotateLeft();
        drawToBlocks();
    }

    private boolean cantBePlaced(Block[][] b){
        for (int x = 0; x < b.length; x++) {
            for (int y = 0; y < b[0].length; y++) {
                if(b[x][y] != null && figure.isNull(x,y)
                        && nonNullOrOutOfBounds(figure.getX()+x, figure.getY()+y)) return true;
            }
        }
        return false;
    }


    private void setNull(int x, int y){ blocks[x][y] = null; }
    private boolean nonNull(int x, int y){ return blocks[x][y] != null; }
    private boolean nonNullOrLeftOut(int x, int y){ return x<0 || nonNull(x, y); }
    private boolean nonNullOrRightOut(int x, int y){ return x>=getWidth() || nonNull(x, y); }
    private boolean nonNullOrDownOut(int x, int y){ return y<0 || nonNull(x ,y); }
    private boolean nonNullOrOutOfBounds(int x, int y){ return x<0 || x>=getWidth() || y<0 || y>=getHeight() || nonNull(x,y); }


    private void drawToBlocks(){
        for (int x = 0; x < figure.getBlocks().length; x++) {
            for (int y = 0; y < figure.getBlocks()[0].length; y++) {
                if (figure.nonNull(x, y))
                    blocks[figure.getX()+x][figure.getY()+y] = figure.getBlocks()[x][y];
            }
        }
    }

    private void removeFromBlocks(){
        for (int x = 0; x < figure.getBlocks().length; x++) {
            for (int y = 0; y < figure.getBlocks()[0].length; y++) {
                if (figure.nonNull(x, y))
                    setNull(figure.getX()+x, figure.getY()+y);
            }
        }
    }



    public void clear(){
        for (int i = 0; i < getWidth(); i++) {
            Arrays.fill(blocks[i], null);
        }
        figure = null;
        nextFigure = null;
        score = 0;
        lines = 0;
        currLevel = startLevel;
    }



}
