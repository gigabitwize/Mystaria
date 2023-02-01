package com.mystaria.game.item;

import com.mystaria.game.api.file.JsonFile;
import com.mystaria.game.item.gear.GearModifierContainer;
import net.minestom.server.tag.Tag;

/**
 * Created by Giovanni on 1/31/2023
 */
public final class ItemTags {

    /**
     * Converted item tag formats, use this preferably since they map the Raw Tag to an usuable Tag.
     */
    public static Tag<Item.Type> TYPE = RawItemTags.TYPE.map(Item.Type::valueOf, Enum::name);
    public static Tag<GearModifierContainer> MODIFIERS = RawItemTags.MODIFIERS.map(s -> JsonFile.GSON.fromJson(s, GearModifierContainer.class), gearModifierContainer -> JsonFile.GSON.toJson(gearModifierContainer));

    /**
     * Raw item tag formats.
     */
    public final class RawItemTags {

        public static final Tag<String> TYPE = Tag.String("type");
        public static final Tag<String> MODIFIERS = Tag.String("modifiers");
    }
}
