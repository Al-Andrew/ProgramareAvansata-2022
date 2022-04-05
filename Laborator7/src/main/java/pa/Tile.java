package pa;

public class Tile {
    private final char letter;
    private final int pointValue;

    public Tile(char letter, int pointValue) {
        this.letter = letter;
        this.pointValue = pointValue;
    }

    public char getLetter() {
        return letter;
    }

    public int getPointValue() {
        return pointValue;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "letter=" + letter +
                ", pointValue=" + pointValue +
                '}';
    }
}
