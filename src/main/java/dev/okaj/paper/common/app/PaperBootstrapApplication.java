package dev.okaj.paper.common.app;

import dev.okaj.paper.common.app.context.BootstrapPaperContext;
import dev.okaj.paper.common.inject.injector.BootstrapInjector;
import dev.okaj.paper.common.inject.injector.InjectorFactory;
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
        BootstrapInjector injector = InjectorFactory.create(bootstrap);
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
        //todo:
        // moduleManager.install(new EnchantmentModule());
        // registries
    }
}
