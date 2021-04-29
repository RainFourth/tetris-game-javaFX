package tetris;

public class Block {
    private int colorCode;

    public Block(int colorCode) { this.colorCode = colorCode; }
    public Block() { colorCode = 0; }

    public int getColor() { return colorCode; }
    public void setColor(int colorCode) { this.colorCode = colorCode; }
}
