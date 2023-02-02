package com.mystaria.game.item.gear;

import com.google.common.collect.Maps;
import com.mystaria.game.api.event.MystariaEventFilters;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.modifiers.DamageModifier;
import com.mystaria.game.item.gear.modifiers.HPModifier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.trait.EntityEvent;

import java.util.HashMap;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * The {@link GearModifier} registry. All GearModifiers should be registered here.
 */
public class GearModifierRegistry {

    private final EventNode<EntityAttackEvent> weaponModifiersNode;
    private final EventNode<EntityEvent> armorModifiersNode;
    private final HashMap<Class<? extends GearModifier>, GearModifier> weaponRegistry;
    private final HashMap<Class<? extends GearModifier>, GearModifier> armorRegistry;

    /**
     * TODO append modifierNode to global combat node.
     */
    @SuppressWarnings("unchecked")
    public GearModifierRegistry() {
        this.weaponRegistry = Maps.newHashMap();
        this.weaponRegistry.put(DamageModifier.class, new DamageModifier());

        this.armorRegistry = Maps.newHashMap();
        this.armorRegistry.put(HPModifier.class, new HPModifier());

        // Listeners in this node will only execute if a player is using a weapon
        this.weaponModifiersNode = EventNode.value("weapon-modifiers", MystariaEventFilters.ENTITY_ATTACK, entity -> {
            if (!(entity instanceof MystariaPlayer player)) return false;
            return player.isHoldingItem(Item.Type.WEAPON);
        });
        for (GearModifier value : this.weaponRegistry.values()) {
            if (value instanceof EventListener<?> listeningModifier)
                this.weaponModifiersNode.addListener((EventListener<? extends EntityAttackEvent>) listeningModifier);
        }

        // Listeners in this node will only execute if a player is wearing or holding armor
        this.armorModifiersNode = EventNode.value("armor-modifiers", EventFilter.ENTITY, entity -> entity instanceof MystariaPlayer);
        for (GearModifier value : this.armorRegistry.values()) {
            if (value instanceof EventListener<?> listeningModifier)
                this.armorModifiersNode.addListener((EventListener<? extends EntityEvent>) listeningModifier);
        }

        MinecraftServer.getGlobalEventHandler().addChild(weaponModifiersNode);
        MinecraftServer.getGlobalEventHandler().addChild(armorModifiersNode);
    }

    public String getNameOf(Class<? extends GearModifier> clazz) {
        String name = null;
        if (weaponRegistry.get(clazz) != null) name = weaponRegistry.get(clazz).getName();
        if (armorRegistry.get(clazz) != null) name = armorRegistry.get(clazz).getName();
        return name;
    }
}
