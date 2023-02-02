package com.mystaria.game.tier;

import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Created by Giovanni on 1/31/2023
 */
public enum Tier {

    T1(new TierColor(NamedTextColor.WHITE), TierMaterial.Presets.TIER_ONE, TierDropChances.Presets.TIER_ONE),
    T2(new TierColor(NamedTextColor.WHITE), TierMaterial.Presets.TIER_TWO, TierDropChances.Presets.TIER_TWO),
    T3(new TierColor(NamedTextColor.WHITE), TierMaterial.Presets.TIER_THREE, TierDropChances.Presets.TIER_THREE),
    T4(new TierColor(NamedTextColor.LIGHT_PURPLE), TierMaterial.Presets.TIER_FOUR, TierDropChances.Presets.TIER_FOUR),
    T5(new TierColor(NamedTextColor.YELLOW), TierMaterial.Presets.TIER_FIVE, TierDropChances.Presets.TIER_FIVE);

    private final TierColor colorData;
    private final TierMaterial materialData;
    private final TierDropChances dropChances;

    Tier(TierColor tierColor, TierMaterial tierMaterial, TierDropChances dropChances) {
        this.colorData = tierColor;
        this.materialData = tierMaterial;
        this.dropChances = dropChances;
    }

    public TierDropChances getDropChances() {
        return dropChances;
    }

    public TierColor getColorData() {
        return colorData;
    }

    public TierMaterial getMaterialData() {
        return materialData;
    }
}
