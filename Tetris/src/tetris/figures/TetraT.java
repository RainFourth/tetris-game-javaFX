package tetris.figures;

import tetris.Block;

public class TetraT extends Figure {

    public TetraT() { super(-1, -1, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(3);
        Block b2 = new Block(3);
        Block b3 = new Block(3);
        Block b4 = new Block(3);

        final Block[][] blocks0 = {
                {null,  b1, null},
                {b4,    b2, null},
                {null,  b3, null},
        };
        //□□□
        //■■■
        //□■□

        final Block[][] blocks1 = {
                {null,  b4,     null},
                {b3,    b2,     b1},
                {null,  null,   null}
        };
        //□■□
        //■■□
        //□■□

        final Block[][] blocks2 = {
                {b3, null,  null},
                {b2, b4,    null},
                {b1, null,  null}
        };
        //□□□
        //□■□
        //■■■

        final Block[][] blocks3 = {
                {null,  null,   null},
                {b1,    b2,     b3},
                {null,  b4,     null  }
        };
        //□■□
        //□■■
        //□■□

        return new Block[][][]{blocks0, blocks1, blocks2, blocks3};
    }
}
