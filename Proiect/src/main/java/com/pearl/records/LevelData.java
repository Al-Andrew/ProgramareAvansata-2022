package com.pearl.records;

import com.pearl.prng.TerrainGenerator;

import java.util.Arrays;
import java.util.Vector;

public class LevelData {
    public Tile[][] tileMap;
    public Vector<EntityData> entityList;

    public LevelData(int w, int h) {
        tileMap = new Tile[h][w];
        for (Tile[] tiles : tileMap) {
            Arrays.fill(tiles, Tile.BACKGROUND);
        }
        entityList = new Vector<>();

    }

    public void populate(TerrainGenerator gen, int depth) {
        gen.generate(tileMap, entityList, depth);
    }

}
