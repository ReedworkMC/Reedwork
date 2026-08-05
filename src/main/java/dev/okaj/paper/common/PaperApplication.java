package dev.okaj.paper.common;

import dev.okaj.paper.common.command.module.CommandModule;
import dev.okaj.paper.common.inject.injector.InjectorFactory;
import dev.okaj.paper.common.inject.injector.PaperInjector;
import dev.okaj.paper.common.listener.ListenerModule;
import dev.okaj.paper.common.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperApplication extends AbstractPaperApplication {

    public PaperApplication(PaperInjector injector, ModuleManager modules, PaperContext context) {
        super(injector, modules, context);

        installDefaults();

        moduleManager.initialize(context);
    }

    public static PaperApplication create(JavaPlugin plugin) {
        PaperInjector injector = InjectorFactory.create(plugin);
        ModuleManager modules = new ModuleManager();
        PaperContext context =
                new PaperContext(
                        injector,
                        modules
                );
        return new PaperApplication(
                injector,
                modules,
                context
        );
    }

    private void installDefaults() {
        moduleManager.install(new ListenerModule());
        moduleManager.install(new CommandModule());
    }
}