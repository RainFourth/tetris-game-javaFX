package tetris.views;

import javafx.application.Application;
import javafx.application.Platform;
import tetris.TetrisGame;

public class FXviewController extends View{

    public FXviewController(TetrisGame game) {
        super(game);
        FXView.game = game;
        /*FXView.fXviewController = this;*/

        Thread t = new Thread(() -> Application.launch(FXView.class));
        t.setDaemon(true);
        t.start();


        /*synchronized (game){

            game.waitGame();
        }*/

        game.sleepGame(1500);

        /*Platform.runLater(() -> FXView.fx.setGameMainScene());*/
    }





    @Override
    public void refreshView() {
        Platform.runLater(() -> FXView.fx.refresh());
    }
}

