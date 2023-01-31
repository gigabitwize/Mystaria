package com.mystaria.game.api.exception;

import com.mystaria.game.item.Item;

/**
 * Created by Giovanni on 1/31/2023
 */
public class InvalidTypeForItemException extends RuntimeException {

    public InvalidTypeForItemException(Item.Type type, Class<?> incompatible) {
        super(type.name() + " is not a compatible type for " + incompatible.getSimpleName());
    }
}
