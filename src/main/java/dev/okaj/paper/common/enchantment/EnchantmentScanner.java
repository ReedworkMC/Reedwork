package dev.okaj.paper.common.enchantment;

import dev.okaj.paper.common.annotation.Enchantment;
import net.kyori.adventure.key.Key;

public final class EnchantmentScanner {

    public EnchantmentDefinition scan(CustomEnchantment enchantment) {
        Enchantment annotation = enchantment.getClass().getAnnotation(Enchantment.class);

        if (annotation == null) {
            throw new IllegalArgumentException("Missing @Enchantment on " + enchantment.getClass().getName());
        }

        return new EnchantmentDefinition(Key.key(annotation.value()), enchantment);
    }
}
