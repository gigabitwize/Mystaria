package com.mystaria.game.item;

import com.mystaria.game.api.json.JsonFile;
import com.mystaria.game.api.json.serializable.Adapters;
import com.mystaria.game.item.gear.GearModifier;
import com.mystaria.game.tier.Tier;
import net.minestom.server.tag.Tag;

import java.util.HashSet;

/**
 * Created by Giovanni on 1/31/2023
 */
public final class ItemTags {

    /**
     * Converted item tag formats, use this preferably since they map the Raw Tag to an usuable Tag.
     */
    public static Tag<Item.Type> TYPE = RawItemTags.TYPE.map(Item.Type::valueOf, Enum::name);
    public static Tag<Tier> TIER = RawItemTags.TIER.map(Tier::valueOf, Enum::name);
    public static Tag<HashSet<GearModifier.GearModifierValue<?>>> MODIFIERS = RawItemTags.MODIFIERS.map(s -> JsonFile.GSON.fromJson(s, Adapters.MODIFIER_CONTAINER), values -> JsonFile.GSON.toJson(values));

    /**
     * Raw item tag formats.
     */
    public final class RawItemTags {

        public static final Tag<String> TYPE = Tag.String("type");
        public static final Tag<String> TIER = Tag.String("tier");
        public static final Tag<String> MODIFIERS = Tag.String("gameModifiers");
    }
}
