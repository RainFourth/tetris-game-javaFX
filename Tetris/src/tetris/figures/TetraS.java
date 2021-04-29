package tetris.figures;

import tetris.Block;

public class TetraS extends Figure {

    public TetraS() { super(-1, -1, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(1);
        Block b2 = new Block(1);
        Block b3 = new Block(1);
        Block b4 = new Block(1);

        final Block[][] blocks0 = {
                {b1,    null,  null},
                {b2,    b3,    null},
                {null,  b4,    null},
        };
        //□□□
        //□■■
        //■■□

        final Block[][] blocks1 = {
                {null,  b2,   b1},
                {b4,    b3,   null},
                {null,  null, null}
        };
        //■□□
        //■■□
        //□■□


        return new Block[][][]{blocks0, blocks1};
    }
}
