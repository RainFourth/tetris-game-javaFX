package tetris.figures;

import tetris.Block;

public class TetraZ extends Figure {

    public TetraZ() { super(-1, -1, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(2);
        Block b2 = new Block(2);
        Block b3 = new Block(2);
        Block b4 = new Block(2);

        final Block[][] blocks0 = {
                {null,  b1,     null},
                {b3,    b2,     null},
                {b4,    null,   null},
        };
        //□□□
        //■■□
        //□■■

        final Block[][] blocks1 = {
                {null,  null,   null},
                {b4,    b3,     null},
                {null,  b2,     b1}
        };
        //□□■
        //□■■
        //□■□


        return new Block[][][]{blocks0, blocks1};
    }
}
