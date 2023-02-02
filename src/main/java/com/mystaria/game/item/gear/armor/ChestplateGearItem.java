package com.mystaria.game.item.gear.armor;

import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.tier.Tier;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;

import java.util.HashSet;

/**
 * Created by Giovanni on 2/2/2023
 */
public class ChestplateGearItem extends GearItem {

    public ChestplateGearItem(Tier tier, HashSet<GearModifier.GearModifierValue<?>> modifiers) {
        super(tier, Type.ARMOR, modifiers);
    }

    public ChestplateGearItem(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base = super.getItemStack();
        return Item.fixItalicKyoriShit(base
                .withMaterial(getTier().getMaterialData().getChestplate())
                .with(builder -> builder
                        .displayName(Component.text("Chestplate").color(getTier().getColorData().getTextColor()))));
    }
}
