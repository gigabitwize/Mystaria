package com.mystaria.game.core.entity;

import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.instance.MystariaInstanceContainer;

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

    /**
     * Returns this entity's {@link Location}.
     */
    Location getLocation();

    /**
     * Returns this entity's current {@link MystariaInstanceContainer}.
     */
    MystariaInstanceContainer getInstance();
}
