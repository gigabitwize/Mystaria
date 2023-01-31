package com.mystaria.game.item;

import net.minestom.server.item.ItemStack;

/**
 * Created by Giovanni on 1/31/2023
 */
public interface Item {

    /**
     * Returns the actual {@link ItemStack}.
     */
    ItemStack getItemStack();

    /**
     * Returns the item {@link Type}.
     */
    Type getType();

    enum Type {

        ARMOR(Category.GEAR),
        WEAPON(Category.GEAR),
        POTION(Category.CONSUMABLES),
        OTHER(Category.MISC);

        private final Category category;

        Type(Category category) {
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }

    enum Category {

        GEAR,
        CONSUMABLES,
        MISC;
    }
}
