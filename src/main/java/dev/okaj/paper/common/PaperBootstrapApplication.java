package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.BootstrapInjector;
import dev.okaj.paper.common.module.ModuleManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class PaperBootstrapApplication extends AbstractPaperApplication {

    public PaperBootstrapApplication(BootstrapInjector injector, ModuleManager modules, BootstrapPaperContext context) {
        super(injector, modules, context);

        installDefaults();

        moduleManager.initialize(context);
    }

    public static PaperBootstrapApplication create(BootstrapContext bootstrap) {
        BootstrapInjector injector = new BootstrapInjector(bootstrap);
        ModuleManager modules = new ModuleManager();
        BootstrapPaperContext context =
                new BootstrapPaperContext(
                        bootstrap,
                        injector,
                        modules
                );
        return new PaperBootstrapApplication(
                injector,
                modules,
                context
        );
    }

    private void installDefaults() {
        // später:
        // moduleManager.install(new BootstrapCommandModule());
        // moduleManager.install(new EnchantmentModule());
    }
}
