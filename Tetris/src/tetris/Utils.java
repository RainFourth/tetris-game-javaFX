package tetris;

public final class Utils {
    private Utils() { }

    public static int incr(int curr, int max){ return curr+1>max ? 0 : curr+1; }
    public static int decr(int curr, int max){ return curr-1<0 ? max : curr-1; }
    public static int incrNotMore(int curr, int max){ return curr==max ? max : curr+1; }
}
