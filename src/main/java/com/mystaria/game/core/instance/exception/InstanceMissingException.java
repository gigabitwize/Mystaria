package com.mystaria.game.core.instance.exception;

/**
 * Created by Giovanni on 1/30/2023
 */
public class InstanceMissingException extends RuntimeException {

    public InstanceMissingException(String instancePath) {
        super(instancePath + " does not exist in the instances directory");
    }
}
