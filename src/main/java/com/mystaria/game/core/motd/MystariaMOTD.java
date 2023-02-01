package com.mystaria.game.core.motd;

import com.mystaria.game.core.MystariaCore;
import com.mystaria.game.core.properties.ConnectorProperties;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaMOTD implements EventListener<ServerListPingEvent> {

    public static final Component version = Component.text(MystariaCore.BUILD_VERSION).color(NamedTextColor.YELLOW);
    public static final Component header = Component.text("                 MYSTARIA").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD);
    public static final Component footer = Component.text("\n               A Minecraft MMORPG/Looter").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC);
    private final ConnectorProperties properties;

    public MystariaMOTD(ConnectorProperties properties) {
        this.properties = properties;
    }

    @Override
    public @NotNull Result run(@NotNull ServerListPingEvent event) {
        ResponseData responseData = new ResponseData();

        responseData.setDescription(version.append(header).append(footer));
        responseData.setMaxPlayer(properties.maxPlayers);
        responseData.setOnline(MinecraftServer.getConnectionManager().getOnlinePlayers().size());

        event.setResponseData(responseData);
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<ServerListPingEvent> eventType() {
        return ServerListPingEvent.class;
    }
}
