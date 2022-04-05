package pa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Game {
    private Bag bag = new Bag();
    private Board board = new Board(this);
    private List<Player> players = new ArrayList<>();
    private Map<Player, Integer> scoreboard = new HashMap<>();
    private Dictionary dictionary = new MockDictionary();

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
        scoreboard.put(player, 0);
    }

    public void play() throws InterruptedException {
        //Here we block until the game is done
        ExecutorService es = Executors.newCachedThreadPool();
        for(var pl : players)
            es.execute(pl);
        es.shutdown();
        boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

        Map.Entry<Player, Integer> maxEntry = scoreboard
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .orElse(null);
        System.out.println("Winner: " + maxEntry.getKey().getName() + " with a score of: " + maxEntry.getValue());
    }

    public static void main(String args[]) throws InterruptedException {
        Game game = new Game();
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.play();
    }

    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Player, Integer> getScoreboard() {
        return scoreboard;
    }
}
