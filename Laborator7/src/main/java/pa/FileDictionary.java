package pa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileDictionary implements Dictionary {
    private List<String> words;

    FileDictionary(String pathToDictFile) throws IOException {
        this.words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(pathToDictFile));
        String line = reader.readLine();
        while (line != null) {
            words.add(line.strip());
            line = reader.readLine();
        }
    }

    @Override
    public boolean isWord(String word) {
        if(word == null)
            return false;
        int pos = Collections.binarySearch(this.words, word);  //NOTE(Al-Andrew): The dictionary is alphabetically ordered. might as well binary search
        return pos > 0;
    }

    @Override
    public List<String> getWordlist() {
        return words;
    }
}
