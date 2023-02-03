package com.mystaria.game.core.player;

import com.mystaria.game.api.Function;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.modifiers.HPModifier;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

/**
 * Created by Giovanni on 2/2/2023
 */
public enum Stat {

    HP(HPModifier.class, (newItem, slot, player) -> {
        // Get of new
        float newItemHP = (float) newItem.getModifierValue(HPModifier.class).getValue();

        // Get of old
        float currentMaxHP = player.getMaxHealth();
        if (!(player.getInventory().getItemStack(slot.armorSlot()).material() == Material.AIR)) {
            ItemStack currentItemStack = player.getInventory().getItemStack(slot.armorSlot());
            if (Item.isItemOfType(currentItemStack, Item.Type.ARMOR)) {
                GearItem currentItem = new GearItem(currentItemStack);
                float addedHP = (float) currentItem.getModifierValue(HPModifier.class).getValue();
                currentMaxHP -= addedHP;
            }
        }

        // Return old + new itemhp = new calculated HP.
        float newHP = currentMaxHP + newItemHP;
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(newHP);
        player.sendMessage(currentMaxHP + " " + "HP" + " -> " + newHP + " HP");
        return newHP;
    });

    private final Class<? extends GearModifier> modifier;
    private final Function<GearItem, EquipmentSlot, MystariaPlayer, Float> updater;

    Stat(Class<? extends GearModifier> modifier, Function<GearItem, EquipmentSlot, MystariaPlayer, Float> updater) {
        this.modifier = modifier;
        this.updater = updater;
    }

    public Function<GearItem, EquipmentSlot, MystariaPlayer, Float> updater() {
        return updater;
    }

    public Class<? extends GearModifier> getModifier() {
        return modifier;
    }
}
