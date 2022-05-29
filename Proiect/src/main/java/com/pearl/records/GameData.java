package com.pearl.records;


import com.pearl.prng.*;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.io.IOException;

public class GameData {
    public boolean isRunning = true;
    public final int WINDOW_WIDTH = 1536; //NOTE(Al-Andrew): Width and height such that we have 16x9
    public final int WINDOW_HEIGHT = 864;
    public final int GAME_WIDTH = 1536;
    public final int GAME_HEIGHT = 864;
    public final String WINDOW_TITLE = "PEARL"; //TODO(Al-Andrew): generate a random sub-title at startup

    public final TexturePack texturePack = TexturePack.loadFromFile("assets/default.json");
    public final Raylib.Vector2 TILE_SIZE = new Jaylib.Vector2(24, 24);
    public final Raylib.Vector2 TILEMAP_SIZE = new Jaylib.Vector2(GAME_WIDTH / TILE_SIZE.x(),
            GAME_HEIGHT / TILE_SIZE.y());

    public long frameTimeMillis = 16; //NOTE(Al-Andrew): 16 millis = 60 fps
    public EntityData player = new EntityData();
    public int currentLevel = 0;
    public final int LEVEL_COUNT = 5;
    public final LevelData[] levelMaps = new LevelData[LEVEL_COUNT];


    public GameData() throws IOException {
        //NOTE(Al-Andrew): The level layout should end up something like this:
        //TODO(Al-Andrew): Add more variation in the levels
        // 1. (Dungeon)
        // 2. (Cave)
        // 3. (Dwarf village)
        // 4. (MagmaCave)
        // 5. (Hell)
        TerrainGenerator[] generatorList = new TerrainGenerator[]{
                new DungeonGenerator(),
                new CaveGenerator(),
                new DwarfVillageGenerator(),
                new MagmaCaveGenerator(),
                new HellGenerator()
        };

        for(int i = 0; i < LEVEL_COUNT; ++i) {
            levelMaps[i] = new LevelData((int) TILEMAP_SIZE.x(), (int) TILEMAP_SIZE.y());
            levelMaps[i].populate(generatorList[i], i);
        }

    }
}
