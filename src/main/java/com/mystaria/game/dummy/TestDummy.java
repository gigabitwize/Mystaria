package com.mystaria.game.dummy;

import com.google.common.collect.Sets;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import com.mystaria.game.item.gear.weapon.SwordGearItem;
import com.mystaria.game.monster.Monster;
import com.mystaria.game.tier.Tier;
import net.minestom.server.entity.EntityType;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TestDummy {

    public static void createAt(Location location) {
        Monster monster = new Monster(Tier.T5, "Skeleton Man", EntityType.SKELETON);
        monster.spawn(location);
    }

    public static void giveTestItem(MystariaPlayer player) {
        GearModifier.RangedFloatValue damageRandomizer = new GearModifier.RangedFloatValue(DamageModifier.class, 10, 20);
        SwordGearItem sword = new SwordGearItem(Tier.T5, Sets.newHashSet(damageRandomizer));
        player.getInventory().addItemStack(sword.getItemStack());
    }
}
