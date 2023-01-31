package com.mystaria.game.core.entity;

import com.mystaria.game.core.instance.Location;

/**
 * Created by Giovanni on 1/31/2023
 */
public interface MystariaEntity {

    /**
     * Spawns this entity.
     */
    void spawn(Location location);

    /**
     * Despawns this entity.
     */
    void despawn();
}
