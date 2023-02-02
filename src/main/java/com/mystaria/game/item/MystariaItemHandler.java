package com.mystaria.game.item;

import com.google.common.collect.Sets;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.GearModifierRegistry;
import com.mystaria.game.item.gear.armor.BootsGearItem;
import com.mystaria.game.item.gear.armor.ChestplateGearItem;
import com.mystaria.game.item.gear.armor.HelmetGearItem;
import com.mystaria.game.item.gear.armor.LeggingsGearItem;
import com.mystaria.game.tier.Tier;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaItemHandler {

    private final GearModifierRegistry gearModifierRegistry;

    public MystariaItemHandler() {
        this.gearModifierRegistry = new GearModifierRegistry();
    }

    /**
     * Creates a random {@link HelmetGearItem} based on a Tier.
     *
     * @param tier The helmet tier.
     */
    public HelmetGearItem randomHelmet(Tier tier) {
        HashSet<GearModifier.GearModifierValue<?>> modifiers = Sets.newHashSet();
        return new HelmetGearItem(tier, modifiers);
    }

    /**
     * Generates a random {@link Droppable} {@link HelmetGearItem}.
     */
    public Droppable randomDroppableHelmet(Tier tier) {
        float[] chanceRange = tier.getDropChances().getHelmetRange();
        return new Droppable(randomHelmet(tier), ThreadLocalRandom.current().nextFloat(chanceRange[0], chanceRange[1]));
    }

    /**
     * Creates a random {@link ChestplateGearItem} based on a Tier.
     *
     * @param tier The chestplate tier.
     */
    public ChestplateGearItem randomChestplate(Tier tier) {
        HashSet<GearModifier.GearModifierValue<?>> modifiers = Sets.newHashSet();
        return new ChestplateGearItem(tier, modifiers);
    }

    /**
     * Generates a random {@link Droppable} {@link ChestplateGearItem}.
     */
    public Droppable randomDroppableChestplate(Tier tier) {
        float[] chanceRange = tier.getDropChances().getChestplateRange();
        return new Droppable(randomChestplate(tier), ThreadLocalRandom.current().nextFloat(chanceRange[0], chanceRange[1]));
    }

    /**
     * Creates a random {@link LeggingsGearItem} based on a Tier.
     *
     * @param tier The leggings tier.
     */
    public LeggingsGearItem randomLeggings(Tier tier) {
        HashSet<GearModifier.GearModifierValue<?>> modifiers = Sets.newHashSet();
        return new LeggingsGearItem(tier, modifiers);
    }

    /**
     * Generates a random {@link Droppable} {@link LeggingsGearItem}.
     */
    public Droppable randomDroppableLeggings(Tier tier) {
        float[] chanceRange = tier.getDropChances().getChestplateRange();
        return new Droppable(randomLeggings(tier), ThreadLocalRandom.current().nextFloat(chanceRange[0], chanceRange[1]));
    }

    /**
     * Creates a random {@link BootsGearItem} based on a Tier.
     *
     * @param tier The boots tier.
     */
    public BootsGearItem randomBoots(Tier tier) {
        HashSet<GearModifier.GearModifierValue<?>> modifiers = Sets.newHashSet();
        return new BootsGearItem(tier, modifiers);
    }

    /**
     * Generates a random {@link Droppable} {@link BootsGearItem}.
     */
    public Droppable randomDroppableBoots(Tier tier) {
        float[] chanceRange = tier.getDropChances().getChestplateRange();
        return new Droppable(randomBoots(tier), ThreadLocalRandom.current().nextFloat(chanceRange[0], chanceRange[1]));
    }

    public GearModifierRegistry getGearModifierRegistry() {
        return gearModifierRegistry;
    }
}
