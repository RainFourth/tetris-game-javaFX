package tetris;

import tetris.views.View;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

//Controller

public class TetrisGame implements Runnable{

    private volatile Field field;
    private volatile MainMenu mainMenu;
    private final Set<View> views = new HashSet<>();
    private final UpdateThread uThread = new UpdateThread();
    private final Queue<Command> commands = new LinkedBlockingQueue<>();

    private volatile GameState gameState;

    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }
    public MainMenu getMainMenu() { return mainMenu; }
    public void setMainMenu(MainMenu mainMenu) { this.mainMenu = mainMenu; }
    public void addViews(View... views) { Collections.addAll(this.views, views); }
    public GameState getGameState(){return gameState;}
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public TetrisGame(Field field, MainMenu mainMenu) {
        this.field = field;
        this.mainMenu = mainMenu;
        uThread.setDaemon(true);
        uThread.start();
    }



    public synchronized void addCommand(Command command) {
        commands.offer(command);
        this.notify();
    }
    public synchronized void notifyGame() { this.notify(); }
    public synchronized void waitGame() { try { wait(); } catch (InterruptedException e) { e.printStackTrace();} }
    public synchronized void waitGame(long ms) { try { wait(ms); } catch (InterruptedException e) { e.printStackTrace();} }
    private long getStepTime(){ return 800 - field.getCurrLevel()*70; }
    private long getSystemTime(){ return System.currentTimeMillis(); }
    public void sleepGame(long ms){ try { Thread.sleep(ms); } catch (InterruptedException e) { e.printStackTrace(); } }







    @Override
    public void run(){
        gameState = GameState.LOGO;
        while (true) switchState();
    }

    private void switchState(){
        switch (gameState){
            case LOGO: runLogo(); break;
            case MAIN_MENU: runMainMenu(); break;
            case COPYRIGHT: runCopyright(); break;
            case CONTROLS: runControls(); break;
            case READY_TO_START: runMainGameEngine(); break;
            case RUNNING: break;
            case PAUSE: runPauseMenu(); break;
            case GAME_OVER: runGameOverState(); break;
        }
    }

    private void runLogo(){ refresh(); sleepGame(2000); gameState = GameState.MAIN_MENU; }
    private void runMainMenu(){ while (gameState == GameState.MAIN_MENU){ refresh(); waitGame(); } }
    private void runCopyright(){ while (gameState == GameState.COPYRIGHT){ refresh(); waitGame(); } }
    private void runControls(){ while (gameState == GameState.CONTROLS){ refresh(); waitGame(); } }
    private void runPauseMenu(){ while (gameState == GameState.PAUSE){ refresh(); waitGame(); } }
    private void runGameOverState(){ while (gameState == GameState.GAME_OVER){ refresh(); waitGame(); } }


    private long time;
    private void runMainGameEngine() {
        long waitTime;


        commands.clear();
        field.clear();
        gameState = GameState.RUNNING;

        if (!nextAndCanContinue()) return;
        refresh();

        loop: while (true){
            time = getSystemTime();


            subLoop: while (getSystemTime()-time < getStepTime()){

                if (!canContinue()) return;
                refresh();

                if (!commands.isEmpty()){
                    switch (commands.poll()){
                        case MOVE_LEFT: field.moveLeft(); break;
                        case MOVE_RIGHT: field.moveRight(); break;
                        case ROTATE_LEFT: field.rotateLeft(); break;
                        case ROTATE_RIGHT: field.rotateRight(); break;
                        case DROP:
                            while (field.moveDown()) {
                                field.increaseScore(1);

                                if (!canContinue()) return;
                                refresh();

                                sleepGame(10);
                            }
                        case MOVE_DOWN:
                            break subLoop;
                    }



                } else {
                    waitTime = getStepTime()-(getSystemTime()-time);
                    if(waitTime>0) waitGame(waitTime);
                }
            }

            if(field.moveDown()){ refresh(); continue loop; }
            else {
                field.stop();
                if (field.clearLines()!=0){

                    if (!canContinue()) return;
                    refresh();

                    sleepGame(400);
                    field.deleteEmptyLines();
                }

                if (!canContinue()) return;
                refresh();

                sleepGame(400);


                if (!nextAndCanContinue()) return;
                refresh();

                commands.clear();
                continue loop;
            }

        }
    }

    private synchronized boolean nextAndCanContinue(){
        if(!field.next()) { gameState = GameState.GAME_OVER; return canContinue();}
        else return true;
    }

    private synchronized boolean canContinue(){
        long time = 0;
        switch (gameState){
            case GAME_OVER: switchState(); return false;
            case PAUSE:
                time = getSystemTime();
                switchState();
                this.time += getSystemTime()-time;
                return gameState==GameState.RUNNING;
            case RUNNING: return true;
            case READY_TO_START: return false;

            default: return false;
        }
    }


    public synchronized void pauseOrResume(){
        switch (gameState){
            case MAIN_MENU: gameState = GameState.READY_TO_START; break;
            case RUNNING: gameState = GameState.PAUSE; break;
            case PAUSE: gameState = GameState.RUNNING; break;
            case GAME_OVER: gameState = GameState.LOGO; break;
        }
        notifyGame();
    }
    public synchronized void restart(){
        switch (gameState){
            case RUNNING: case GAME_OVER: case PAUSE: gameState = GameState.READY_TO_START; break;
        }
        notifyGame();
    }


    public void gameToLogo(){gameState = GameState.LOGO; notifyGame();}




    public void mainMenuDown(){mainMenu.down(); refresh();}
    public void mainMenuUp(){mainMenu.up(); refresh();}
    public void mainMenuLeft(){mainMenu.left(); refresh();}
    public void mainMenuRight(){mainMenu.right(); refresh();}
    public void mainMenuSelect(){
        switch (mainMenu.getCurrMenuPos()){
            case 0: gameState = GameState.READY_TO_START; notifyGame(); break;
            case 2: gameState = GameState.COPYRIGHT; notifyGame(); break;
            case 3: gameState = GameState.CONTROLS; notifyGame(); break;
        }
    }
    public void mainMenuToLogo(){gameState = GameState.LOGO; notifyGame(); }


    public void toMainMenu(){gameState = GameState.MAIN_MENU; notifyGame();}
















    public void quit(){ System.exit(0); }

    public void refreshViews(){ views.forEach(View::refreshView); }



    public void refresh(){ uThread.refresh(); }

    private class UpdateThread extends Thread{

        private volatile boolean doRefresh = false;

        @Override
        public void run(){
            while (true){
                synchronized (this){
                    while (!getDoRefreshAndSetFalse()) try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                TetrisGame.this.refreshViews();
            }
        }

        private boolean getDoRefreshAndSetFalse(){
            return doRefresh && !(doRefresh = false);
        }

        public void refresh(){
            doRefresh = true;
            synchronized (this){this.notify();}
        }

    }

}
