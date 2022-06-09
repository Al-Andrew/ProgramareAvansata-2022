package com.pearl.records;

import com.pearl.update.brains.*;

public enum Entity {
    PLAYER(PlayerBrain.class, new StatSheet(10, 10, 5, 5), "attacked"),
    UP_STAIR(StairUpBrain.class),
    DOWN_STAIR(StairDownBrain.class),
    DOOR,
    RAT(RandomMoveAttackBrain.class, new StatSheet(2, 2, 2, 1), "scratched"),
    HEALTH_UP(HealthUpBrain.class),
    FULL_HEALTH(FullHealthBrain.class),
    STRENGTH_UP(StrengthUpBrain.class),
    DEFENCE_UP(DefenceUpBrain.class),
    AMULET(AmuletBrain.class)
    ;


    private final Class<?> brainClass;
    private final boolean solid;
    private final StatSheet baseStats;
    public final String attackPhrase;

    Entity(Class<?> brainClass) {
        this.brainClass = brainClass;
        this.solid = true;
        this.baseStats = StatSheet.INDESTRUCTIBLE;
        this.attackPhrase = null;
    }

    Entity(Class<?> brainClass, StatSheet stats, String attackPhrase) {
        this.brainClass = brainClass;
        this.solid = true;
        this.baseStats = new StatSheet(stats);
        this.attackPhrase = attackPhrase;
    }

    Entity() {
        this.brainClass = EmptyBrain.class;
        this.solid = true;
        this.baseStats = StatSheet.INDESTRUCTIBLE;
        this.attackPhrase = null;
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
