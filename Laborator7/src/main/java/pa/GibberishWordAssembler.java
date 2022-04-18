package pa;

import java.util.List;

public class GibberishWordAssembler implements WordAssembler {

    @Override
    public String assembleWords(List<Tile> tiles) {
        String word = "";
        for(var tile : tiles) {
            word += tile.getLetter();
        }
        return word;
    }
}
