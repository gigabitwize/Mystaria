package com.mystaria.game.item.gear;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * A gear stat modifier like DPS, HPS or Damage.
 */
public class GearModifier {

    private final String name;

    public GearModifier(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this modifier.
     */
    public String getName() {
        return name;
    }

    /**
     * A GearModifier value, like the amount of damage a DamageModifier does.
     *
     * This is not the actual implementation of a modifier, but merely just what determines the value
     * that a modifier will use.
     */
    public interface GearModifierValue<A> {

        /**
         * Class of the {@link GearModifier} this value is made for.
         */
        Class<? extends GearModifier> getModifierClass();

        /**
         * The value of the modifier.
         */
        A getValue();

    }

    /**
     * A persistent value like Armor's HP.
     */
    public static class Value<A> implements GearModifierValue<A> {

        private final Class<? extends GearModifier> modifier;
        private final A value;

        public Value(Class<? extends GearModifier> modifier, A type) {
            this.modifier = modifier;
            this.value = type;
        }

        @Override
        public Class<? extends GearModifier> getModifierClass() {
            return modifier;
        }

        @Override
        public A getValue() {
            return value;
        }
    }

    /**
     * Returns a random Double between a min and a max.
     */
    public static class RangedDoubleValue implements GearModifierValue<Double> {

        private final Class<? extends GearModifier> modifier;
        private final double min, max;

        public RangedDoubleValue(Class<? extends GearModifier> modifier, double min, double max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Class<? extends GearModifier> getModifierClass() {
            return modifier;
        }

        @Override
        public Double getValue() {
            return ThreadLocalRandom.current().nextDouble(min, max);
        }
    }

    /**
     * Returns a random Integer between a min and a max.
     */
    public static class RangedIntValue implements GearModifierValue<Integer> {

        private final Class<? extends GearModifier> modifier;
        private final int min, max;

        public RangedIntValue(Class<? extends GearModifier> modifier, int min, int max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Class<? extends GearModifier> getModifierClass() {
            return modifier;
        }

        @Override
        public Integer getValue() {
            return ThreadLocalRandom.current().nextInt(min, max);
        }
    }

    /**
     * Returns a random Float between a min and a max.
     */
    public static class RangedFloatValue implements GearModifierValue<Float> {

        private final Class<? extends GearModifier> modifier;
        private final float min, max;

        public RangedFloatValue(Class<? extends GearModifier> modifier, float min, float max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Class<? extends GearModifier> getModifierClass() {
            return modifier;
        }

        @Override
        public Float getValue() {
            return ThreadLocalRandom.current().nextFloat(min, max);
        }
    }
}
