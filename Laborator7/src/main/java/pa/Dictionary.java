package pa;

import java.util.List;

public interface Dictionary {
    boolean isWord(String word);

    List<String> getWordlist();
}
