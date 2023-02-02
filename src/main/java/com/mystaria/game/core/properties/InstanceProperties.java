package com.mystaria.game.core.properties;

import com.mystaria.game.api.json.serializable.SerializablePos;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.world.DimensionType;

/**
 * Created by Giovanni on 1/30/2023
 */
public class InstanceProperties {

    public final SerializablePos spawn = new SerializablePos(new Pos(0, 0, 0));
    public final DimensionType dimension = DimensionType.OVERWORLD;
}
