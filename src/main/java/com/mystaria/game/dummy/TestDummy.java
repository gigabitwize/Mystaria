package com.mystaria.game.dummy;

import com.google.common.collect.Sets;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import com.mystaria.game.monster.Monster;
import com.mystaria.game.tier.Tier;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.TimeUnit;

import java.util.List;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TestDummy {

    public static void createAt(Location location) {
        EntityCreature entityCreature = new EntityCreature(EntityType.ZOMBIE);
        entityCreature.addAIGroup(
                List.of(new MeleeAttackGoal(entityCreature, 1.2, 20, TimeUnit.SERVER_TICK)),
                List.of(new ClosestEntityTarget(entityCreature, 32, entity -> entity instanceof Player))
        );

        entityCreature.setInstance(location.getInstance(), location.getPosition());
    }

    public static void giveTestItem(MystariaPlayer player) {
        GearModifier.RangedFloatValue damageRandomizer = new GearModifier.RangedFloatValue(DamageModifier.class, 10, 20);
        GearItem gearItem = new GearItem(Item.Type.WEAPON, Sets.newHashSet(damageRandomizer));
        ItemStack gearItemStack = gearItem.getItemStack();
        player.getInventory().addItemStack(gearItemStack);
    }
}
