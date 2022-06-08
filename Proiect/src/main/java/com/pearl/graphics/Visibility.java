package com.pearl.graphics;

import com.pearl.records.Tile;
import com.raylib.Raylib;

import java.util.Arrays;

public abstract class Visibility {
    protected Tile[][] map;

    Visibility(Tile[][] map) {
        this.map = map;
    }

    protected boolean[][] getBlankVisibilityMap() {
        boolean[][] ret = new boolean[map.length][map[0].length];

        for(var line : ret) {
            Arrays.fill(line, false);
        }

        return ret;
    }

    protected void setVisible(boolean[][] vis, int x, int y) {
        if(x < 0 || y < 0 || y >= vis.length || x >= vis[0].length )
            return;

        vis[y][x] = true;
    }

    protected boolean blocksVision(int x, int y) {
        if(x < 0 || y < 0)
            return true;
        if(map[y][x] == null)
            return false;
        return map[y][x].isSolid();
    }

    protected float getDistance(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    abstract public boolean[][] compute(Raylib.Vector2 origin, int rangeLimit);
}
