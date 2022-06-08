package com.pearl.records;


import com.pearl.graphics.GraphicsData;
import com.pearl.prng.DungeonGenerator;
import com.pearl.prng.TerrainGenerator;
import com.pearl.update.InputData;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.io.IOException;

public class GameData {
    public static boolean isRunning = true;
    public static final int WINDOW_WIDTH = 1536; //NOTE(Al-Andrew): Width and height such that we have 16x9
    public static final int WINDOW_HEIGHT = 864;
    public static final int GAME_MAP_WIDTH = 2 * WINDOW_WIDTH;
    public static final int GAME_MAP_HEIGHT = 2 * WINDOW_HEIGHT;
    public static final int GAME_WIDTH = WINDOW_WIDTH - 200;
    public static final int GAME_HEIGHT = WINDOW_HEIGHT - 200;
    public static final String WINDOW_TITLE = "PEARL"; //TODO(Al-Andrew): generate a random sub-title at startup

    public final TexturePack texturePack;
    public static final Raylib.Vector2 TILE_SIZE = new Jaylib.Vector2(24, 24);
    public static final Raylib.Vector2 TILEMAP_SIZE = new Jaylib.Vector2(GAME_MAP_WIDTH / TILE_SIZE.x(),
            GAME_MAP_HEIGHT / TILE_SIZE.y());

    public static long frameTimeMillis = 16; //NOTE(Al-Andrew): 16 millis = 60 fps
    public static int currentLevel = 0;
    public static final int LEVEL_COUNT = 2;
    public final LevelData[] levelMaps = new LevelData[LEVEL_COUNT];
    public final InputData inputData = new InputData();
    public final GraphicsData graphicsData = new GraphicsData(this);


    public GameData() throws IOException {
        texturePack = TexturePack.loadFromFile("assets/default.json");
        //exturePack = TexturePack.defaultEmpty();

        TerrainGenerator[] generatorList = new TerrainGenerator[]{
                new DungeonGenerator(),
                new DungeonGenerator(),
        };

        for(int i = 0; i < LEVEL_COUNT; ++i) {
            levelMaps[i] = new LevelData((int) TILEMAP_SIZE.x(), (int) TILEMAP_SIZE.y());
            levelMaps[i].populate(generatorList[i], i);
        }

    }
}
