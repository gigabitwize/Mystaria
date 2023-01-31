package com.mystaria.game.core.event;

import com.mystaria.game.core.player.MystariaPlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaJoinEvent implements PlayerEvent {

    private final MystariaPlayer player;

    public MystariaJoinEvent(MystariaPlayer player) {
        this.player = player;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
