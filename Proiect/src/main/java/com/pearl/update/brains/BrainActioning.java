package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.raylib.Raylib;

public interface BrainActioning {

    default boolean act(Raylib.Vector2 target, GameData data, EntityData body) {
        EntityData targetEntity = data.levelMaps[data.currentLevel].entityList
                .stream()
                .filter((entityData -> entityData.tilePosition.x() == target.x() &&
                        entityData.tilePosition.y() == target.y()))
                .findFirst().orElse(null);

        if(targetEntity == null)
            return false;
        if(targetEntity == body)
            return false;
        if(!(targetEntity.brain instanceof BrainActionable))
            return false;

        ((BrainActionable) targetEntity.brain).takeAction(body, targetEntity, data);
        return true;
    }
}
