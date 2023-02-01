package com.mystaria.game.item.gear;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Giovanni on 1/31/2023
 * <p>
 * A gear stat modifier like DPS, HPS or Damage.
 */
public abstract class GearModifier {

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
     * Ranged modifier.
     */
    public interface Ranged<A> {

        A getMin();

        A getMax();
    }

    /**
     * A GearModifier value, like the amount of damage a DamageModifier does.
     * <p>
     * This is not the actual implementation of a modifier, but merely just what determines the value
     * that a modifier will use.
     * <p>
     * Weird? This class is meant for inheritance of JSON serialization only, don't use it for anything else.
     */
    public static class GearModifierValue<A> {

        private Class<? extends GearModifier> modifierClass;
        private A value;

        /**
         * Class of the {@link GearModifier} this value is made for.
         */
        public Class<? extends GearModifier> getModifierClass() {
            return modifierClass;
        }

        /**
         * The value of the modifier.
         */
        public A getValue() {
            return value;
        }

    }

    /**
     * A persistent value like Armor's HP.
     */
    public static class Value<A> extends GearModifierValue<A> {

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
    public static class RangedDoubleValue extends GearModifierValue<Double> implements Ranged<Double> {

        private final Class<? extends GearModifier> modifier;
        private final double min, max;

        public RangedDoubleValue(Class<? extends GearModifier> modifier, double min, double max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Double getMin() {
            return min;
        }

        @Override
        public Double getMax() {
            return max;
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
    public static class RangedIntValue extends GearModifierValue<Integer> implements Ranged<Integer> {

        private final Class<? extends GearModifier> modifier;
        private final int min, max;

        public RangedIntValue(Class<? extends GearModifier> modifier, int min, int max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Integer getMin() {
            return min;
        }

        @Override
        public Integer getMax() {
            return max;
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
    public static class RangedFloatValue extends GearModifierValue<Float> implements Ranged<Float> {

        private final Class<? extends GearModifier> modifier;
        private final float min, max;

        public RangedFloatValue(Class<? extends GearModifier> modifier, float min, float max) {
            this.modifier = modifier;
            this.min = min;
            this.max = max;
        }

        @Override
        public Float getMin() {
            return min;
        }

        @Override
        public Float getMax() {
            return max;
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
