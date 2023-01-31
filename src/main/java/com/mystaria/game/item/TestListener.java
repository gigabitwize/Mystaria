package com.mystaria.game.item;

import com.google.common.collect.Sets;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TestListener implements EventListener<PlayerLoginEvent> {

    @Override
    public @NotNull Result run(@NotNull PlayerLoginEvent event) {
        GearModifier.RangedFloatValue damageModifier = new GearModifier.RangedFloatValue(DamageModifier.class, 5, 10);
        GearItem gearItem = new GearItem(Item.Type.WEAPON, Sets.newHashSet(damageModifier));
        ItemStack converted = gearItem.getItemStack();

        GearItem testConversion = new GearItem(converted);
        event.getPlayer().sendMessage("Test Damage: " + (float) testConversion.getModifierValue(DamageModifier.class).getValue());
        event.getPlayer().getInventory().addItemStack(testConversion.getItemStack());
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<PlayerLoginEvent> eventType() {
        return PlayerLoginEvent.class;
    }
}
