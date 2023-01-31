package com.mystaria.game.tier;

import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Created by Giovanni on 1/31/2023
 */
public class TierColor {

    private final NamedTextColor textColor;

    /**
     * @param textColor The color used in chat, item names and monster display names.
     */
    public TierColor(NamedTextColor textColor) {
        this.textColor = textColor;
    }

    public NamedTextColor getTextColor() {
        return textColor;
    }
}
