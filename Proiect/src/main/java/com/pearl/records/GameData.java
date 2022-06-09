package com.pearl.records;


import com.pearl.graphics.GraphicsData;
import com.pearl.prng.DungeonGenerator;
import com.pearl.prng.TerrainGenerator;
import com.pearl.update.InputData;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.io.IOException;

public class GameData {
    public static boolean isRunning = true;
    public static final int WINDOW_WIDTH = 1536; //NOTE(Al-Andrew): Width and height such that we have 16x9
    public static final int WINDOW_HEIGHT = 864;
    public static final int GAME_MAP_WIDTH = WINDOW_WIDTH;
    public static final int GAME_MAP_HEIGHT = WINDOW_HEIGHT;
    public static final int LOG_HEIGHT = 140;
    public static final int INVENTORY_WIDTH = 250;
    public static final int GAME_WIDTH = WINDOW_WIDTH - INVENTORY_WIDTH;
    public static final int GAME_HEIGHT = WINDOW_HEIGHT - LOG_HEIGHT;
    public static final String WINDOW_TITLE = "PEARL"; //TODO(Al-Andrew): generate a random sub-title at startup

    public static TexturePack texturePack;
    public static final Raylib.Vector2 TILE_SIZE = new Jaylib.Vector2(24, 24);
    public static final Raylib.Vector2 TILEMAP_SIZE = new Jaylib.Vector2(GAME_MAP_WIDTH / TILE_SIZE.x(),
            GAME_MAP_HEIGHT / TILE_SIZE.y());

    public static long frameTimeMillis = 16; //NOTE(Al-Andrew): 16 millis = 60 fps
    public static int currentLevel = 0;
    public static int LEVEL_COUNT;
    public static GameOver gameOver = GameOver.NONE;
    public static LevelData[] levelMaps;
    public final InputData inputData = new InputData();
    public GraphicsData graphicsData = new GraphicsData(this);
    public static final CircularFifoBuffer logBuffer = new CircularFifoBuffer(5);

    public GameData() throws IOException {
        texturePack = TexturePack.loadFromFile("assets/default.json");
        TerrainGenerator[] generatorList = new TerrainGenerator[]{
                new DungeonGenerator(),
                new DungeonGenerator(),
        };
        GameData.LEVEL_COUNT = generatorList.length;
        levelMaps = new LevelData[LEVEL_COUNT];

        for(int i = 0; i < LEVEL_COUNT; ++i) {
            levelMaps[i] = new LevelData((int) TILEMAP_SIZE.x(), (int) TILEMAP_SIZE.y());
            levelMaps[i].populate(generatorList[i], i);
        }

        addLogEntryNormal("Welcome to PEARL!");
        addLogEntryNormal("At the bottom of the dungeon lies your prize:");
        addLogEntryGood("The amulet of YANDOR!!!");
    }

    static private String[] splitText(String text) {
        final int maxLength = 46;
        final String[] ret = new String[(text.length() / (maxLength - 10)) + 1];
        String current = "";
        int j = 0;
        String[] words = text.split(" ");
        for(int i = 0; i < words.length ; ++i) {
            if(current.trim().length() + words[i].length() > maxLength) {
                ret[j++] = current.trim();
                current = "";
            }
            current += words[i] + " ";
        }
        if(!current.trim().isBlank() && !current.trim().isEmpty()) {
            ret[j] = current.trim();
        }
        return ret;
    }


    static public void addLogEntryNormal(String text){
        for(var msg : splitText(text)) {
            logBuffer.add(new LogEntry(msg, EntryType.NORMAL));
        }
    }
    static public void addLogEntryBad(String text){
        for(var msg : splitText(text)) {
            logBuffer.add(new LogEntry(msg, EntryType.BAD));
        }
    }
    static public void addLogEntryGood(String text){
        for(var msg : splitText(text)) {
            logBuffer.add(new LogEntry(msg, EntryType.GOOD));
        }
    }

    public static void reset() {
        try {
            texturePack = TexturePack.loadFromFile("assets/default.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TerrainGenerator[] generatorList = new TerrainGenerator[]{
                new DungeonGenerator(),
                new DungeonGenerator(),
        };
        GameData.LEVEL_COUNT = generatorList.length;
        levelMaps = new LevelData[LEVEL_COUNT];

        for(int i = 0; i < LEVEL_COUNT; ++i) {
            levelMaps[i] = new LevelData((int) TILEMAP_SIZE.x(), (int) TILEMAP_SIZE.y());
            levelMaps[i].populate(generatorList[i], i);
        }

        addLogEntryNormal("Welcome to PEARL!");
        addLogEntryNormal("At the bottom of the dungeon lies your prize:");
        addLogEntryGood("The amulet of YANDOR!!!");
        gameOver = GameOver.NONE;
        texturePack.loadTextures();
    }
}
