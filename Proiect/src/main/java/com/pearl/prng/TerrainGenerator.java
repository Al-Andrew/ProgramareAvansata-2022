package com.pearl.prng;

import com.pearl.records.EntityData;
import com.pearl.records.Tile;

import java.util.Vector;

public interface TerrainGenerator {

    void generate(Tile[][] tiles, Vector<EntityData> entityList, int depth);
}
