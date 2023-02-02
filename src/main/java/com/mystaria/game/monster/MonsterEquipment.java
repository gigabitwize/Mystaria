package com.mystaria.game.monster;

import com.google.common.collect.Sets;
import com.mystaria.game.MystariaServer;
import com.mystaria.game.item.Droppable;
import net.minestom.server.entity.EquipmentSlot;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Giovanni on 2/2/2023
 * <p>
 * A {@link Monster}'s equipment. This purely used for drops & visualization, it does not
 * affect the Monster's stats (like HP).
 */
public class MonsterEquipment {

    private final Monster owner;

    @Nullable
    private Droppable helmet, chestplate, leggings, boots, hand;

    public MonsterEquipment(Monster owner) {
        this.owner = owner;
        randomize();
    }

    /**
     * Randomizes the equipment.
     */
    public void randomize() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            this.helmet = MystariaServer.getGame().getItemHandler().randomDroppableHelmet(owner.getTier());
            owner.setHelmet(helmet.getDroppable().getItemStack());
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            this.chestplate = MystariaServer.getGame().getItemHandler().randomDroppableChestplate(owner.getTier());
            owner.setChestplate(chestplate.getDroppable().getItemStack());
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            this.leggings = MystariaServer.getGame().getItemHandler().randomDroppableLeggings(owner.getTier());
            owner.setLeggings(leggings.getDroppable().getItemStack());
        }
        if (ThreadLocalRandom.current().nextBoolean()) {
            this.boots = MystariaServer.getGame().getItemHandler().randomDroppableBoots(owner.getTier());
            owner.setBoots(boots.getDroppable().getItemStack());
        }
    }

    /**
     * Returns a random selection of equipment drops.
     */
    public HashSet<Droppable> getRandomDrops() {
        HashSet<Droppable> droppables = Sets.newHashSet();
        if (helmet != null && helmet.shouldDrop()) droppables.add(helmet);
        if (chestplate != null && chestplate.shouldDrop()) droppables.add(chestplate);
        if (leggings != null && leggings.shouldDrop()) droppables.add(leggings);
        if (boots != null && boots.shouldDrop()) droppables.add(boots);
        if (hand != null && hand.shouldDrop()) droppables.add(hand);
        return droppables;
    }
}
