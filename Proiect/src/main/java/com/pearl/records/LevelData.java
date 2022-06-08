package com.pearl.records;

import com.pearl.graphics.RaycastingVisibility;
import com.pearl.graphics.Visibility;
import com.pearl.prng.TerrainGenerator;

import java.util.Vector;

public class LevelData {
    public Tile[][] tileMap;
    public Vector<EntityData> entityList;
    public boolean[][] visibilityMap;
    public boolean[][] mapMemory;
    private Visibility visibility;
    public EntityData player; //This is needed everywhere so i'm separating a reference to keep here

    public LevelData(int w, int h) {
        tileMap = new Tile[h][w];
        entityList = new Vector<>();
        visibilityMap = new boolean[h][w];
        mapMemory = new boolean[h][w];
        visibility = new RaycastingVisibility(tileMap);
    }

    public void populate(TerrainGenerator gen, int depth) {
        gen.generate(tileMap, entityList, depth);
        this.player = entityList
                .stream()
                .filter((e)->e.type == Entity.PLAYER)
                .findFirst()
                .orElse(null);
    }

    public void updateVisibility() {
        assert player != null;
        visibilityMap = visibility.compute(player.tilePosition, 14);

        for(int y = 0; y < visibilityMap.length ; ++y) {
            for(int x = 0; x < visibilityMap[y].length; ++x) {
                if(visibilityMap[y][x])
                    mapMemory[y][x] = true;
            }
        }
    }

}
