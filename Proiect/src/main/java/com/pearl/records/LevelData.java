package com.pearl.records;

import com.pearl.prng.TerrainGenerator;

import java.util.Arrays;
import java.util.Vector;

public class LevelData {
    public Tile[][] tileMap;
    public int[][] entityMap;
    public Vector<Entity> entityList;

    public LevelData(int w, int h) {
        tileMap = new Tile[h][w];
        //TODO(Al-Andrew): remove this once terrain generation is done
        for (Tile[] tiles : tileMap) {
            Arrays.fill(tiles, Tile.DUNGEON_FLOOR);
        }

    }

    public void populate(TerrainGenerator gen, int depth) {
        gen.generate(tileMap, entityMap, entityList, depth);
    }

}
