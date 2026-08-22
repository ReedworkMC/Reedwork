package dev.reedworkmc.reedwork.enchantment.paper;

import dev.reedworkmc.reedwork.enchantment.CustomEnchantment;
import dev.reedworkmc.reedwork.enchantment.EnchantmentDefinition;
import dev.reedworkmc.reedwork.enchantment.EnchantmentRegistry;
import dev.reedworkmc.reedwork.logger.PaperLogger;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;

public final class PaperEnchantmentRegistry implements EnchantmentRegistry {

    private final PaperLogger logger;
    private final LifecycleEventManager<BootstrapContext> lifecycleEventManager;

    public PaperEnchantmentRegistry(PaperLogger logger, LifecycleEventManager<BootstrapContext> lifecycleEventManager) {
        this.logger = logger;
        this.lifecycleEventManager = lifecycleEventManager;
    }

    @Override
    public void register(EnchantmentDefinition definition) {
        logger.info("Registering enchantment: " + definition.key());

        lifecycleEventManager.registerEventHandler(RegistryEvents.ENCHANTMENT.compose().newHandler(event -> {
            CustomEnchantment enchantment = definition.enchantment();
            event.registry().register(TypedKey.create(RegistryKey.ENCHANTMENT, definition.key()),
                    builder -> builder
                            .description(enchantment.description())
                            .supportedItems(event.getOrCreateTag(enchantment.supportedItems()))
                            .anvilCost(enchantment.anvilCost())
                            .maxLevel(enchantment.maxLevel())
                            .weight(enchantment.weight())
                            .minimumCost(enchantment.minimumCost())
                            .maximumCost(enchantment.maximumCost())
                            .activeSlots(enchantment.activeSlots())
                    //todo add more
            );
        }));
    }
}
