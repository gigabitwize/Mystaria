package com.mystaria.game.item.gear;

import com.mystaria.game.api.event.MystariaEventFilters;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityAttackEvent;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * The {@link GearModifier} registry. All GearModifiers should be registered here.
 */
public class GearModifierRegistry {

    private final EventNode<EntityAttackEvent> weaponModifiersNode;
    private final EventNode<EntityAttackEvent> armorModifiersNode;

    /**
     * TODO append modifierNode to global combat node.
     */
    public GearModifierRegistry() {
        // Listeners in this node will only execute if a player is using a weapon
        this.weaponModifiersNode = EventNode.value("weapon-modifiers", MystariaEventFilters.ENTITY_ATTACK, entity -> {
            MystariaPlayer player = (MystariaPlayer) entity;
            return player.isHoldingItem(Item.Type.WEAPON);
        })
                .addListener(new DamageModifier());

        // Listeners in this node will only execute if a player is wearing armor
        this.armorModifiersNode = EventNode.value("armor-modifiers", MystariaEventFilters.ENTITY_ATTACK, entity -> {
            MystariaPlayer player = (MystariaPlayer) entity;
            return player.isWearingAnyArmor();
        });

        MinecraftServer.getGlobalEventHandler().addChild(weaponModifiersNode);
        MinecraftServer.getGlobalEventHandler().addChild(armorModifiersNode);
    }
}
