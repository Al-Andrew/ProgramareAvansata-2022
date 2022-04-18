package pa;

import java.util.List;

public class MockDictionary implements Dictionary {
    @Override
    public boolean isWord(String word) {
        return true;
    }

    @Override
    public List<String> getWordlist() {
        return null;
    }
}
