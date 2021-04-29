package tetris.figures;

import tetris.Block;

public class TetraL extends Figure {

    public TetraL() { super(-1, -1, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(4);
        Block b2 = new Block(4);
        Block b3 = new Block(4);
        Block b4 = new Block(4);

        final Block[][] blocks0 = {
                {b4,    b3, null},
                {null,  b2, null},
                {null,  b1, null},
        };
        //□□□
        //■■■
        //■□□

        final Block[][] blocks1 = {
                {null,  null,   b4},
                {b1,    b2,     b3},
                {null,  null,   null}
        };
        //■■□
        //□■□
        //□■□

        final Block[][] blocks2 = {
                {b1, null, null},
                {b2, null, null},
                {b3, b4,   null}
        };
        //□□□
        //□□■
        //■■■

        final Block[][] blocks3 = {
                {null,  null,   null},
                {b3,    b2,     b1},
                {b4,    null,   null  }
        };
        //□■□
        //□■□
        //□■■

        return new Block[][][]{blocks0, blocks1, blocks2, blocks3};
    }
}
