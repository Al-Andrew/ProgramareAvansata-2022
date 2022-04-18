package pa;

import java.util.ArrayList;
import java.util.List;

public class PointChasingWordAssembler implements WordAssembler {
    private Dictionary dictionary;
    private Board board;

    PointChasingWordAssembler(Dictionary dict, Board board) {
        this.board = board;
        this.dictionary = dict;
    }

    @Override
    public String assembleWords(List<Tile> tiles) {
        List<String> restricted = new ArrayList<>();
        //NOTE(Al-Andrew): we can only have words containing our letters and only as long as the number of letters we have;
        for (var word : dictionary.getWordlist()) {
            if (word.length() > tiles.size())
                continue;
            //NOTE(Al-Andrew): this doesn't account for duplicate letters but was quick and convenient
            String lettersRegex = "[";
            for (var tile : tiles) {
                lettersRegex += tile.getLetter();
            }
            lettersRegex += "]+";

            if (word.matches(lettersRegex)) {
                restricted.add(word);
            }
        }

        return restricted.stream().max(((String a, String b) -> Integer.compare(board.calculatePoints(a),board.calculatePoints(b)))).orElse(null);
    }
}
