package tetris.figures;

import tetris.Block;

public class TetraLine extends Figure{

    public TetraLine() { super(-1, -2, generateBlocks()); }

    private static Block[][][] generateBlocks(){
        Block b1 = new Block(6);
        Block b2 = new Block(6);
        Block b3 = new Block(6);
        Block b4 = new Block(6);

        final Block[][] blocks0 = {
                {null, null, b1, null},
                {null, null, b2, null},
                {null, null, b3, null},
                {null, null, b4, null},
        };
        //□□□□
        //■■■■
        //□□□□
        //□□□□

        final Block[][] blocks1 = {
                {null,  null,   null,   null},
                {null,  null,   null,   null},
                {b1,    b2,     b3,     b4},
                {null,  null,   null,   null}
        };
        //□□■□
        //□□■□
        //□□■□
        //□□■□

        return new Block[][][]{blocks0, blocks1};
    }
}
