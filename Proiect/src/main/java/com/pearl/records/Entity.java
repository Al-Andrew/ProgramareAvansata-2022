package com.pearl.records;

import com.pearl.update.brains.*;

public enum Entity {
    PLAYER(PlayerBrain.class, new StatSheet(10, 10, 5, 5)),
    UP_STAIR(StairUpBrain.class),
    DOWN_STAIR(StairDownBrain.class),
    DOOR,
    GOBLIN(RandomMoveAttackBrain.class, new StatSheet(2, 2, 2, 1)),
    ;


    private final Class<?> brainClass;
    private final boolean solid;
    private final StatSheet baseStats;

    Entity(Class<?> brain, boolean solid, StatSheet baseStats) {
        this.brainClass = brain;
        this.solid = solid;
        this.baseStats = baseStats;
    }

    Entity(Class<?> brainClass) {
        this.brainClass = brainClass;
        this.solid = true;
        this.baseStats = StatSheet.INDESTRUCTIBLE;
    }

    Entity(Class<?> brainClass, StatSheet stats) {
        this.brainClass = brainClass;
        this.solid = true;
        this.baseStats = new StatSheet(stats);
    }

    Entity() {
        this.brainClass = EmptyBrain.class;
        this.solid = true;
        this.baseStats = StatSheet.INDESTRUCTIBLE;
    }

    public Class getBrainClass() {
        return brainClass;
    }

    public boolean isSolid() {
        return solid;
    }

    public StatSheet getBaseStats() {
        return new StatSheet(baseStats.maxHealth, baseStats.maxHealth, baseStats.strength, baseStats.defence);
    }
}
