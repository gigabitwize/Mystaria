package com.mystaria.game.core.instance;

import com.mystaria.game.core.instance.generators.FlatTerrainGenerator;
import com.mystaria.game.core.player.MystariaPlayer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created by Giovanni on 1/30/2023
 * <p>
 * Default Mystaria instance container. This type of instance is only stored in memory,
 * if you want the instance to be saved and loadable to/from disk, use a {@link CachedMystariaInstanceContainer}.
 */
public class MystariaInstanceContainer extends InstanceContainer {

    private final MystariaInstanceHandler instanceHandler;
    private Location spawnPosition;

    /**
     * @param spawnPosition The spawn coordinates.
     * @param dimensionType The dimension type of this instance.
     */
    public MystariaInstanceContainer(MystariaInstanceHandler instanceHandler, Pos spawnPosition, DimensionType dimensionType) {
        super(UUID.randomUUID(), dimensionType, null);
        this.instanceHandler = instanceHandler;
        this.spawnPosition = new Location(this, spawnPosition);
    }

    /**
     * @param chunkLoader The chunk save & load manager, used to save & read instances to/from disk.
     */
    public MystariaInstanceContainer(MystariaInstanceHandler instanceHandler, Pos spawnPosition, DimensionType dimensionType, IChunkLoader chunkLoader) {
        super(UUID.randomUUID(), dimensionType, chunkLoader);
        this.instanceHandler = instanceHandler;
        this.spawnPosition = new Location(this, spawnPosition);
    }

    /**
     * Unloads this instance and tries to find a new available instance for its players.
     * <p>
     * If no other instance is found, the player will be kicked.
     */
    public void unload() {
        this.instanceHandler.getRandomInstance().ifPresentOrElse(newMystariaInstance -> {
            for (Player player : getPlayers()) {
                MystariaPlayer mystariaPlayer = (MystariaPlayer) player;
                mystariaPlayer.setInstance(newMystariaInstance);
                mystariaPlayer.setRespawnPoint(newMystariaInstance.getSpawn().getPosition());
                mystariaPlayer.sendMessage("Your previous instance was unloaded, you have been teleported to a new one");
            }
        }, () -> {
            for (Player player : getPlayers()) {
                MystariaPlayer mystariaPlayer = (MystariaPlayer) player;
                mystariaPlayer.kick("Could not find a new instance for you");
            }
        });
    }

    /**
     * Returns this instance's spawn {@link Location}.
     */
    public Location getSpawn() {
        return spawnPosition;
    }

    /**
     * Sets this instance's spawn {@link Location}.
     */
    public void setSpawn(Pos pos) {
        this.spawnPosition = new Location(this, pos);
    }

    /**
     * Registers this instance.
     */
    public void register() {
        MinecraftServer.getInstanceManager().registerInstance(this);
    }

    @Override
    public boolean placeBlock(BlockHandler.@NotNull Placement placement) {
        return super.placeBlock(placement);
    }

    @Override
    public boolean breakBlock(@NotNull Player player, @NotNull Point blockPosition) {
        return super.breakBlock(player, blockPosition);
    }
}
