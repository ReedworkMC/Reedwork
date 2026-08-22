package dev.reedworkmc.reedwork.enchantment.module;

import dev.reedworkmc.reedwork.app.context.ApplicationContext;
import dev.reedworkmc.reedwork.app.context.BootstrapReedworkContext;
import dev.reedworkmc.reedwork.enchantment.EnchantmentProcessor;
import dev.reedworkmc.reedwork.enchantment.exception.EnchantmentException;
import dev.reedworkmc.reedwork.enchantment.paper.PaperEnchantmentRegistry;
import dev.reedworkmc.reedwork.module.ReedworkModule;

public class EnchantmentModule implements ReedworkModule {
    @Override
    public void initialize(ApplicationContext context) {
        if (!(context instanceof BootstrapReedworkContext bootstrapReedworkContext)) {
            throw new EnchantmentException("Enchantment module musst run in Bootloader.");
        }

        PaperEnchantmentRegistry registry = new PaperEnchantmentRegistry(
                bootstrapReedworkContext.logger(),
                bootstrapReedworkContext.lifecycleManager()
        );

        bootstrapReedworkContext.injector().addProcessor(
                new EnchantmentProcessor(bootstrapReedworkContext.injector(), registry)
        );
    }
}
