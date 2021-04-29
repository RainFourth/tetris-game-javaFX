package tetris.views;

//View

import tetris.TetrisGame;

public abstract class View {

    protected TetrisGame game;

    public View(TetrisGame game) {
        this.game = game;
    }

    public abstract void refreshView();
}
