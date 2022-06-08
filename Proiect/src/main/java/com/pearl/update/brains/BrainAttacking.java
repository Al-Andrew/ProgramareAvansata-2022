package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.raylib.Raylib;

public interface BrainAttacking {

    default boolean attack(Raylib.Vector2 target, GameData data, EntityData body) {
        EntityData targetEntity = data.levelMaps[GameData.currentLevel].entityList
                .stream()
                .filter((entityData -> entityData.tilePosition.x() == target.x() &&
                        entityData.tilePosition.y() == target.y()))
                .findFirst().orElse(null);

        if(targetEntity == null)
            return false;
        if(targetEntity == body)
            return false;
        if(!(targetEntity.brain instanceof BrainAttackable))
            return false;
        ((BrainAttackable) targetEntity.brain).takeAttack(body, targetEntity);

        return true;
    }
}
