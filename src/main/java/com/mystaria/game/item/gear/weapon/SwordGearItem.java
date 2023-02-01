package com.mystaria.game.item.gear.weapon;

import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.tier.Tier;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;

import java.util.HashSet;

/**
 * Created by Giovanni on 2/1/2023
 */
public class SwordGearItem extends GearItem {

    public SwordGearItem(Tier tier, HashSet<GearModifier.GearModifierValue<?>> modifiers) {
        super(tier, Type.WEAPON, modifiers);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base = super.getItemStack();
        return Item.fixItalicKyoriShit(base
                .withMaterial(getTier().getMaterialData().getSword())
                .with(builder -> builder
                        .displayName(Component.text("Sword").color(getTier().getColorData().getTextColor()))));
    }
}
