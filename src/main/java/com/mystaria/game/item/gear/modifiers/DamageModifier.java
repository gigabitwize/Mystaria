package com.mystaria.game.item.gear.modifiers;

import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.entity.EntityAttackEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/31/2023
 */
public class DamageModifier extends GearModifier implements EventListener<EntityAttackEvent> {

    public DamageModifier() {
        super("Damage");
    }

    @Override
    public @NotNull Result run(@NotNull EntityAttackEvent event) {
        MystariaPlayer mystariaPlayer = (MystariaPlayer) event.getEntity();
        LivingEntity livingEntity = (LivingEntity) event.getTarget();

        GearItem weapon = new GearItem(mystariaPlayer.getItemInMainHand());
        if(weapon.getModifierValue(this) == null) return Result.INVALID;

        float damage = (float) weapon.getModifierValue(this).getValue();

        livingEntity.damage(DamageType.fromPlayer(mystariaPlayer), damage);
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<EntityAttackEvent> eventType() {
        return EntityAttackEvent.class;
    }
}
