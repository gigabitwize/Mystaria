package com.mystaria.game.api;

/**
 * Created by Giovanni on 2/3/2023
 */
public interface Function<A, B, C, R> {

    R execute(A refA, B refB, C refC);
}
