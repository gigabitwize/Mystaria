package com.mystaria.game.item;

import com.mystaria.game.item.gear.GearModifierRegistry;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaItemHandler {

    private final GearModifierRegistry gearModifierRegistry;

    public MystariaItemHandler() {
        this.gearModifierRegistry = new GearModifierRegistry();
    }

    public GearModifierRegistry getGearModifierRegistry() {
        return gearModifierRegistry;
    }
}
