package com.mystaria.game.item.gear.modifiers;

import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.GearModifier;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.item.EntityEquipEvent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 2/2/2023
 */
public class HPModifier extends GearModifier implements EventListener<EntityEquipEvent> {

    public HPModifier() {
        super("HP");
    }

    @Override
    public @NotNull Result run(@NotNull EntityEquipEvent event) {
        if (event.getEntity() instanceof MystariaPlayer player) {
            if (!event.getSlot().isArmor()) return Result.INVALID;
            ItemStack equipment = event.getEquippedItem();

            if (!Item.isItemOfType(equipment, Item.Type.ARMOR)) return Result.INVALID;
            GearItem gearItem = new GearItem(equipment);
            if (gearItem.getModifierValue(this) == null) return Result.INVALID;
            player.recaculateStats();
            return Result.SUCCESS;
        }
        return Result.INVALID;
    }

    @Override
    public @NotNull Class<EntityEquipEvent> eventType() {
        return EntityEquipEvent.class;
    }
}
