package com.mystaria.game.tier;

import net.minestom.server.item.Material;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TierMaterial {

    private final Material helmet, chestplate, leggings, boots, staff, polearm, axe, sword;

    /**
     * I've decided to do it this way for IF, and big IF, we may want any new tiers in the future,
     * implementing them would be easier this way.
     */
    public TierMaterial(Material helmet, Material chestplate, Material leggings, Material boots, Material staff, Material polearm, Material axe, Material sword) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.staff = staff;
        this.polearm = polearm;
        this.axe = axe;
        this.sword = sword;
    }

    public Material getHelmet() {
        return helmet;
    }

    public Material getChestplate() {
        return chestplate;
    }

    public Material getLeggings() {
        return leggings;
    }

    public Material getBoots() {
        return boots;
    }

    public Material getStaff() {
        return staff;
    }

    public Material getAxe() {
        return axe;
    }

    public Material getPolearm() {
        return polearm;
    }

    public Material getSword() {
        return sword;
    }

    public final class Presets {

        public static final TierMaterial TIER_ONE = new TierMaterial(
                Material.LEATHER_HELMET,
                Material.LEATHER_CHESTPLATE,
                Material.LEATHER_LEGGINGS,
                Material.LEATHER_BOOTS,
                Material.WOODEN_HOE,
                Material.WOODEN_SHOVEL,
                Material.WOODEN_AXE,
                Material.WOODEN_SWORD);

        public static final TierMaterial TIER_TWO = new TierMaterial(
                Material.CHAINMAIL_HELMET,
                Material.CHAINMAIL_CHESTPLATE,
                Material.CHAINMAIL_LEGGINGS,
                Material.CHAINMAIL_BOOTS,
                Material.STONE_HOE,
                Material.STONE_SHOVEL,
                Material.STONE_AXE,
                Material.STONE_SWORD);

        public static final TierMaterial TIER_THREE = new TierMaterial(
                Material.IRON_HELMET,
                Material.IRON_CHESTPLATE,
                Material.IRON_LEGGINGS,
                Material.IRON_BOOTS,
                Material.IRON_HOE,
                Material.IRON_SHOVEL,
                Material.IRON_AXE,
                Material.IRON_SWORD);

        public static final TierMaterial TIER_FOUR = new TierMaterial(
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS,
                Material.DIAMOND_HOE,
                Material.DIAMOND_SHOVEL,
                Material.DIAMOND_AXE,
                Material.DIAMOND_SWORD);

        public static final TierMaterial TIER_FIVE = new TierMaterial(
                Material.GOLDEN_HELMET,
                Material.GOLDEN_CHESTPLATE,
                Material.GOLDEN_LEGGINGS,
                Material.GOLDEN_BOOTS,
                Material.GOLDEN_HOE,
                Material.GOLDEN_SHOVEL,
                Material.GOLDEN_AXE,
                Material.GOLDEN_SWORD);
    }
}
