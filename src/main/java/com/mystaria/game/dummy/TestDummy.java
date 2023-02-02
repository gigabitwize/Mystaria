package com.mystaria.game.dummy;

import com.google.common.collect.Sets;
import com.mystaria.game.api.json.JsonFile;
import com.mystaria.game.api.json.serializable.Adapters;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.item.gear.armor.ChestplateGearItem;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import com.mystaria.game.item.gear.modifiers.HPModifier;
import com.mystaria.game.item.gear.weapon.SwordGearItem;
import com.mystaria.game.monster.Monster;
import com.mystaria.game.tier.Tier;
import net.minestom.server.entity.EntityType;
import net.minestom.server.item.ItemStack;

import java.util.HashSet;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TestDummy {

    public static void createAt(Location location) {
        Monster monster = new Monster(Tier.T5, "Skeleton Man", EntityType.SKELETON);
        monster.spawn(location);
        monster.getMonsterEquipment().randomize();
    }

    public static void giveTestItem(MystariaPlayer player) {
        GearModifier.Value<Float> hp = new GearModifier.Value<>(HPModifier.class, 500F);
        ChestplateGearItem gearItem = new ChestplateGearItem(Tier.T5, Sets.newHashSet(hp));
        ItemStack itemStack = gearItem.getItemStack();
        player.getInventory().addItemStack(itemStack);

       // GearModifier.RangedFloatValue damageRandomizer = new GearModifier.RangedFloatValue(DamageModifier.class, 10, 20);
        //SwordGearItem sword = new SwordGearItem(Tier.T5, Sets.newHashSet(damageRandomizer));
        //player.getInventory().addItemStack(sword.getItemStack());
    }
}
