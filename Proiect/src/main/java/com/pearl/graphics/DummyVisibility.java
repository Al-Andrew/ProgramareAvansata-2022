package com.pearl.graphics;

import com.pearl.records.Tile;
import com.raylib.Raylib;

import java.util.Arrays;

public class DummyVisibility extends Visibility {
    public DummyVisibility(Tile[][] map) {
        super(map);
    }

    @Override
    public boolean[][] compute(Raylib.Vector2 origin, int rangeLimit) {
        {
            boolean[][] vision = new boolean[map.length][map[0].length];
            for(var line : vision) {
                Arrays.fill(line, false);
            }
            int ox = (int) origin.x();
            int oy = (int) origin.y();

            for(int y = Math.max(oy - rangeLimit, 0); y < Math.min(oy + rangeLimit, map.length) ; ++y) {
                for(int x = Math.max(ox - rangeLimit, 0); x < Math.min(ox + rangeLimit, map[0].length ); ++x) {
                    vision[y][x] = true;
                }
            }

            return vision;
        }
    }
}
