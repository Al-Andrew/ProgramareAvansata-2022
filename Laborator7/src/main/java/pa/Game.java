package pa;

import java.io.IOException;
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
    private Dictionary dictionary = new FileDictionary("en_US-custom.dic.clean");
    private Timekeeper timekeeper;
    private int currentPlayerId = 0;
    private boolean lastTurn = false;
    private boolean running = true;

    public Game() throws IOException {
    }

    public void addPlayer(Player player) {
        player.setGame(this);
        player.setWordAssembler(new PointChasingWordAssembler(dictionary, board));
        player.setId(players.size());
        players.add(player);
        scoreboard.put(player, 0);
    }

    public void play() throws InterruptedException {
        //Here we block until the game is done
        ExecutorService es = Executors.newCachedThreadPool();
        timekeeper = new Timekeeper(this, 5);
        Thread timeThread = new Thread(timekeeper);
        timeThread.setDaemon(true);
        timeThread.start();

        for(var pl : players)
            es.execute(pl);
        this.running = true;
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        this.running = false;

        Map.Entry<Player, Integer> maxEntry = scoreboard
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .orElse(null);
        System.out.println("Winner: " + maxEntry.getKey().getName() + " with a score of: " + maxEntry.getValue()); //TODO(Al-Andrew): consider ties
    }

    public static void main(String args[]) throws InterruptedException, IOException {
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

    public boolean isLastTurn() { return lastTurn; }
    public boolean isRunning() { return running; }
    public void forceEnd() { this.lastTurn = true; }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Player, Integer> getScoreboard() {
        return scoreboard;
    }

    public synchronized void waitForTurn(Player player) {
        while(this.currentPlayerId != player.getId()) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException e) {
                System.err.print(e.getMessage());
            }
        }
    }

    public synchronized void endTurn() {
        this.currentPlayerId = (this.currentPlayerId + 1) % players.size();
        notifyAll();
    }
}
