package dev.okaj.paper.common.enchantment.module;

import dev.okaj.paper.common.app.context.ApplicationContext;
import dev.okaj.paper.common.app.context.BootstrapPaperContext;
import dev.okaj.paper.common.enchantment.EnchantmentProcessor;
import dev.okaj.paper.common.enchantment.exception.EnchantmentException;
import dev.okaj.paper.common.enchantment.paper.PaperEnchantmentRegistry;
import dev.okaj.paper.common.module.PaperModule;

public class EnchantmentModule implements PaperModule {
    @Override
    public void initialize(ApplicationContext context) {
        if (!(context instanceof BootstrapPaperContext bootstrapPaperContext)) {
            throw new EnchantmentException("Enchantment module musst run in Bootloader.");
        }

        PaperEnchantmentRegistry registry = new PaperEnchantmentRegistry(
                bootstrapPaperContext.logger(),
                bootstrapPaperContext.lifecycleManager()
        );

        bootstrapPaperContext.injector().addProcessor(
                new EnchantmentProcessor(bootstrapPaperContext.injector(), registry)
        );
    }
}
