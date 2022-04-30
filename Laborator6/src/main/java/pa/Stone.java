package pa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stone {
    public int x, y;
    public StoneColor color;

    @JsonCreator
    public Stone(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("color") StoneColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
