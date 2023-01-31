package com.mystaria.game.api.event;

import net.minestom.server.entity.Entity;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.entity.EntityAttackEvent;

/**
 * Created by Giovanni on 1/31/2023
 */
public final class MystariaEventFilters {

    public static final EventFilter<EntityAttackEvent, Entity> ENTITY_ATTACK = EventFilter.from(EntityAttackEvent.class, Entity.class, EntityAttackEvent::getEntity);
}
