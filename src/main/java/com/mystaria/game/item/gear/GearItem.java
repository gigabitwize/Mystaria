package com.mystaria.game.item.gear;

import com.mystaria.game.api.exception.InvalidTypeForItemException;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.ItemTags;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Giovanni on 1/31/2023
 */
public class GearItem implements Item {

    private final Type type;
    private final HashSet<GearModifier.GearModifierValue<?>> modifiers;

    private ItemStack itemStack;

    /**
     * Used to create a new GearItem.
     */
    public GearItem(Type type, HashSet<GearModifier.GearModifierValue<?>> modifiers) {
        if (type.getCategory() != Category.GEAR)
            throw new InvalidTypeForItemException(type, getClass());
        this.type = type;
        this.modifiers = modifiers;

    }

    /**
     * Reads GearItem data from a {@link ItemStack}.
     */
    public GearItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.type = itemStack.getTag(ItemTags.TYPE);
        this.modifiers = itemStack.getTag(ItemTags.MODIFIERS).convert();
    }


    /**
     * Returns this gear item's stats.
     */
    public Set<GearModifier.GearModifierValue<?>> getModifiers() {
        return modifiers;
    }


    public GearModifier.GearModifierValue<?> getModifierValue(GearModifier modifier) {
        return getModifierValue(modifier.getClass());
    }

    public GearModifier.GearModifierValue<?> getModifierValue(Class<? extends GearModifier> modifier) {
        for (GearModifier.GearModifierValue<?> value : modifiers) {
            if (value.getModifierClass() == modifier) return value;
        }
        return null;
    }

    @Override
    public ItemStack getItemStack() {
        if (itemStack != null) return itemStack;
        itemStack = ItemStack
                .builder(Material.IRON_AXE)
                .displayName(Component.text("Battle Axe"))
                .set(ItemTags.TYPE, type)
                .set(ItemTags.MODIFIERS, new GearModifierContainer(modifiers))
                .build();

        return itemStack;
    }

    @Override
    public Type getType() {
        return type;
    }
}
