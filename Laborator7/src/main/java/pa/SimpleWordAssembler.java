package pa;

import java.util.ArrayList;
import java.util.List;

public class SimpleWordAssembler implements WordAssembler {
    private Dictionary dictionary;

    SimpleWordAssembler(Dictionary dict) {
        this.dictionary = dict;
    }

    @Override
    public String assembleWords(List<Tile> tiles) {
        List<String> restricted = new ArrayList<>();
        //NOTE(Al-Andrew): we can only have words containing our letters and only as long as the number of letters we have;
        for(var word :  dictionary.getWordlist()) {
            if(word.length() > tiles.size())
                continue;
            //NOTE(Al-Andrew): this doesn't account for duplicate letters but was quick and convenient
            String lettersRegex = "[";
            for(var tile : tiles){
                lettersRegex += tile.getLetter();
            }
            lettersRegex += "]+";

            if(word.matches(lettersRegex)){
                restricted.add(word);
            }
        }
        System.out.println(restricted);
        if(!restricted.isEmpty())
            return restricted.get(0);

        return null;
    }
}
