package com.mystaria.game.core.player;

import com.mystaria.game.core.database.MystariaPlayerData;
import com.mystaria.game.item.Item;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayer extends Player {

    private final MystariaPlayerData playerData;

    public MystariaPlayer(@NotNull MystariaPlayerData playerData, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(playerData.uuid, username, playerConnection);
        this.playerData = playerData;
        this.playerData.username = username;
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
     * Returns this player's {@link MystariaPlayerData}. This is the data that is
     * actually being stored in the {@link com.mystaria.game.core.database.MystariaSQL}.
     */
    public MystariaPlayerData getPlayerData() {
        return playerData;
    }
}
