package com.mystaria.game.core.instance;

import net.minestom.server.coordinate.Pos;

/**
 * Created by Giovanni on 1/30/2023
 */
public final class Location {

    private final MystariaInstanceContainer mystariaInstance;
    private final Pos position;

    public Location(MystariaInstanceContainer mystariaInstance, Pos pos) {
        this.mystariaInstance = mystariaInstance;
        this.position = pos;
    }

    public MystariaInstanceContainer getInstance() {
        return mystariaInstance;
    }

    public Pos getPosition() {
        return position;
    }
}
