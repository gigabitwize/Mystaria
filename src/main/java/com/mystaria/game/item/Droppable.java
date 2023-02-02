package com.mystaria.game.item;

import com.mystaria.game.core.instance.Location;
import net.minestom.server.entity.ItemEntity;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Giovanni on 2/2/2023
 */
public class Droppable {

    private final Item droppable;
    private final float chancePercentage;

    public Droppable(Item item, float chance) {
        this.droppable = item;
        this.chancePercentage = chance;
    }

    /**
     * Drops this item.
     */
    public void dropAt(Location location) {
        ItemEntity itemEntity = new ItemEntity(droppable.getItemStack());
        itemEntity.setInstance(location.getInstance(), location.getPosition());
    }

    public boolean shouldDrop() {
        return ThreadLocalRandom.current().nextFloat(100) < chancePercentage;
    }

    public Item getDroppable() {
        return droppable;
    }

    public float getChancePercentage() {
        return chancePercentage;
    }
}
