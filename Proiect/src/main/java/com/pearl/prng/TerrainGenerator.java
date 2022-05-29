package com.pearl.prng;

import com.pearl.records.Entity;
import com.pearl.records.Tile;

import java.util.Vector;

public interface TerrainGenerator {

    void generate(Tile[][] tiles, int[][] entityMap, Vector<Entity> entityList, int depth);
}
