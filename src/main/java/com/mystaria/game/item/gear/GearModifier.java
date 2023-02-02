package com.mystaria.game.item.gear;

import com.google.gson.annotations.SerializedName;

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
     *
     * Methods in this class should always be overriden.
     * <p>
     * Weird? This class is meant for inheritance of JSON serialization only, don't use it for anything else.
     */
    public static class GearModifierValue<A> {

        /**
         * Class of the {@link GearModifier} this value is made for.
         */
        public Class<? extends GearModifier> getModifierClass() {
            return null;
        }

        /**
         * The value of the modifier.
         */
        public A getValue() {
            return null;
        }
    }

    /**
     * A persistent value like Armor's HP.
     */
    public static class Value<A> extends GearModifierValue<A> {

        private transient Class<? extends GearModifier> modifierClass;
        @SerializedName("modifierPath")
        private final String modifierClassName;
        private final A typeValue;

        public Value(Class<? extends GearModifier> modifier, A type) {
            this.modifierClassName = modifier.getName();
            this.typeValue = type;
        }

        @SuppressWarnings("unchecked")
        public Class<? extends GearModifier> getModifierClass() {
            if (modifierClass != null) return modifierClass;
            try {
                this.modifierClass = (Class<? extends GearModifier>) Class.forName(modifierClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return modifierClass;
        }

        @Override
        public A getValue() {
            return typeValue;
        }
    }

    /**
     * Returns a random Double between a min and a max.
     */
    public static class RangedDoubleValue extends GearModifierValue<Double> implements Ranged<Double> {

        private transient Class<? extends GearModifier> modifierClass;
        @SerializedName("modifierPath")
        private final String modifierClassName;
        private final double min, max;

        public RangedDoubleValue(Class<? extends GearModifier> modifier, double min, double max) {
            this.modifierClassName = modifier.getName();
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

        @SuppressWarnings("unchecked")
        public Class<? extends GearModifier> getModifierClass() {
            if (modifierClass != null) return modifierClass;
            try {
                this.modifierClass = (Class<? extends GearModifier>) Class.forName(modifierClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return modifierClass;
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

        private transient Class<? extends GearModifier> modifierClass;
        @SerializedName("modifierPath")
        private final String modifierClassName;
        private final int min, max;

        public RangedIntValue(Class<? extends GearModifier> modifier, int min, int max) {
            this.modifierClassName = modifier.getName();
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

        @SuppressWarnings("unchecked")
        public Class<? extends GearModifier> getModifierClass() {
            if (modifierClass != null) return modifierClass;
            try {
                this.modifierClass = (Class<? extends GearModifier>) Class.forName(modifierClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return modifierClass;
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

        private transient Class<? extends GearModifier> modifierClass;
        @SerializedName("modifierPath")
        private final String modifierClassName;
        private final float min, max;

        public RangedFloatValue(Class<? extends GearModifier> modifier, float min, float max) {
            this.modifierClassName = modifier.getName();
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

        @SuppressWarnings("unchecked")
        public Class<? extends GearModifier> getModifierClass() {
            if (modifierClass != null) return modifierClass;
            try {
                this.modifierClass = (Class<? extends GearModifier>) Class.forName(modifierClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return modifierClass;
        }

        @Override
        public Float getValue() {
            return ThreadLocalRandom.current().nextFloat(min, max);
        }
    }
}
