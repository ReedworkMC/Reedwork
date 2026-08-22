package dev.reedworkmc.reedwork.app;

import dev.reedworkmc.reedwork.app.context.ReedworkContext;
import dev.reedworkmc.reedwork.command.module.CommandModule;
import dev.reedworkmc.reedwork.inject.injector.InjectorFactory;
import dev.reedworkmc.reedwork.inject.injector.ReedworkInjector;
import dev.reedworkmc.reedwork.listener.module.ListenerModule;
import dev.reedworkmc.reedwork.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReedworkApplication extends AbstractReedworkApplication {

    public ReedworkApplication(ReedworkInjector injector, ModuleManager modules, ReedworkContext context) {
        super(injector, modules, context);

        installDefaults();

        moduleManager.initialize(context);
    }

    public static ReedworkApplication create(JavaPlugin plugin) {
        ReedworkInjector injector = InjectorFactory.create(plugin);
        ModuleManager modules = new ModuleManager();
        ReedworkContext context =
                new ReedworkContext(
                        injector,
                        modules
                );
        return new ReedworkApplication(
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