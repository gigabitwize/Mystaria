package com.mystaria.game.core.player;

import com.mystaria.game.api.Function;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.armor.BootsGearItem;
import com.mystaria.game.item.gear.armor.ChestplateGearItem;
import com.mystaria.game.item.gear.armor.HelmetGearItem;
import com.mystaria.game.item.gear.armor.LeggingsGearItem;
import com.mystaria.game.item.gear.modifiers.HPModifier;
import net.minestom.server.attribute.Attribute;

import java.util.Optional;

/**
 * Created by Giovanni on 2/2/2023
 */
public enum Stat {

    HP(HPModifier.class, player -> {
        float base = 20;
        if (!player.isWearingAnyArmor()) return base;
        Optional<HelmetGearItem> helmet = player.getHelmetGear();
        if (helmet.isPresent())
            base += (float) helmet.get().getModifierValue(HPModifier.class).getValue();

        Optional<ChestplateGearItem> chestplate = player.getChestplateGear();
        if (chestplate.isPresent())
            base += (float) chestplate.get().getModifierValue(HPModifier.class).getValue();

        Optional<LeggingsGearItem> leggings = player.getLeggingsGear();
        if (leggings.isPresent())
            base += (float) leggings.get().getModifierValue(HPModifier.class).getValue();

        Optional<BootsGearItem> boots = player.getBootsGear();
        if (boots.isPresent())
            base += (float) boots.get().getModifierValue(HPModifier.class).getValue();

        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(base);
        return base;
    }),
    DPS(null, new Function<Float, MystariaPlayer>() {
        @Override
        public Float execute(MystariaPlayer player) {
            return null;
        }
    }),
    ENERGY(null, new Function<Float, MystariaPlayer>() {
        @Override
        public Float execute(MystariaPlayer player) {
            return null;
        }
    });

    private final Class<? extends GearModifier> modifier;
    private final Function<Float, MystariaPlayer> updater;

    Stat(Class<? extends GearModifier> modifier, Function<Float, MystariaPlayer> updater) {
        this.modifier = modifier;
        this.updater = updater;
    }

    public Function<Float, MystariaPlayer> updater() {
        return updater;
    }

    public Class<? extends GearModifier> getModifier() {
        return modifier;
    }
}
