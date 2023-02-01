package com.mystaria.game.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

/**
 * Created by Giovanni on 1/31/2023
 */
public interface Item {

    /**
     * Fuck you Kyori, with your shitty component library forced down our innocent throats.
     */
    static ItemStack fixItalicKyoriShit(ItemStack itemStack) {
        return itemStack.withDisplayName(Item::stripItalics)
                .withLore(lore -> lore.stream()
                        .map(Item::stripItalics)
                        .toList());
    }

    private static Component stripItalics(Component component) {
        if (component == null) return null;
        if (component.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
            component = component.decoration(TextDecoration.ITALIC, false);
        return component;
    }

    /**
     * Way to check whether a Minestom {@link ItemStack} is of a specific Mystaria {@link Type}.
     */
    static boolean isItemOfType(ItemStack itemStack, Type type) {
        if (itemStack == null) return false;
        if (itemStack.material() == Material.AIR) return false;
        if (!itemStack.hasTag(ItemTags.TYPE)) return false;
        return itemStack.getTag(ItemTags.TYPE) == type;
    }

    /**
     * Way to check whether a Minestom {@link ItemStack} is of a specific Mystaria {@link Category}.
     */
    static boolean isItemOfCategory(ItemStack itemStack, Category category) {
        if (itemStack == null) return false;
        if (itemStack.material() == Material.AIR) return false;
        if (!itemStack.hasTag(ItemTags.TYPE)) return false;
        return itemStack.getTag(ItemTags.TYPE).getCategory() == category;
    }

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
        MISC
    }
}
