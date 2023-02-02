package com.mystaria.game.tier;

/**
 * Created by Giovanni on 2/2/2023
 */
public class TierDropChances {

    private final float[] helmetRange, chestplateRange, leggingsRange, bootsRange, handRange;

    public TierDropChances(float[] helmetRange,
                           float[] chestplateRange,
                           float[] leggingsRange,
                           float[] bootsRange,
                           float[] handRange) {
        this.helmetRange = helmetRange;
        this.chestplateRange = chestplateRange;
        this.leggingsRange = leggingsRange;
        this.bootsRange = bootsRange;
        this.handRange = handRange;
    }

    public float[] getHelmetRange() {
        return helmetRange;
    }

    public float[] getChestplateRange() {
        return chestplateRange;
    }

    public float[] getLeggingsRange() {
        return leggingsRange;
    }

    public float[] getBootsRange() {
        return bootsRange;
    }

    public float[] getHandRange() {
        return handRange;
    }

    public final class Presets {

        public static TierDropChances TIER_ONE = new TierDropChances(
                new float[]{0, 50}, // helmet
                new float[]{0, 50}, // chestplate
                new float[]{0, 50}, // leggings
                new float[]{0, 50}, // boots
                new float[]{0, 50}); // hand

        public static TierDropChances TIER_TWO = new TierDropChances(
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50});

        public static TierDropChances TIER_THREE = new TierDropChances(
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50});

        public static TierDropChances TIER_FOUR = new TierDropChances(
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50});

        public static TierDropChances TIER_FIVE = new TierDropChances(
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50},
                new float[]{0, 50});
    }
}
