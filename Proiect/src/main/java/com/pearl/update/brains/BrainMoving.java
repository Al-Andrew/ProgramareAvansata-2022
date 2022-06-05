package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.raylib.Raylib;

public interface BrainMoving {

    default boolean moveBody(Raylib.Vector2 direction, GameData data, EntityData body) {

        int newx = (int) (body.tilePosition.x() + direction.x());
        int newy = (int) (body.tilePosition.y() + direction.y());
        if(data.levelMaps[data.currentLevel].tileMap[newy][newx].isSolid()) {
            return false;
        }

        boolean occupied = data.levelMaps[data.currentLevel].entityList
                .stream()
                .anyMatch((EntityData e) -> e.tilePosition.x() == newx &&
                        e.tilePosition.y() == newy &&
                        e.type.isSolid());

        if(occupied == true)
            return false;

        body.tilePosition.x(newx).y(newy);
        return true;
    }
}
