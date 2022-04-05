package pa;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Game game;
    private final List<String> words = new ArrayList<>();

    public Board(Game game) {
        this.game = game;
    }

    public int calculatePoints(String word) {
        int points = 0;
        for(var chr : word.toCharArray()) {
            points += game.getBag().letterPoints.get(chr);
        }
        points *= word.length();

        return points;
    }

    synchronized void addWord(Player player, String word) {
        int new_points = 0;
        if(game.getDictionary().isWord(word)) {
            words.add(word);
            new_points = calculatePoints(word);
            Integer old_score = game.getScoreboard().get(player);
            game.getScoreboard().replace(player, new_points + (old_score == null? 0 : old_score));
        }
        if(new_points != 0)
            System.out.println(player.getName() + ": " + word + " worth: " + new_points);
        else
            System.out.println(player.getName() + "skipped a turn");
    }

    @Override
    public String toString() {
        return words.toString();
    }
}
