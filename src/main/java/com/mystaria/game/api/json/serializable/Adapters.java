package com.mystaria.game.api.json.serializable;

import com.google.common.reflect.TypeToken;
import com.mystaria.game.item.gear.GearModifier;

import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Created by Giovanni on 2/2/2023
 */
public class Adapters {

    public static final Type MODIFIER_CONTAINER = new TypeToken<HashSet<GearModifier.GearModifierValue<?>>>(){}.getType();
}
