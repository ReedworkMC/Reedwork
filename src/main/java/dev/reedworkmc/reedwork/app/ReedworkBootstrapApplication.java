package dev.reedworkmc.reedwork.app;

import dev.reedworkmc.reedwork.app.context.BootstrapReedworkContext;
import dev.reedworkmc.reedwork.enchantment.module.EnchantmentModule;
import dev.reedworkmc.reedwork.inject.injector.ReedworkBootstrapInjector;
import dev.reedworkmc.reedwork.inject.injector.InjectorFactory;
import dev.reedworkmc.reedwork.module.ModuleManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class ReedworkBootstrapApplication extends AbstractReedworkApplication {

    public ReedworkBootstrapApplication(ReedworkBootstrapInjector injector, ModuleManager modules, BootstrapReedworkContext context) {
        super(injector, modules, context);

        installDefaults();

        moduleManager.initialize(context);
    }

    public static ReedworkBootstrapApplication create(BootstrapContext bootstrap) {
        ReedworkBootstrapInjector injector = InjectorFactory.create(bootstrap);
        ModuleManager modules = new ModuleManager();
        BootstrapReedworkContext context =
                new BootstrapReedworkContext(
                        bootstrap,
                        injector,
                        modules
                );
        return new ReedworkBootstrapApplication(
                injector,
                modules,
                context
        );
    }

    private void installDefaults() {
        moduleManager.install(new EnchantmentModule());
    }
}
