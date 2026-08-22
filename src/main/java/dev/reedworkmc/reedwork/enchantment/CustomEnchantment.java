package dev.reedworkmc.reedwork.enchantment;

import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface CustomEnchantment {
    Component description();

    TagKey<ItemType> supportedItems();

    int anvilCost();

    int maxLevel();

    int weight();

    EnchantmentRegistryEntry.EnchantmentCost minimumCost();

    EnchantmentRegistryEntry.EnchantmentCost maximumCost();

    EquipmentSlotGroup activeSlots();
}
