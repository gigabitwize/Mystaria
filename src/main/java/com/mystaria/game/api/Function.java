package com.mystaria.game.api;

/**
 * Created by Giovanni on 2/2/2023
 */
public interface Function<A, B> {

    A execute(B b);
}
