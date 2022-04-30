package pa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stick {
    public int endX, endY;
    public int startX, startY;

    @JsonCreator
    public Stick(@JsonProperty("startX") int startX, @JsonProperty("startY") int startY,
                 @JsonProperty("endX") int endX, @JsonProperty("endY") int endY) {
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
                "(" + endX + ", " + endY + ")" ;
    }
}
