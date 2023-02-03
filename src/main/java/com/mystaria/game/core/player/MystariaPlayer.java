package com.mystaria.game.core.player;

import com.google.common.collect.Maps;
import com.mystaria.game.core.database.MystariaPlayerData;
import com.mystaria.game.core.instance.CachedMystariaInstanceContainer;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.item.Item;
import com.mystaria.game.item.gear.GearItem;
import com.mystaria.game.item.gear.armor.BootsGearItem;
import com.mystaria.game.item.gear.armor.ChestplateGearItem;
import com.mystaria.game.item.gear.armor.HelmetGearItem;
import com.mystaria.game.item.gear.armor.LeggingsGearItem;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayer extends Player {

    private final MystariaPlayerData playerData;
    private final HashMap<Stat, Float> playerStats;

    public MystariaPlayer(@NotNull MystariaPlayerData playerData, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(playerData.uuid, username, playerConnection);
        this.playerData = playerData;
        this.playerData.username = username;
        this.playerStats = Maps.newHashMap();
    }

    public void recalculateStatsWith(GearItem newItem, EquipmentSlot slot) {
        playerStats.forEach((stat, value) -> {
            float newValue = stat.updater().execute(newItem, slot, this);
            playerStats.put(stat, newValue);
        });
    }

    /**
     * Recalculates the player's {@link Stat}s.
     */
    @SuppressWarnings("unchecked")
    public void recaculateStats() {
        if (!isWearingAnyArmor()) {
            playerStats.put(Stat.HP, 20F);
            return;
        }

        getHelmetGear().ifPresent(helmetGearItem -> recalculateStatsWith(helmetGearItem, EquipmentSlot.HELMET));
        getChestplateGear().ifPresent(chestplateGearItem -> recalculateStatsWith(chestplateGearItem, EquipmentSlot.CHESTPLATE));
        getLeggingsGear().ifPresent(leggingsGearItem -> recalculateStatsWith(leggingsGearItem, EquipmentSlot.LEGGINGS));
        getBootsGear().ifPresent(bootsGearItem -> recalculateStatsWith(bootsGearItem, EquipmentSlot.BOOTS));

    }

    /**
     * Returns whether this player is holding a specific item {@link com.mystaria.game.item.Item.Type} in their MAIN_HAND.
     *
     * @param type The item type.
     */
    public boolean isHoldingItem(Item.Type type) {
        return Item.isItemOfType(getItemInMainHand(), type);
    }

    /**
     * Returns whether this player is holding an item of a specific {@link com.mystaria.game.item.Item.Category} in their MAIN_HAND.
     *
     * @param category The item category.
     */
    public boolean isHoldingItem(Item.Category category) {
        return Item.isItemOfCategory(getItemInMainHand(), category);
    }

    /**
     * Returns whether this player is wearing any {@link com.mystaria.game.item.Item.Type#ARMOR} at all.
     */
    public boolean isWearingAnyArmor() {
        return Item.isItemOfType(getInventory().getHelmet(), Item.Type.ARMOR)
                || Item.isItemOfType(getInventory().getChestplate(), Item.Type.ARMOR)
                || Item.isItemOfType(getInventory().getLeggings(), Item.Type.ARMOR)
                || Item.isItemOfType(getInventory().getBoots(), Item.Type.ARMOR);
    }

    /**
     * Returns this player's current {@link Location}.
     */
    public Location getLocation() {
        return new Location(getInstance(), getPosition());
    }

    @Override
    public @Nullable CachedMystariaInstanceContainer getInstance() {
        return (CachedMystariaInstanceContainer) super.getInstance();
    }

    /**
     * Returns this player's {@link MystariaPlayerData}. This is the data that is
     * actually being stored in the {@link com.mystaria.game.core.database.MystariaSQL}.
     */
    public MystariaPlayerData getPlayerData() {
        return playerData;
    }

    public Optional<HelmetGearItem> getHelmetGear() {
        if (!Item.isItemOfType(getInventory().getHelmet(), Item.Type.ARMOR)) return Optional.empty();
        return Optional.of(new HelmetGearItem(getInventory().getHelmet()));
    }

    public Optional<ChestplateGearItem> getChestplateGear() {
        if (!Item.isItemOfType(getInventory().getHelmet(), Item.Type.ARMOR)) return Optional.empty();
        return Optional.of(new ChestplateGearItem(getInventory().getChestplate()));
    }

    public Optional<LeggingsGearItem> getLeggingsGear() {
        if (!Item.isItemOfType(getInventory().getHelmet(), Item.Type.ARMOR)) return Optional.empty();
        return Optional.of(new LeggingsGearItem(getInventory().getLeggings()));
    }

    public Optional<BootsGearItem> getBootsGear() {
        if (!Item.isItemOfType(getInventory().getHelmet(), Item.Type.ARMOR)) return Optional.empty();
        return Optional.of(new BootsGearItem(getInventory().getBoots()));
    }

}
