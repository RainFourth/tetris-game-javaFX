package tetris.figures;

import tetris.Block;

public class TetraJ extends Figure {

    public TetraJ() { super(-1, -1, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(5);
        Block b2 = new Block(5);
        Block b3 = new Block(5);
        Block b4 = new Block(5);

        final Block[][] blocks0 = {
                {null, b1, null},
                {null, b2, null},
                {b4,   b3, null},
        };
        //□□□
        //■■■
        //□□■

        final Block[][] blocks1 = {
                {b4,    null,   null},
                {b3,    b2,     b1},
                {null,  null,   null}
        };
        //□■□
        //□■□
        //■■□

        final Block[][] blocks2 = {
                {b3, b4,    null},
                {b2, null,  null},
                {b1, null,  null}
        };
        //□□□
        //■□□
        //■■■

        final Block[][] blocks3 = {
                {null,  null,   null},
                {b1,    b2,     b3},
                {null,  null,   b4}
        };
        //□■■
        //□■□
        //□■□

        return new Block[][][]{blocks0, blocks1, blocks2, blocks3};
    }


}
