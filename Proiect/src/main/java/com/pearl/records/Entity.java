package com.pearl.records;

import com.pearl.update.brains.*;

public enum Entity {
    PLAYER(PlayerBrain.class),
    UP_STAIR(StairUpBrain.class),
    DOWN_STAIR(StairDownBrain.class),
    DOOR,
    GOBLIN(RandomMoveAttackBrain.class),
    ;

    private final Class<?> brainClass;
    private final boolean solid;

    Entity(Class<?> brain, boolean solid) {
        this.brainClass = brain;
        this.solid = solid;
    }

    Entity(Class<?> brainClass) {
        this.brainClass = brainClass;
        this.solid = true;
    }

    Entity() {
        this.brainClass = EmptyBrain.class;
        this.solid = true;
    }

    public Class getBrainClass() {
        return brainClass;
    }

    public boolean isSolid() {
        return solid;
    }
}
