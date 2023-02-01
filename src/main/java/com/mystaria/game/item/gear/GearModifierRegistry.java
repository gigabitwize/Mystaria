package com.mystaria.game.item.gear;

import com.google.common.collect.Maps;
import com.mystaria.game.api.event.MystariaEventFilters;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityAttackEvent;

import java.util.HashMap;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * The {@link GearModifier} registry. All GearModifiers should be registered here.
 */
public class GearModifierRegistry {

    private final EventNode<EntityAttackEvent> weaponModifiersNode;
    private final EventNode<EntityAttackEvent> armorModifiersNode;
    private final HashMap<Class<? extends GearModifier>, GearModifier> modifierRegistry;

    /**
     * TODO append modifierNode to global combat node.
     */
    @SuppressWarnings("unchecked")
    public GearModifierRegistry() {
        this.modifierRegistry = Maps.newHashMap();

        // Listeners in this node will only execute if a player is using a weapon
        this.weaponModifiersNode = EventNode.value("weapon-modifiers", MystariaEventFilters.ENTITY_ATTACK, entity -> {
            if (!(entity instanceof MystariaPlayer player)) return false;
            return player.isHoldingItem(Item.Type.WEAPON);
        });

        this.modifierRegistry.put(DamageModifier.class, new DamageModifier());
        for (GearModifier value : this.modifierRegistry.values()) {
            if (value instanceof EventListener<?> listeningModifier)
                this.weaponModifiersNode.addListener((EventListener<? extends EntityAttackEvent>) listeningModifier);
        }

        // Listeners in this node will only execute if a player is wearing armor
        this.armorModifiersNode = EventNode.value("armor-modifiers", MystariaEventFilters.ENTITY_ATTACK, entity -> {
            if (!(entity instanceof MystariaPlayer player)) return false;
            return player.isWearingAnyArmor();
        });

        MinecraftServer.getGlobalEventHandler().addChild(weaponModifiersNode);
        MinecraftServer.getGlobalEventHandler().addChild(armorModifiersNode);
    }

    public String getNameOf(Class<? extends GearModifier> clazz) {
        return modifierRegistry.get(clazz).getName();
    }
}
