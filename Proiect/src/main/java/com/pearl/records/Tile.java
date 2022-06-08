package com.pearl.records;

public enum Tile {
    DUNGEON_FLOOR,
    DUNGEON_BRICK_WALL(true),
    DUNGEON_SLATE_WALL(true),
    ;

    final boolean solid;

    Tile() {
        this.solid = false;
    }

    Tile(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }
}
