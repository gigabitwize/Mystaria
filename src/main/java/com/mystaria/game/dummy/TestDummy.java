package com.mystaria.game.dummy;

import com.google.common.collect.Sets;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import net.kyori.adventure.text.Component;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.item.ItemStack;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TestDummy {

    public static void createAt(Location location) {
        EntityCreature zombie = new EntityCreature(EntityType.ZOMBIE);
        zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(100);
        zombie.setHealth(100);
        zombie.setCustomName(Component.text("Test Dummy (Max 100 HP)"));
        zombie.setCustomNameVisible(true);
        zombie.setInstance(location.getInstance(), location.getPosition());
    }

    public static void giveTestItem(MystariaPlayer player) {
        GearModifier.RangedFloatValue damageRandomizer = new GearModifier.RangedFloatValue(DamageModifier.class, 10, 20);
        GearItem gearItem = new GearItem(Item.Type.WEAPON, Sets.newHashSet(damageRandomizer));
        ItemStack gearItemStack = gearItem.getItemStack();
        player.getInventory().addItemStack(gearItemStack);
    }
}
