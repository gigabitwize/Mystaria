package com.mystaria.game.api.file.serializable;

import com.mystaria.game.api.Convertable;
import net.minestom.server.coordinate.Pos;

/**
 * Created by Giovanni on 1/30/2023
 */
public class SerializablePos implements Convertable<Pos> {

    private final double x, y, z;
    private final float yaw, pitch;

    public SerializablePos(Pos pos) {
        this.x = pos.x();
        this.y = pos.y();
        this.z = pos.z();
        this.yaw = pos.yaw();
        this.pitch = pos.pitch();
    }

    @Override
    public Pos convert() {
        return new Pos(x, y, z, yaw, pitch);
    }
}
