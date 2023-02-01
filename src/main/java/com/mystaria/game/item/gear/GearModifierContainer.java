package com.mystaria.game.item.gear;

import com.mystaria.game.api.Convertable;

import java.util.HashSet;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * The {@link GearModifier} container. This is only used for serialization purposes.
 */
public class GearModifierContainer implements Convertable<HashSet<GearModifier.GearModifierValue<?>>> {

    private final HashSet<GearModifier.GearModifierValue<?>> container;

    public GearModifierContainer(HashSet<GearModifier.GearModifierValue<?>> values) {
        this.container = values;
    }

    @Override
    public HashSet<GearModifier.GearModifierValue<?>> convert() {
        return container;
    }
}
