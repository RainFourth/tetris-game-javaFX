package tetris.figures;

import tetris.Block;

public class TetraSquare extends Figure {

    public TetraSquare() { super(0, -1, generateBlocks()[0]); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(0);
        Block b2 = new Block(0);
        Block b3 = new Block(0);
        Block b4 = new Block(0);

        final Block[][] blocks0 = {
                {b1, b2},
                {b3, b4}
        };
        //■■
        //■■

        return new Block[][][]{blocks0};
    }
}
