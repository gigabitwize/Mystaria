package com.mystaria.game.item.gear;

import com.google.common.collect.Lists;
import com.mystaria.game.MystariaServer;
import com.mystaria.game.api.exception.InvalidTypeForItemException;
import com.mystaria.game.api.json.JsonFile;
import com.mystaria.game.api.json.serializable.Adapters;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.ItemTags;
import com.mystaria.game.tier.Tier;
import com.mystaria.game.util.PrimConvert;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.item.ItemHideFlag;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Giovanni on 1/31/2023
 */
public class GearItem implements Item {

    private final Tier tier;
    private final Type type;
    private HashSet<GearModifier.GearModifierValue<?>> modifiers;

    private ItemStack itemStack;

    /**
     * Used to create a new GearItem.
     */
    public GearItem(Tier tier, Type type, HashSet<GearModifier.GearModifierValue<?>> modifiers) {
        if (type.getCategory() != Category.GEAR)
            throw new InvalidTypeForItemException(type, getClass());
        this.tier = tier;
        this.type = type;
        this.modifiers = modifiers;
    }

    /**
     * Reads GearItem data from a {@link ItemStack}.
     */
    public GearItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.tier = itemStack.getTag(ItemTags.TIER);
        this.type = itemStack.getTag(ItemTags.TYPE);
        this.modifiers = itemStack.getTag(ItemTags.MODIFIERS);
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
            if (value.getModifierClass() == modifier)
                return value;
        }
        return null;
    }

    protected ArrayList<Component> generateModifiersLore() {
        ArrayList<Component> lines = Lists.newArrayList();
        for (GearModifier.GearModifierValue<?> modifier : modifiers) {
            // Values are always cast to int for displaying round numbers
            String name = MystariaServer.getGame().getItemHandler().getGearModifierRegistry().getNameOf(modifier.getModifierClass());
            if (modifier instanceof GearModifier.Ranged<?>) {
                int min = PrimConvert.intFrom(((GearModifier.Ranged<?>) modifier).getMin());
                int max = PrimConvert.intFrom(((GearModifier.Ranged<?>) modifier).getMax());
                // lol
                lines.add(Component.text(name + ": ").append(Component.text(min + " - " + max)).color(NamedTextColor.RED));
            } else {
                int value = PrimConvert.intFrom(modifier.getValue());
                lines.add(Component.text(name + ": ").append(Component.text("+" + value)).color(NamedTextColor.RED));
            }
        }
        return lines;
    }


    @Override
    public ItemStack getItemStack() {
        if (itemStack != null) return itemStack;
        itemStack = ItemStack
                .builder(Material.STONE)
                .displayName(Component.text("Gear Item"))
                .lore(generateModifiersLore())
                .set(ItemTags.TIER, tier)
                .set(ItemTags.TYPE, type)
                .set(ItemTags.MODIFIERS, modifiers)
                .meta(builder -> builder.hideFlag(ItemHideFlag.values())
                        .unbreakable(true)
                        .damage(0)
                        .build())
                .build();
        return itemStack;
    }

    @Override
    public Type getType() {
        return type;
    }

    public Tier getTier() {
        return tier;
    }
}
