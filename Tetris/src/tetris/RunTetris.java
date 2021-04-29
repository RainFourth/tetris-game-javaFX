package tetris;

import tetris.views.*;

public class RunTetris {
    public static void main(String[] args) {
        Field field = new Field();
        TetrisGame game = new TetrisGame(field, new MainMenu(field));
        //field.setGame(game);
        game.addViews(/*new ConsoleView(game), new SwingView(game),*/ new FXviewController(game));
        /*game.waitGame();*/
        game.run();
    }
}
