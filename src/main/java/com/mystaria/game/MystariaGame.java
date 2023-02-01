package com.mystaria.game;

import com.mystaria.game.core.MystariaCore;
import com.mystaria.game.item.MystariaItemHandler;


/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * The actual Mystaria game, manages things like entities, realms, dungeons, items etc.
 */
public class MystariaGame {

    private final MystariaCore core;
    private final MystariaItemHandler itemHandler;

    public MystariaGame(MystariaCore core) {
        this.core = core;
        this.itemHandler = new MystariaItemHandler();
    }

    public MystariaCore getCore() {
        return core;
    }

    public MystariaItemHandler getItemHandler() {
        return itemHandler;
    }
}
