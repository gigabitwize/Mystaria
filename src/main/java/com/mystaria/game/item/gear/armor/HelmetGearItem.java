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
public class HelmetGearItem extends GearItem {

    public HelmetGearItem(Tier tier, HashSet<GearModifier.GearModifierValue<?>> modifiers) {
        super(tier, Type.ARMOR, modifiers);
    }

    public HelmetGearItem(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base = super.getItemStack();
        return Item.fixItalicKyoriShit(base
                .withMaterial(getTier().getMaterialData().getHelmet())
                .with(builder -> builder
                        .displayName(Component.text("Helmet").color(getTier().getColorData().getTextColor()))));
    }
}
