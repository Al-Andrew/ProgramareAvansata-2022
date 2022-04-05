package pa;

public class Stick {
    int endX, endY;
    int startX, startY;

    public Stick(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean occupies(int x, int y) {
        if( x == startX && y == startY)
            return true;
        if( x == endX && y == endY)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "(" + startX + ", " + startY + ") -> " +
                "(" + endX + ", " + startY + ")" ;
    }
}
