package com.mystaria.game.core.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Giovanni on 1/30/2023
 */
public class Logging {

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final Class<?> clazz;
    private final String name;

    public Logging(Class<?> clazz) {
        this.clazz = clazz;
        this.name = clazz.getSimpleName();
    }

    public void info(String out) {
        System.out.println(getTime() + " (" + Thread.currentThread().getName() + "@" + name + ") - INFO - " + out);
    }

    public void warn(String out) {
        System.out.println(getTime() + " (" + Thread.currentThread().getName() + "@" + name + ") - WARN - " + out);
    }

    public void severe(String out) {
        System.out.println(getTime() + " (" + Thread.currentThread().getName() + "@" + name + ") - SEVERE - " + out);
    }

    private String getTime() {
        return "[" + TIME_FORMAT.format(LocalDateTime.now()) + "]";
    }
}
