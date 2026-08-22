package dev.reedworkmc.reedwork.enchantment;

import dev.reedworkmc.reedwork.annotation.Enchantment;
import dev.reedworkmc.reedwork.inject.injector.InjectorDependencyProvider;
import dev.reedworkmc.reedwork.inject.processor.ClassProcessor;

import java.util.List;

public final class EnchantmentProcessor implements ClassProcessor {

    private final InjectorDependencyProvider injector;
    private final EnchantmentRegistry registry;
    private final EnchantmentScanner scanner;

    public EnchantmentProcessor(InjectorDependencyProvider injector, EnchantmentRegistry registry) {
        this.injector = injector;
        this.registry = registry;
        this.scanner = new EnchantmentScanner();
    }

    @Override
    public void process(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(Enchantment.class)) {
                continue;
            }

            CustomEnchantment enchantment = (CustomEnchantment) injector.get(clazz);

            registry.register(scanner.scan(enchantment));
        }
    }
}
